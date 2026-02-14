import React, { useState } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
} from "react-native";
import { useGameStore } from "../../state/gameStore";
import { THEME } from "../../theme/colors";
import {
  SPECIAL_RASPBERRY,
  SPECIAL_TRUMP,
  SPECIAL_BOLT,
} from "../../utils/constants";

interface BetInputProps {
  selectedHorse: string | null;
}

export default function BetInput({ selectedHorse }: BetInputProps) {
  const [betText, setBetText] = useState("");
  const { placeBet, addSpecialHorse, player } = useGameStore();

  const handlePlaceBet = () => {
    const text = betText.trim().toLowerCase();

    // Easter egg codes
    if (text === SPECIAL_RASPBERRY) {
      addSpecialHorse(1);
      setBetText("");
      return;
    }
    if (text === SPECIAL_TRUMP) {
      addSpecialHorse(3);
      setBetText("");
      return;
    }
    if (text === SPECIAL_BOLT) {
      addSpecialHorse(4);
      setBetText("");
      return;
    }

    if (!selectedHorse) return;

    const amount = parseInt(betText, 10);
    if (isNaN(amount) || amount <= 0) return;

    placeBet(selectedHorse, amount);
    setBetText("");
  };

  const quickBets = [10, 25, 50];

  return (
    <View style={styles.container}>
      <View style={styles.inputRow}>
        <Text style={styles.dollar}>$</Text>
        <TextInput
          style={styles.input}
          value={betText}
          onChangeText={setBetText}
          placeholder="Bet amount"
          placeholderTextColor="#666"
          keyboardType="default"
          returnKeyType="done"
          onSubmitEditing={handlePlaceBet}
        />
        <TouchableOpacity
          style={[
            styles.placeButton,
            !selectedHorse && styles.buttonDisabled,
          ]}
          onPress={handlePlaceBet}
          disabled={!selectedHorse && !betText.trim()}
        >
          <Text style={styles.placeButtonText}>BET</Text>
        </TouchableOpacity>
      </View>

      <View style={styles.quickBets}>
        {quickBets.map((amount) => (
          <TouchableOpacity
            key={amount}
            style={[
              styles.quickBetButton,
              amount > player.cash && styles.buttonDisabled,
            ]}
            onPress={() => {
              if (selectedHorse && amount <= player.cash) {
                placeBet(selectedHorse, amount);
              }
            }}
            disabled={!selectedHorse || amount > player.cash}
          >
            <Text style={styles.quickBetText}>${amount}</Text>
          </TouchableOpacity>
        ))}
        <TouchableOpacity
          style={[
            styles.quickBetButton,
            styles.allInButton,
            player.cash <= 0 && styles.buttonDisabled,
          ]}
          onPress={() => {
            if (selectedHorse && player.cash > 0) {
              placeBet(selectedHorse, player.cash);
            }
          }}
          disabled={!selectedHorse || player.cash <= 0}
        >
          <Text style={styles.quickBetText}>ALL IN</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    paddingHorizontal: 16,
    gap: 8,
  },
  inputRow: {
    flexDirection: "row",
    alignItems: "center",
    gap: 8,
  },
  dollar: {
    color: THEME.gold,
    fontSize: 24,
    fontWeight: "bold",
  },
  input: {
    flex: 1,
    backgroundColor: "#FFFFFF",
    borderRadius: 8,
    padding: 12,
    fontSize: 20,
    fontWeight: "bold",
    color: "#000000",
    borderWidth: 2,
    borderColor: THEME.gold,
    minHeight: 48,
  },
  placeButton: {
    backgroundColor: THEME.gold,
    paddingHorizontal: 20,
    paddingVertical: 12,
    borderRadius: 8,
  },
  placeButtonText: {
    color: THEME.darkNavy,
    fontWeight: "bold",
    fontSize: 16,
  },
  buttonDisabled: {
    opacity: 0.4,
  },
  quickBets: {
    flexDirection: "row",
    gap: 8,
  },
  quickBetButton: {
    flex: 1,
    backgroundColor: "rgba(255,215,0,0.2)",
    paddingVertical: 8,
    borderRadius: 6,
    alignItems: "center",
    borderWidth: 1,
    borderColor: THEME.gold,
  },
  allInButton: {
    borderColor: THEME.crimson,
    backgroundColor: "rgba(220,20,60,0.2)",
  },
  quickBetText: {
    color: THEME.gold,
    fontWeight: "bold",
    fontSize: 13,
  },
});
