import { Horse, Bet, RaceResult } from "../models/types";
import { simulateRace, getLastTime, shouldRetire } from "./Horse";
import { calculatePayout } from "./OddsCalculator";
import { getUniqueName, createHorse } from "./HorseGenerator";
import { HORSE_COUNT } from "../utils/constants";

/**
 * Run a full race: simulate all horses, sort by time, determine results.
 * Ports the race flow from RaspberryGUI.updateStuff() and HorseList.
 */
export function runFullRace(
  horses: Horse[],
  bets: Bet[]
): RaceResult {
  // 1. Simulate race for all horses
  for (const horse of horses) {
    simulateRace(horse);
  }

  // 2. Sort by time (ascending — fastest first)
  horses.sort((a, b) => getLastTime(a) - getLastTime(b));

  // 3. Process results — determine placements and retirements
  // The original Java starts from index 1 (mark=1 in printResults),
  // meaning place numbering starts at 2. This is because index 0 is the winner
  // displayed separately. But we'll track all placements for the UI.
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

  // 4. Remove retired horses (reverse order to preserve indices)
  for (let i = toRemove.length - 1; i >= 0; i--) {
    horses.splice(toRemove[i], 1);
  }

  // 5. Replace retired horses with new ones
  while (horses.length < HORSE_COUNT) {
    const name = getUniqueName(horses.map((h) => h.name));
    horses.push(createHorse(name));
  }

  // 6. Process bets — winner is first place from placements
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
