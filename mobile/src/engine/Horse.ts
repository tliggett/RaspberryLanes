import { Horse } from "../models/types";
import {
  FURLONG_COUNT,
  BASE_FURLONG_TIME,
  RACE_TIME_OFFSET,
  INJURY_CHANCE,
  INJURY_NUMBER,
  DNF_TIME,
} from "../utils/constants";

/**
 * Simulate a single race for a horse. Mutates the horse in place.
 * Faithfully ports Horse.doRace() from Java.
 */
export function simulateRace(horse: Horse): void {
  horse.age++;

  // 1% injury chance (injury number == 24)
  const injury = Math.floor(Math.random() * INJURY_CHANCE);
  if (injury === INJURY_NUMBER) {
    horse.times.push(DNF_TIME);
    horse.furlongs = new Array(FURLONG_COUNT).fill(0);
    return;
  }

  // Random factor for this race (0 to 6, continuous)
  const rnd = Math.random() * 6;

  // Base furlong time for each furlong
  for (let i = 0; i < FURLONG_COUNT; i++) {
    horse.furlongs[i] = BASE_FURLONG_TIME - horse.speed / 10 + rnd / 10;
  }

  // Acceleration penalty on first furlong
  horse.furlongs[0] += (10 - horse.acc / 10) * 2;

  // Stamina degradation
  const staminaDropoff = Math.floor(horse.stamina / 10 - 2);
  let dep = 1;
  for (let i = staminaDropoff; i < FURLONG_COUNT; i++) {
    horse.furlongs[i] += dep;
    dep++;
  }

  // Heart bonus on final furlong
  horse.furlongs[9] -= horse.heart / 20;
  if (horse.furlongs[9] < horse.speed) {
    horse.furlongs[9] = 20.5 - horse.speed / 10;
  }

  // Sum total time
  let time = 0;
  for (const furlong of horse.furlongs) {
    time += furlong;
  }

  // Add base offset
  time += RACE_TIME_OFFSET;

  // Age decay
  const ageDiff = horse.maxAge - horse.age;
  if (ageDiff !== 0) {
    horse.speed -= Math.trunc(10 / ageDiff);
    horse.acc -= Math.trunc(3 / ageDiff);
  }

  // Round to 2 decimal places (matching Java: (int)(time*100) / 100)
  time = Math.floor(time * 100) / 100;
  horse.times.push(time);
}

/**
 * Calculate horse score for odds. Ports Horse.calcScore() from Java.
 */
export function calcScore(horse: Horse): number {
  if (horse.times.length === 0) {
    return 2;
  }
  const lastTime = horse.times[horse.times.length - 1];
  let ret = 130 - lastTime - horse.age / 2;
  if (ret <= 0) {
    ret = 0.01;
  }
  return ret;
}

/**
 * Get the horse's last race time.
 */
export function getLastTime(horse: Horse): number {
  if (horse.times.length === 0) return 0;
  return horse.times[horse.times.length - 1];
}

/**
 * Get pixels-per-second for current furlong based on x position.
 * Ports Horse.getPPS() from Java.
 */
export function getPPS(horse: Horse): number {
  if (horse.times.length === 0) return 0;
  const furlongWidth = 85;
  for (let i = 0; i < 10; i++) {
    if (horse.x < furlongWidth * (i + 1)) {
      return furlongWidth / horse.furlongs[i];
    }
  }
  return furlongWidth / horse.furlongs[9];
}

/**
 * Check if a horse should retire.
 */
export function shouldRetire(horse: Horse): boolean {
  const lastTime = getLastTime(horse);
  return (
    horse.age > horse.maxAge ||
    lastTime === DNF_TIME ||
    lastTime > 130
  );
}
