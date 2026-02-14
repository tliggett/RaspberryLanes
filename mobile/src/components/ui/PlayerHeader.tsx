import React from "react";
import { View, Text, StyleSheet } from "react-native";
import { useGameStore } from "../../state/gameStore";
import { formatCash } from "../../utils/formatting";
import { THEME } from "../../theme/colors";

export default function PlayerHeader() {
  const { player, raceNumber } = useGameStore();

  return (
    <View style={styles.container}>
      <Text style={styles.name}>{player.name}</Text>
      <Text style={styles.cash}>{formatCash(player.cash)}</Text>
      <Text style={styles.race}>Race #{raceNumber + 1}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    paddingHorizontal: 16,
    paddingVertical: 10,
    backgroundColor: "rgba(0,0,0,0.3)",
  },
  name: {
    color: THEME.gold,
    fontSize: 20,
    fontWeight: "bold",
  },
  cash: {
    color: THEME.gold,
    fontSize: 22,
    fontWeight: "bold",
  },
  race: {
    color: THEME.crimson,
    fontSize: 14,
    fontWeight: "600",
  },
});
