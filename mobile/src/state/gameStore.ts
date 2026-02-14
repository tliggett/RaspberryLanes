import { create } from "zustand";
import {
  Horse,
  Bet,
  TrackTheme,
  RaceResult,
  GameState,
} from "../models/types";
import { generateInitialHorses, createSpecialHorse } from "../engine/HorseGenerator";
import { getHorseOdds } from "../engine/OddsCalculator";
import { runFullRace } from "../engine/RaceEngine";
import { STARTING_CASH, WEEKLY_ALLOWANCE } from "../utils/constants";

interface GameActions {
  // Game lifecycle
  newGame: (playerName: string, track: TrackTheme) => void;
  loadGame: (state: GameState) => void;

  // Betting
  placeBet: (horseName: string, amount: number) => void;
  clearBets: () => void;

  // Racing
  startRace: () => RaceResult;
  finishRace: () => void;
  setIsRacing: (racing: boolean) => void;

  // Easter eggs
  addSpecialHorse: (code: number) => void;

  // Getters
  getOdds: (horseName: string) => number;
  getSaveData: () => GameState;
}

type GameStore = GameState & GameActions;

export const useGameStore = create<GameStore>((set, get) => ({
  // Initial state
  player: { name: "", cash: STARTING_CASH },
  horses: [],
  bets: [],
  currentTrack: "Classic",
  raceNumber: 0,
  lastRaceResult: null,
  isRacing: false,
  gameOver: false,

  newGame: (playerName, track) => {
    set({
      player: { name: playerName, cash: STARTING_CASH },
      horses: generateInitialHorses(),
      bets: [],
      currentTrack: track,
      raceNumber: 0,
      lastRaceResult: null,
      isRacing: false,
      gameOver: false,
    });
  },

  loadGame: (state) => {
    set({
      player: state.player,
      horses: state.horses,
      bets: [],
      currentTrack: state.currentTrack,
      raceNumber: state.raceNumber,
      lastRaceResult: null,
      isRacing: false,
      gameOver: state.gameOver,
    });
  },

  placeBet: (horseName, amount) => {
    const { player, horses, bets } = get();
    // Cap bet at available cash
    const actualBet = Math.min(amount, player.cash);
    if (actualBet <= 0) return;

    const odds = getHorseOdds(
      horses.find((h) => h.name === horseName)!,
      horses
    );

    const bet: Bet = {
      horseName,
      amount: actualBet,
      odds,
      won: false,
    };

    set({
      player: { ...player, cash: player.cash - actualBet },
      bets: [...bets, bet],
    });
  },

  clearBets: () => set({ bets: [] }),

  startRace: () => {
    const { horses, bets, player, raceNumber } = get();

    // Deep clone horses so race simulation mutates the clones
    const horsesClone = horses.map((h) => ({
      ...h,
      times: [...h.times],
      places: [...h.places],
      furlongs: [...h.furlongs],
    }));

    const betsClone = bets.map((b) => ({ ...b }));
    const result = runFullRace(horsesClone, betsClone);

    // Apply weekly allowance + bet earnings
    const newCash = player.cash + WEEKLY_ALLOWANCE + result.totalEarnings;

    set({
      horses: horsesClone,
      bets: [],
      player: { ...player, cash: newCash },
      raceNumber: raceNumber + 1,
      lastRaceResult: result,
      gameOver: newCash <= 0,
    });

    return result;
  },

  finishRace: () => {
    set({ isRacing: false });
  },

  setIsRacing: (racing) => set({ isRacing: racing }),

  addSpecialHorse: (code) => {
    const { horses } = get();
    const special = createSpecialHorse(code);
    const newHorses = [...horses];
    // Replace last horse(s) like the original
    if (code === 1) newHorses.splice(9, 1, special); // raspberry replaces 10th
    if (code === 3) newHorses.splice(8, 1, special); // trump replaces 9th
    if (code === 4) newHorses.splice(7, 1, special); // bolt replaces 8th
    set({ horses: newHorses });
  },

  getOdds: (horseName) => {
    const { horses } = get();
    const horse = horses.find((h) => h.name === horseName);
    if (!horse) return 0;
    return getHorseOdds(horse, horses);
  },

  getSaveData: () => {
    const { player, horses, currentTrack, raceNumber, gameOver } = get();
    return {
      player,
      horses,
      bets: [],
      currentTrack,
      raceNumber,
      lastRaceResult: null,
      isRacing: false,
      gameOver,
    };
  },
}));
