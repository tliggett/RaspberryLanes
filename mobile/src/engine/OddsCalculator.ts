import { Horse } from "../models/types";
import { calcScore } from "./Horse";

/**
 * Calculate the total score of all horses.
 */
export function sumScores(horses: Horse[]): number {
  return horses.reduce((sum, horse) => sum + calcScore(horse), 0);
}

/**
 * Calculate odds for a specific horse.
 * Ports HorseList.getHorseOdds() from Java.
 * Returns a value between 0.01 and 1.0 (probability-style odds).
 */
export function getHorseOdds(horse: Horse, horses: Horse[]): number {
  const totalScore = sumScores(horses);
  let odds = calcScore(horse) / totalScore;
  // Truncate to 2 decimal places (matching Java: (int)(odds*100) / 100)
  odds = Math.floor(odds * 100) / 100;
  if (odds <= 0) {
    return 0.01;
  }
  return odds;
}

/**
 * Calculate payout for a winning bet.
 * Ports Bet.reward() from Java: (int)(value / odds)
 */
export function calculatePayout(betAmount: number, odds: number): number {
  return Math.floor(betAmount / odds);
}
