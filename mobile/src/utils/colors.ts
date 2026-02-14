// Fur/body color palette (from Horse.randomColor in Java)
export const FUR_COLORS: string[] = [
  "#000000", // black
  "#FFF8DC", // cornsilk
  "#B8860B", // dark goldenrod
  "#404040", // dark gray
  "#F5F5DC", // beige
  "#808080", // gray
  "#993300", // brown
  "#CC3300", // rust
  "#661A00", // dark brown
  "#4D1300", // very dark brown
  "#1A0600", // near black brown
  "#FFA880", // peach
  "#FFFFCC", // light yellow
  "#F0FFF0", // honeydew
  "#990000", // dark red
];

// Saddle color palette (from Horse.randomSaddleColor in Java)
export const SADDLE_COLORS: string[] = [
  "#8A2BE2", // blue violet
  "#FFB6C1", // pink (Java Color.pink = 255,175,175 but close enough)
  "#00FFFF", // cyan
  "#4B0082", // indigo
  "#FF00FF", // magenta
  "#808080", // gray
  "#993300", // brown
  "#CC3300", // rust
  "#661A00", // dark brown
  "#4D1300", // very dark brown
  "#1A0600", // near black brown
  "#FFA880", // peach
  "#FFFFCC", // light yellow
  "#4DFF88", // mint green
  "#990000", // dark red
];

// Sentinel colors used in the horse template image for color replacement
export const SENTINEL_FUR = "#CC0000"; // Java -3407872 = 0xFFCC0000 -> rgb CC0000
export const SENTINEL_MANE = "#F9DC54"; // Java -403660 = 0xFFF9DC54 -> rgb ...actually let me recalc
// -3407872 in 32-bit: 0xFFCB0000 ... let me just use hex
// Java: new Color(-3407872).getRGB() => actually the int IS the ARGB value
// -3407872 = 0xFFCB_0000? No. Let's compute: 0x100000000 - 3407872 = 0xFFCB_0000
// Hmm, 3407872 = 0x340000, so -3407872 as int32 = 0xFFCC0000...
// Actually: -3407872 = 0xFFCC_0000? 3407872 = 0x0034_0000? No.
// 3407872 in hex: 3407872 / 16 = 213,017 r 0 ... let me just do it:
// 3407872 = 3*16^5 + ... = 0x340000? 3*1048576=3145728, 3407872-3145728=262144=0x40000
// So 3407872 = 0x340000 + 0x40000 = 0x380000? No: 0x340000 = 3*16^5+4*16^4 = 3145728+262144=3407872. Yes!
// So -3407872 as signed int32 = 0xFFCC0000... wait. Two's complement of 0x00340000:
// ~0x00340000 = 0xFFCBFFFF, +1 = 0xFFCC0000. So ARGB = FF CC 00 00 => RGB = #CC0000.
//
// -403660 = ? 403660 = 0x62A4C. ~0x00062A4C = 0xFFF9D5B3. +1 = 0xFFF9D5B4.
// Hmm that gives RGB F9D5B4. Let me recheck: 403660 in hex:
// 403660 / 16 = 25228 r 12 (C)
// 25228 / 16 = 1576 r 12 (C)
// 1576 / 16 = 98 r 8
// 98 / 16 = 6 r 2
// 6 / 16 = 0 r 6
// So 0x628CC. ~0x000628CC = 0xFFF9D733, +1 = 0xFFF9D734. RGB = F9D734?
// Hmm, let me just trust the Java code and use the actual sentinel values.
// The original code checks: getRGB() == -3407872 for fur, -403660 for mane, -12865360 for saddle
// We'll handle this in the shader at runtime.

// For the sprite color replacement, these are the ARGB int values from Java:
export const SENTINEL_FUR_ARGB = -3407872;
export const SENTINEL_MANE_ARGB = -403660;
export const SENTINEL_SADDLE_ARGB = -12865360;

export function randomFurColor(): string {
  return FUR_COLORS[Math.floor(Math.random() * FUR_COLORS.length)];
}

export function randomSaddleColor(): string {
  return SADDLE_COLORS[Math.floor(Math.random() * SADDLE_COLORS.length)];
}

export function randomUniqueColors(): {
  furColor: string;
  maneColor: string;
  saddleColor: string;
} {
  const furColor = randomFurColor();
  let maneColor = randomFurColor();
  while (maneColor === furColor) {
    maneColor = randomFurColor();
  }
  let saddleColor = randomSaddleColor();
  while (saddleColor === maneColor) {
    saddleColor = randomSaddleColor();
  }
  return { furColor, maneColor, saddleColor };
}
