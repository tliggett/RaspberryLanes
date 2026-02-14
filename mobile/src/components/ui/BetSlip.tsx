import React from "react";
import { View, Text, StyleSheet } from "react-native";
import { useGameStore } from "../../state/gameStore";
import { THEME } from "../../theme/colors";

export default function BetSlip() {
  const bets = useGameStore((s) => s.bets);

  if (bets.length === 0) return null;

  return (
    <View style={styles.container}>
      <Text style={styles.title}>ACTIVE BETS</Text>
      {bets.map((bet, i) => (
        <View key={i} style={styles.betRow}>
          <Text style={styles.horseName}>{bet.horseName}</Text>
          <Text style={styles.amount}>${bet.amount}</Text>
          <Text style={styles.odds}>@ {bet.odds}</Text>
        </View>
      ))}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    paddingHorizontal: 16,
    paddingVertical: 8,
  },
  title: {
    color: THEME.crimson,
    fontSize: 14,
    fontWeight: "bold",
    marginBottom: 4,
  },
  betRow: {
    flexDirection: "row",
    gap: 12,
    paddingVertical: 2,
  },
  horseName: {
    color: THEME.white,
    fontSize: 14,
    flex: 1,
  },
  amount: {
    color: THEME.gold,
    fontSize: 14,
    fontWeight: "bold",
  },
  odds: {
    color: "rgba(255,255,255,0.6)",
    fontSize: 14,
  },
});
