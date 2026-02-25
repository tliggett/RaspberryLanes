import React from "react";
import { View, Text, StyleSheet } from "react-native";
import { Horse } from "../../models/types";
import { timeToString } from "../../utils/formatting";
import { THEME } from "../../theme/colors";

interface HorseCardProps {
  horse: Horse;
  odds: number;
  selected?: boolean;
}

export default function HorseCard({ horse, odds, selected }: HorseCardProps) {
  return (
    <View
      style={[styles.container, selected && styles.selected]}
    >
      <View style={styles.colorRow}>
        <View style={[styles.colorSwatch, { backgroundColor: horse.furColor }]} />
        <View style={[styles.colorSwatch, { backgroundColor: horse.maneColor }]} />
        <View style={[styles.colorSwatch, { backgroundColor: horse.saddleColor }]} />
      </View>

      <Text style={styles.name} numberOfLines={1}>
        {horse.name}
        {horse.places.filter((p) => p === 1).length > 0 &&
          " " + "\uD83C\uDFC6".repeat(horse.places.filter((p) => p === 1).length)}
      </Text>

      <View style={styles.statsRow}>
        <Text style={styles.stat}>Age {horse.age}</Text>
        <Text style={styles.odds}>{odds}</Text>
      </View>

      {horse.times.length > 0 && (
        <View style={styles.historyRow}>
          {horse.times.slice(-3).map((t, i) => (
            <Text key={i} style={styles.historyTime}>
              {timeToString(t)}
            </Text>
          ))}
        </View>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: "rgba(0,0,80,0.8)",
    borderRadius: 10,
    padding: 10,
    width: 140,
    borderWidth: 2,
    borderColor: "transparent",
  },
  selected: {
    borderColor: THEME.gold,
  },
  colorRow: {
    flexDirection: "row",
    gap: 4,
    marginBottom: 6,
  },
  colorSwatch: {
    width: 16,
    height: 16,
    borderRadius: 4,
    borderWidth: 1,
    borderColor: "rgba(255,255,255,0.3)",
  },
  name: {
    color: THEME.white,
    fontSize: 14,
    fontWeight: "bold",
    marginBottom: 4,
  },
  statsRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  stat: {
    color: THEME.crimson,
    fontSize: 12,
  },
  odds: {
    color: THEME.gold,
    fontSize: 18,
    fontWeight: "bold",
  },
  historyRow: {
    flexDirection: "row",
    gap: 6,
    marginTop: 4,
  },
  historyTime: {
    color: "rgba(255,255,255,0.6)",
    fontSize: 10,
  },
});
