import { Horse, Bet, RaceResult } from "../models/types";
import { simulateRace, getLastTime, shouldRetire } from "./Horse";
import { calculatePayout } from "./OddsCalculator";
import { getUniqueName, createHorse } from "./HorseGenerator";
import { HORSE_COUNT } from "../utils/constants";

/**
 * Phase 1: Simulate the race only — compute furlongs and times.
 * Does NOT sort, retire, or replace. Keeps original lane order for animation.
 */
export function simulateAllHorses(horses: Horse[]): void {
  for (const horse of horses) {
    simulateRace(horse);
  }
}

/**
 * Phase 2: Process results after animation is done.
 * Sorts by time, retires, replaces, and processes bets.
 */
export function processRaceResults(
  horses: Horse[],
  bets: Bet[]
): RaceResult {
  // 1. Sort by time (ascending — fastest first)
  horses.sort((a, b) => getLastTime(a) - getLastTime(b));

  // 2. Determine placements and retirements
  const placements: RaceResult["placements"] = [];
  const toRemove: number[] = [];

  for (let i = 0; i < horses.length; i++) {
    const horse = horses[i];
    const place = i + 1;
    const time = getLastTime(horse);
    // Winner (index 0) is never retired — matches original Java's printResults(1)
    const retired = i > 0 && shouldRetire(horse);

    placements.push({ place, horse, time, retired });

    if (retired) {
      toRemove.push(i);
    } else {
      horse.places.push(place);
    }
  }

  // 3. Remove retired horses (reverse order to preserve indices)
  for (let i = toRemove.length - 1; i >= 0; i--) {
    horses.splice(toRemove[i], 1);
  }

  // 4. Replace retired horses with new ones
  while (horses.length < HORSE_COUNT) {
    const name = getUniqueName(horses.map((h) => h.name));
    horses.push(createHorse(name));
  }

  // 5. Process bets
  const winnerName = placements.length > 0 ? placements[0].horse.name : "";
  const betOutcomes: RaceResult["betOutcomes"] = [];
  let totalEarnings = 0;

  for (const bet of bets) {
    const won = bet.horseName === winnerName;
    bet.won = won;
    const payout = won ? calculatePayout(bet.amount, bet.odds) : 0;
    betOutcomes.push({ bet, payout });
    totalEarnings += payout;
  }

  return {
    placements,
    betOutcomes,
    totalEarnings,
  };
}
