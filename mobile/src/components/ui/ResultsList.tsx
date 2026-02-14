import React from "react";
import {
  View,
  Text,
  StyleSheet,
  Modal,
  TouchableOpacity,
  ScrollView,
} from "react-native";
import { RaceResult } from "../../models/types";
import { numToPlace, timeToString, formatCash } from "../../utils/formatting";
import { THEME } from "../../theme/colors";

interface ResultsListProps {
  result: RaceResult;
  visible: boolean;
  onClose: () => void;
}

export default function ResultsList({
  result,
  visible,
  onClose,
}: ResultsListProps) {
  return (
    <Modal
      visible={visible}
      transparent
      animationType="slide"
      onRequestClose={onClose}
    >
      <View style={styles.overlay}>
        <View style={styles.modal}>
          <Text style={styles.title}>RACE RESULTS</Text>

          <ScrollView style={styles.scroll}>
            {/* Winner highlight */}
            <View style={styles.winnerSection}>
              <Text style={styles.winnerLabel}>WINNER</Text>
              <Text style={styles.winnerName}>
                {result.placements[0].horse.name}
              </Text>
              <Text style={styles.winnerTime}>
                {timeToString(result.placements[0].time)}
              </Text>
            </View>

            {/* All placements */}
            <View style={styles.section}>
              {result.placements.map((p) => (
                <View key={p.place} style={styles.placementRow}>
                  <Text style={styles.place}>{numToPlace(p.place)}</Text>
                  <Text
                    style={[
                      styles.horseName,
                      p.retired && styles.retired,
                    ]}
                    numberOfLines={1}
                  >
                    {p.horse.name}
                  </Text>
                  <Text style={styles.time}>
                    {p.retired
                      ? "RETIRED"
                      : timeToString(p.time)}
                  </Text>
                </View>
              ))}
            </View>

            {/* Bet outcomes */}
            {result.betOutcomes.length > 0 && (
              <View style={styles.section}>
                <Text style={styles.sectionTitle}>BET RESULTS</Text>
                {result.betOutcomes.map((bo, i) => (
                  <View key={i} style={styles.betRow}>
                    <Text
                      style={[
                        styles.betHorse,
                        bo.bet.won ? styles.betWon : styles.betLost,
                      ]}
                    >
                      {bo.bet.horseName}
                    </Text>
                    <Text
                      style={
                        bo.bet.won ? styles.betWon : styles.betLost
                      }
                    >
                      {bo.bet.won
                        ? `+${formatCash(bo.payout)}`
                        : `-${formatCash(bo.bet.amount)}`}
                    </Text>
                  </View>
                ))}
                <Text style={styles.totalEarnings}>
                  Total: {formatCash(result.totalEarnings)}
                </Text>
              </View>
            )}
          </ScrollView>

          <TouchableOpacity style={styles.closeButton} onPress={onClose}>
            <Text style={styles.closeText}>CONTINUE</Text>
          </TouchableOpacity>
        </View>
      </View>
    </Modal>
  );
}

const styles = StyleSheet.create({
  overlay: {
    flex: 1,
    backgroundColor: "rgba(0,0,0,0.7)",
    justifyContent: "flex-end",
  },
  modal: {
    backgroundColor: THEME.darkNavy,
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
    padding: 20,
    maxHeight: "80%",
    borderWidth: 2,
    borderColor: THEME.gold,
    borderBottomWidth: 0,
  },
  title: {
    color: THEME.crimson,
    fontSize: 24,
    fontWeight: "bold",
    textAlign: "center",
    marginBottom: 16,
  },
  scroll: {
    maxHeight: 400,
  },
  winnerSection: {
    alignItems: "center",
    marginBottom: 16,
    paddingBottom: 16,
    borderBottomWidth: 1,
    borderBottomColor: "rgba(255,215,0,0.3)",
  },
  winnerLabel: {
    color: THEME.crimson,
    fontSize: 14,
    fontWeight: "bold",
  },
  winnerName: {
    color: THEME.gold,
    fontSize: 28,
    fontWeight: "bold",
  },
  winnerTime: {
    color: THEME.white,
    fontSize: 18,
  },
  section: {
    marginBottom: 16,
  },
  sectionTitle: {
    color: THEME.crimson,
    fontSize: 16,
    fontWeight: "bold",
    marginBottom: 8,
  },
  placementRow: {
    flexDirection: "row",
    alignItems: "center",
    paddingVertical: 3,
  },
  place: {
    color: THEME.gold,
    fontSize: 14,
    fontWeight: "bold",
    width: 40,
  },
  horseName: {
    color: THEME.white,
    fontSize: 14,
    flex: 1,
  },
  retired: {
    color: THEME.crimson,
  },
  time: {
    color: "rgba(255,255,255,0.7)",
    fontSize: 14,
    width: 80,
    textAlign: "right",
  },
  betRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    paddingVertical: 2,
  },
  betHorse: {
    fontSize: 14,
    flex: 1,
  },
  betWon: {
    color: "#4DFF88",
    fontWeight: "bold",
  },
  betLost: {
    color: THEME.crimson,
  },
  totalEarnings: {
    color: THEME.gold,
    fontSize: 16,
    fontWeight: "bold",
    textAlign: "right",
    marginTop: 8,
  },
  closeButton: {
    backgroundColor: THEME.gold,
    paddingVertical: 14,
    borderRadius: 10,
    alignItems: "center",
    marginTop: 12,
  },
  closeText: {
    color: THEME.darkNavy,
    fontSize: 18,
    fontWeight: "bold",
  },
});
