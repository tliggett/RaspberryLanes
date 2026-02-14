import { Horse } from "../models/types";
import { HORSE_NAMES } from "./HorseNameDatabase";
import { randomUniqueColors } from "../utils/colors";
import {
  MIN_STAT,
  MAX_STAT,
  MIN_MAX_AGE,
  MAX_MAX_AGE,
  FURLONG_COUNT,
} from "../utils/constants";

export function createHorse(name: string): Horse {
  const { furColor, maneColor, saddleColor } = randomUniqueColors();
  return {
    name,
    age: 0,
    maxAge: Math.floor(Math.random() * (MAX_MAX_AGE - MIN_MAX_AGE)) + MIN_MAX_AGE,
    speed: Math.random() * (MAX_STAT - MIN_STAT) + MIN_STAT,
    acc: Math.random() * (MAX_STAT - MIN_STAT) + MIN_STAT,
    stamina: Math.random() * (MAX_STAT - MIN_STAT) + MIN_STAT,
    heart: Math.random() * (MAX_STAT - MIN_STAT) + MIN_STAT,
    furColor,
    maneColor,
    saddleColor,
    times: [],
    places: [],
    furlongs: new Array(FURLONG_COUNT).fill(0),
    x: 0,
  };
}

export function createSpecialHorse(code: number): Horse {
  const base: Horse = {
    name: "",
    age: 0,
    maxAge: 2,
    speed: 100,
    acc: 100,
    stamina: 100,
    heart: 100,
    furColor: "#CC0000",
    maneColor: "#F9DC54",
    saddleColor: "#3C2AB0",
    times: [],
    places: [],
    furlongs: new Array(FURLONG_COUNT).fill(0),
    x: 0,
  };

  switch (code) {
    case 1: // Raspberry Lanes
      return {
        ...base,
        name: "Raspberry Lanes",
        speed: 150,
        acc: 120,
        stamina: 120,
        heart: 120,
      };
    case 3: // Donald Trump
      return {
        ...base,
        name: "Donald Trump",
        saddleColor: "#FF0000",
      };
    case 4: // Usain Bolt
      return {
        ...base,
        name: "Usain Bolt",
        speed: 120,
        acc: 90,
        stamina: 120,
        heart: 120,
        saddleColor: "#00FF00",
      };
    default:
      return base;
  }
}

export function getUniqueName(existingNames: string[]): string {
  const nameSet = new Set(existingNames);
  let name: string;
  do {
    name = HORSE_NAMES[Math.floor(Math.random() * HORSE_NAMES.length)];
  } while (nameSet.has(name));
  return name;
}

export function generateInitialHorses(): Horse[] {
  const horses: Horse[] = [];
  for (let i = 0; i < 10; i++) {
    const name = getUniqueName(horses.map((h) => h.name));
    horses.push(createHorse(name));
  }
  return horses;
}
