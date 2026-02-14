import React, { useEffect, useRef, useState, useMemo } from "react";
import { View, Animated, StyleSheet, Dimensions } from "react-native";
import { Horse, TrackTheme } from "../../models/types";
import { getPPS } from "../../engine/Horse";
import HorseSprite from "./HorseSprite";

const { width: SCREEN_WIDTH } = Dimensions.get("window");
const CANVAS_HEIGHT = 420;
const HORSE_SIZE = 30;
const HORSE_Y_START = 40;
const HORSE_Y_SPACING = HORSE_SIZE + 6;
const PARTICLE_COUNT = 50;

interface RaceCanvasProps {
  horses: Horse[];
  isRacing: boolean;
  track: TrackTheme;
  onRaceFinish: () => void;
}

function getParticleConfig(track: TrackTheme) {
  if (track === "Christmas")
    return { active: true, color: "#FFFFFF", w: 3, h: 3, speed: 1.5 };
  if (track === "Chocolate")
    return { active: true, color: "#4444FF", w: 1, h: 5, speed: 6 };
  return { active: false, color: "#FFF", w: 0, h: 0, speed: 0 };
}

function getTrackBg(track: TrackTheme): string {
  switch (track) {
    case "Christmas": return "#1A4D1A";
    case "Beach": return "#87CEEB";
    case "Chocolate": return "#3E1A00";
    case "Rainbow": return "#2D0060";
    default: return "#4A7A3A";
  }
}

function getTrackSurface(track: TrackTheme): string {
  switch (track) {
    case "Christmas": return "#DDDDDD";
    case "Beach": return "#F4D03F";
    case "Chocolate": return "#8B4513";
    case "Rainbow": return "#FF69B4";
    default: return "#C4A35A";
  }
}

export default function RaceCanvas({
  horses,
  isRacing,
  track,
  onRaceFinish,
}: RaceCanvasProps) {
  const frameCountRef = useRef(0);
  const horsePositions = useRef<number[]>(horses.map(() => 0));
  const gallopRef = useRef(0);
  const finishedRef = useRef(false);

  // Animated values for each horse's X position and gallop Y offset
  const animatedX = useRef(horses.map(() => new Animated.Value(10))).current;
  const animatedGallop = useRef(new Animated.Value(0)).current;

  // Particles - only used for Christmas/Chocolate
  const pConfig = getParticleConfig(track);
  const particlesRef = useRef(
    Array.from({ length: PARTICLE_COUNT }, () => ({
      x: Math.random() * SCREEN_WIDTH,
      y: Math.random() * CANVAS_HEIGHT,
    }))
  );
  const [particles, setParticles] = useState(particlesRef.current);
  const particleFrameRef = useRef(0);

  // Reset when race starts
  useEffect(() => {
    if (isRacing) {
      finishedRef.current = false;
      frameCountRef.current = 0;
      particleFrameRef.current = 0;
      horsePositions.current = horses.map(() => 0);
      for (const h of horses) {
        h.x = 0;
      }
      animatedX.forEach((a) => a.setValue(10));
      animatedGallop.setValue(0);
    }
  }, [isRacing]);

  // Animation loop
  useEffect(() => {
    if (!isRacing) return;

    const interval = setInterval(() => {
      if (finishedRef.current) return;

      frameCountRef.current += 1;

      // Move horses - update Animated values directly (no re-render)
      horses.forEach((horse, i) => {
        if (horse.times[horse.times.length - 1] === 999) return;
        const pps = getPPS(horse);
        const newX = horsePositions.current[i] + pps;
        horse.x = newX;
        horsePositions.current[i] = newX;
        const scaled = (newX / 850) * (SCREEN_WIDTH - 40) + 10;
        animatedX[i].setValue(scaled);
      });

      // Gallop bob
      gallopRef.current = gallopRef.current === 0 ? 1 : 0;
      animatedGallop.setValue(gallopRef.current);

      // Update particles only every 3rd frame
      if (pConfig.active) {
        particleFrameRef.current += 1;
        if (particleFrameRef.current % 3 === 0) {
          const updated = particlesRef.current.map((p) => {
            let newY = p.y + pConfig.speed * 3;
            let newX = p.x;
            if (newY > CANVAS_HEIGHT) {
              newY = 0;
              newX = Math.random() * SCREEN_WIDTH;
            }
            return { x: newX, y: newY };
          });
          particlesRef.current = updated;
          setParticles([...updated]);
        }
      }

      // Check if race is done
      if (frameCountRef.current > 150) {
        finishedRef.current = true;
        for (const h of horses) {
          h.x = 0;
        }
        horsePositions.current = horses.map(() => 0);
        animatedX.forEach((a) => a.setValue(10));
        onRaceFinish();
      }
    }, 30);

    return () => clearInterval(interval);
  }, [isRacing, horses, track]);

  const bgColor = getTrackBg(track);
  const surfaceColor = getTrackSurface(track);

  // Memoize the horse sprites since colors don't change during a race
  const horseSprites = useMemo(
    () =>
      horses.map((horse) => (
        <HorseSprite
          key={horse.name}
          furColor={horse.furColor}
          maneColor={horse.maneColor}
          saddleColor={horse.saddleColor}
          size={HORSE_SIZE}
        />
      )),
    [horses]
  );

  return (
    <View style={[styles.container, { backgroundColor: bgColor }]}>
      {/* Track surface */}
      <View
        style={[
          styles.trackSurface,
          {
            backgroundColor: surfaceColor,
            top: HORSE_Y_START - 10,
            height: HORSE_Y_SPACING * 10 + 20,
          },
        ]}
      />

      {/* Lane lines */}
      {horses.map((_, i) => (
        <View
          key={`lane-${i}`}
          style={[
            styles.laneLine,
            { top: HORSE_Y_START + i * HORSE_Y_SPACING - 1 },
          ]}
        />
      ))}

      {/* Horses - using Animated.View for native-driven position updates */}
      {horses.map((horse, i) => (
        <Animated.View
          key={horse.name}
          style={[
            styles.horseContainer,
            {
              transform: [
                { translateX: animatedX[i] },
                { translateY: Animated.add(
                    new Animated.Value(HORSE_Y_START + i * HORSE_Y_SPACING),
                    animatedGallop
                  )
                },
              ],
            },
          ]}
        >
          {horseSprites[i]}
        </Animated.View>
      ))}

      {/* Particles */}
      {pConfig.active &&
        particles.map((p, i) => (
          <View
            key={`p-${i}`}
            style={[
              styles.particle,
              {
                left: p.x,
                top: p.y,
                width: pConfig.w,
                height: pConfig.h,
                backgroundColor: pConfig.color,
              },
            ]}
          />
        ))}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    width: "100%",
    height: CANVAS_HEIGHT,
    overflow: "hidden",
  },
  trackSurface: {
    position: "absolute",
    left: 0,
    right: 0,
  },
  laneLine: {
    position: "absolute",
    left: 0,
    right: 0,
    height: 0.5,
    backgroundColor: "rgba(0,0,0,0.15)",
  },
  horseContainer: {
    position: "absolute",
    left: 0,
    top: 0,
    width: HORSE_SIZE,
    height: HORSE_SIZE,
  },
  particle: {
    position: "absolute",
  },
});
