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
import { simulateAllHorses, processRaceResults } from "../engine/RaceEngine";
import { STARTING_CASH, WEEKLY_ALLOWANCE } from "../utils/constants";

interface GameActions {
  // Game lifecycle
  newGame: (playerName: string, track: TrackTheme) => void;
  loadGame: (state: GameState) => void;

  // Betting
  placeBet: (horseName: string, amount: number) => void;
  clearBets: () => void;

  // Racing — two-phase: simulate sets up animation, finishRace processes results
  simulateRace: () => void;
  finishRace: () => void;
  setIsRacing: (racing: boolean) => void;

  // Easter eggs
  addSpecialHorse: (code: number) => void;

  // Getters
  getOdds: (horseName: string) => number;
  getSaveData: () => GameState;
}

// Bets are stashed here between simulate and finish so they survive the state update
let pendingBets: Bet[] = [];

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

  // Phase 1: Simulate only — compute furlongs/times, keep original lane order
  simulateRace: () => {
    const { horses, bets } = get();

    // Deep clone horses for simulation
    const horsesClone = horses.map((h) => ({
      ...h,
      times: [...h.times],
      places: [...h.places],
      furlongs: [...h.furlongs],
    }));

    // Simulate — computes furlongs and times but does NOT sort/retire/replace
    simulateAllHorses(horsesClone);

    // Stash bets for processing after animation
    pendingBets = bets.map((b) => ({ ...b }));

    // Update horses with simulation data (furlongs + times) — same lane order
    set({
      horses: horsesClone,
      bets: [],
      isRacing: true,
    });
  },

  // Phase 2: After animation — sort, retire, replace, process bets, update cash
  finishRace: () => {
    const { horses, player, raceNumber } = get();

    // Deep clone again since processRaceResults mutates (sort, splice, push)
    const horsesClone = horses.map((h) => ({
      ...h,
      times: [...h.times],
      places: [...h.places],
      furlongs: [...h.furlongs],
    }));

    const result = processRaceResults(horsesClone, pendingBets);
    pendingBets = [];

    const newCash = player.cash + result.totalEarnings;

    set({
      horses: horsesClone,
      player: { ...player, cash: newCash },
      raceNumber: raceNumber + 1,
      lastRaceResult: result,
      isRacing: false,
      gameOver: newCash <= 0,
    });
  },

  setIsRacing: (racing) => set({ isRacing: racing }),

  addSpecialHorse: (code) => {
    const { horses } = get();
    const special = createSpecialHorse(code);
    const newHorses = [...horses];
    if (code === 1) newHorses.splice(9, 1, special);
    if (code === 3) newHorses.splice(8, 1, special);
    if (code === 4) newHorses.splice(7, 1, special);
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
