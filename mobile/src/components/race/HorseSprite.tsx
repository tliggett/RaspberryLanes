import React from "react";
import Svg, { Path, Circle } from "react-native-svg";

interface HorseSpriteProps {
  furColor: string;
  maneColor: string;
  saddleColor: string;
  size?: number;
}

/**
 * SVG horse sprite with 3 colorable regions matching the original game.
 * Designed to face right in a galloping pose.
 * Viewbox is 100x100 for easy scaling.
 */
function HorseSprite({
  furColor,
  maneColor,
  saddleColor,
  size = 38,
}: HorseSpriteProps) {
  return (
    <Svg width={size} height={size} viewBox="0 0 100 100">
      {/* Body - main horse silhouette */}
      <Path
        d={`
          M 70 25
          Q 80 20 85 22
          L 88 20
          Q 90 18 92 20
          L 90 25
          Q 88 28 85 28
          Q 80 28 75 32
          L 72 38
          Q 70 42 68 45
          L 65 48
          Q 60 50 55 50
          L 45 50
          Q 38 50 32 48
          L 28 45
          Q 22 40 18 35
          Q 15 32 12 35
          L 8 42
          Q 5 45 3 42
          L 5 38
          Q 8 30 15 28
          Q 20 26 25 28
          L 30 32
          Q 35 36 40 38
          L 45 40
          Q 55 40 62 36
          L 68 30
          Z
        `}
        fill={furColor}
        stroke="#000"
        strokeWidth={1.5}
      />

      {/* Front legs */}
      <Path
        d={`
          M 62 48 L 65 62 L 68 75 L 70 82 L 66 82 L 63 75 L 60 62 L 58 50
        `}
        fill={furColor}
        stroke="#000"
        strokeWidth={1.2}
      />
      <Path
        d={`
          M 55 49 L 53 60 L 50 72 L 48 82 L 52 82 L 54 72 L 56 60 L 57 50
        `}
        fill={furColor}
        stroke="#000"
        strokeWidth={1.2}
      />

      {/* Hind legs */}
      <Path
        d={`
          M 32 47 L 28 58 L 25 68 L 22 82 L 26 82 L 29 68 L 32 58 L 34 48
        `}
        fill={furColor}
        stroke="#000"
        strokeWidth={1.2}
      />
      <Path
        d={`
          M 25 45 L 20 55 L 15 65 L 12 82 L 16 82 L 19 65 L 23 55 L 27 46
        `}
        fill={furColor}
        stroke="#000"
        strokeWidth={1.2}
      />

      {/* Hooves */}
      <Path d="M 66 80 L 70 80 L 70 84 L 66 84 Z" fill="#000" />
      <Path d="M 48 80 L 52 80 L 52 84 L 48 84 Z" fill="#000" />
      <Path d="M 22 80 L 26 80 L 26 84 L 22 84 Z" fill="#000" />
      <Path d="M 12 80 L 16 80 L 16 84 L 12 84 Z" fill="#000" />

      {/* Saddle blanket */}
      <Path
        d={`
          M 40 32
          Q 45 28 55 30
          L 60 34
          Q 62 38 60 42
          L 55 46
          Q 48 48 42 46
          L 38 42
          Q 36 38 38 34
          Z
        `}
        fill={saddleColor}
        stroke="#000"
        strokeWidth={1}
      />

      {/* Saddle star decoration */}
      <Path
        d={`
          M 49 36 L 50 33 L 51 36 L 54 36 L 52 38 L 53 41 L 50 39 L 47 41 L 48 38 L 46 36 Z
        `}
        fill="#FFF"
        stroke="none"
      />

      {/* Mane */}
      <Path
        d={`
          M 72 25
          Q 74 20 76 18
          Q 78 16 76 22
          Q 74 26 72 28
          L 68 32
          Q 66 34 65 32
          Q 67 28 70 26
          Z
        `}
        fill={maneColor}
        stroke="#000"
        strokeWidth={1}
      />
      {/* Forelock tuft */}
      <Path
        d={`
          M 80 22
          Q 78 15 82 12
          Q 84 10 83 16
          Q 82 20 80 23
          Z
        `}
        fill={maneColor}
        stroke="#000"
        strokeWidth={0.8}
      />

      {/* Tail */}
      <Path
        d={`
          M 15 30
          Q 8 25 5 18
          Q 3 14 6 16
          Q 10 20 14 26
          Q 16 28 15 30
          Z
        `}
        fill={maneColor}
        stroke="#000"
        strokeWidth={1}
      />

      {/* Eye */}
      <Circle cx={87} cy={23} r={1.5} fill="#000" />
    </Svg>
  );
}

export default React.memo(HorseSprite);
