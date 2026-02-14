import { DNF_TIME } from "./constants";

export function timeToString(numTime: number): string {
  if (numTime === DNF_TIME) {
    return "DNF";
  }

  const seconds = Math.floor((numTime % 60) * 100) / 100;
  const min = Math.floor(numTime / 60);
  const sec = seconds < 10 ? `0${seconds}` : `${seconds}`;
  return `${min}:${sec}`;
}

export function numToPlace(num: number): string {
  const div = num % 10;
  let suffix: string;
  if (div === 1 && num !== 11) {
    suffix = "st";
  } else if (div === 2 && num !== 12) {
    suffix = "nd";
  } else if (div === 3 && num !== 13) {
    suffix = "rd";
  } else {
    suffix = "th";
  }
  return `${num}${suffix}`;
}

export function formatCash(amount: number): string {
  return `$${Math.floor(amount)}`;
}
