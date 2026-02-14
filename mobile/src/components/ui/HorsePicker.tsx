import React from "react";
import { ScrollView, TouchableOpacity, StyleSheet } from "react-native";
import { Horse } from "../../models/types";
import HorseCard from "./HorseCard";
import { useGameStore } from "../../state/gameStore";

interface HorsePickerProps {
  horses: Horse[];
  selectedHorse: string | null;
  onSelect: (name: string) => void;
}

export default function HorsePicker({
  horses,
  selectedHorse,
  onSelect,
}: HorsePickerProps) {
  const getOdds = useGameStore((s) => s.getOdds);

  return (
    <ScrollView
      horizontal
      showsHorizontalScrollIndicator={false}
      contentContainerStyle={styles.container}
    >
      {horses.map((horse) => (
        <TouchableOpacity
          key={horse.name}
          onPress={() => onSelect(horse.name)}
          activeOpacity={0.7}
        >
          <HorseCard
            horse={horse}
            odds={getOdds(horse.name)}
            selected={selectedHorse === horse.name}
          />
        </TouchableOpacity>
      ))}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    paddingHorizontal: 12,
    gap: 10,
    paddingVertical: 8,
  },
});
