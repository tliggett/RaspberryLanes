export interface Horse {
  name: string;
  age: number;
  maxAge: number;
  speed: number;
  acc: number;
  stamina: number;
  heart: number;
  furColor: string;
  maneColor: string;
  saddleColor: string;
  times: number[]; // race finish times history
  places: number[]; // finishing positions history
  furlongs: number[]; // per-furlong times for current race (10 furlongs)
  x: number; // current animation x position
}

export interface Bet {
  horseName: string;
  amount: number;
  odds: number;
  won: boolean;
}

export interface Player {
  name: string;
  cash: number;
}

export type TrackTheme = "Classic" | "Christmas" | "Beach" | "Chocolate" | "Rainbow";

export interface TrackConfig {
  name: TrackTheme;
  backgroundImage: number; // require() asset
  musicFile: number; // require() asset
  hasSnow: boolean;
  hasRain: boolean;
  particleColor: string;
  particleWidth: number;
  particleHeight: number;
  particleSpeed: number;
}

export interface RaceResult {
  placements: {
    place: number;
    horse: Horse;
    time: number;
    retired: boolean;
  }[];
  betOutcomes: {
    bet: Bet;
    payout: number;
  }[];
  totalEarnings: number;
}

export interface GameState {
  player: Player;
  horses: Horse[];
  bets: Bet[];
  currentTrack: TrackTheme;
  raceNumber: number;
  lastRaceResult: RaceResult | null;
  isRacing: boolean;
  gameOver: boolean;
}
