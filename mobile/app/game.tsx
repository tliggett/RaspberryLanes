import React, { useState, useCallback, useRef } from "react";
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  Alert,
  Platform,
  ScrollView,
  Keyboard,
} from "react-native";
import { useRouter } from "expo-router";
import * as Haptics from "expo-haptics";
import { useGameStore } from "../src/state/gameStore";
import { saveGame } from "../src/hooks/useGamePersistence";
import { useAudio } from "../src/hooks/useAudio";
import PlayerHeader from "../src/components/ui/PlayerHeader";
import HorsePicker from "../src/components/ui/HorsePicker";
import BetInput from "../src/components/ui/BetInput";
import BetSlip from "../src/components/ui/BetSlip";
import ResultsList from "../src/components/ui/ResultsList";
import RaceCanvas from "../src/components/race/RaceCanvas";
import { RaceResult } from "../src/models/types";
import { THEME } from "../src/theme/colors";

export default function GameScreen() {
  const [selectedHorse, setSelectedHorse] = useState<string | null>(null);
  const [showResults, setShowResults] = useState(false);
  const [keyboardOpen, setKeyboardOpen] = useState(false);
  const scrollRef = useRef<ScrollView>(null);
  const router = useRouter();

  React.useEffect(() => {
    const showSub = Keyboard.addListener("keyboardDidShow", () => setKeyboardOpen(true));
    const hideSub = Keyboard.addListener("keyboardDidHide", () => setKeyboardOpen(false));
    return () => { showSub.remove(); hideSub.remove(); };
  }, []);

  const {
    horses,
    isRacing,
    setIsRacing,
    startRace,
    finishRace,
    lastRaceResult,
    currentTrack,
    gameOver,
    player,
    getSaveData,
  } = useGameStore();

  const { playTrackMusic, stopMusic } = useAudio();

  const handleRace = useCallback(() => {
    if (isRacing) return;

    // Run simulation synchronously (pre-compute results)
    const result = startRace();

    // Start track music and animation
    playTrackMusic(currentTrack);
    setIsRacing(true);
    Haptics.impactAsync(Haptics.ImpactFeedbackStyle.Heavy);
  }, [isRacing, startRace, setIsRacing, currentTrack, playTrackMusic]);

  const handleRaceFinish = useCallback(() => {
    stopMusic();
    finishRace();
    setShowResults(true);
    setSelectedHorse(null);

    if (lastRaceResult) {
      const hasWin = lastRaceResult.betOutcomes.some((bo) => bo.bet.won);
      if (hasWin) {
        Haptics.notificationAsync(Haptics.NotificationFeedbackType.Success);
      }
    }
  }, [finishRace, lastRaceResult, stopMusic]);

  const handleResultsClose = useCallback(() => {
    setShowResults(false);

    if (gameOver) {
      Alert.alert("Game Over", "You've run out of money!", [
        { text: "New Game", onPress: () => router.replace("/") },
      ]);
    }
  }, [gameOver, router]);

  const handleSave = useCallback(async () => {
    await saveGame(getSaveData());
    Haptics.notificationAsync(Haptics.NotificationFeedbackType.Success);
    Alert.alert("Saved", "Game saved successfully.");
  }, [getSaveData]);

  return (
    <View style={styles.container}>
      {/* Race Canvas - hide when keyboard is open */}
      {!keyboardOpen && (
        <RaceCanvas
          horses={horses}
          isRacing={isRacing}
          track={currentTrack}
          onRaceFinish={handleRaceFinish}
        />
      )}

      <PlayerHeader />

      {/* Bottom section */}
      <ScrollView
        ref={scrollRef}
        style={styles.bottomSection}
        keyboardShouldPersistTaps="handled"
        contentContainerStyle={styles.bottomContent}
      >
        {!isRacing && (
          <>
            <HorsePicker
              horses={horses}
              selectedHorse={selectedHorse}
              onSelect={setSelectedHorse}
            />

            <BetInput selectedHorse={selectedHorse} />
            <BetSlip />
          </>
        )}

        {isRacing && (
          <View style={styles.racingMessage}>
            <Text style={styles.racingText}>RACING...</Text>
          </View>
        )}
      </ScrollView>

      {/* Bottom buttons */}
      <View style={styles.buttonBar}>
        <TouchableOpacity style={styles.saveButton} onPress={handleSave}>
          <Text style={styles.saveText}>SAVE</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={[styles.raceButton, isRacing && styles.buttonDisabled]}
          onPress={handleRace}
          disabled={isRacing}
        >
          <Text style={styles.raceButtonText}>
            {isRacing ? "RACING..." : "RACE!"}
          </Text>
        </TouchableOpacity>
      </View>

      {/* Results modal */}
      {lastRaceResult && (
        <ResultsList
          result={lastRaceResult}
          visible={showResults}
          onClose={handleResultsClose}
        />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: THEME.darkNavy,
  },
  bottomSection: {
    flex: 1,
  },
  bottomContent: {
    paddingBottom: 20,
  },
  racingMessage: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    paddingVertical: 40,
  },
  racingText: {
    color: THEME.gold,
    fontSize: 32,
    fontWeight: "bold",
    letterSpacing: 4,
  },
  buttonBar: {
    flexDirection: "row",
    padding: 12,
    gap: 12,
  },
  saveButton: {
    backgroundColor: "rgba(255,215,0,0.15)",
    paddingVertical: 16,
    paddingHorizontal: 24,
    borderRadius: 12,
    borderWidth: 1,
    borderColor: THEME.gold,
  },
  saveText: {
    color: THEME.gold,
    fontSize: 16,
    fontWeight: "bold",
  },
  raceButton: {
    flex: 1,
    backgroundColor: THEME.gold,
    paddingVertical: 16,
    borderRadius: 12,
    alignItems: "center",
  },
  raceButtonText: {
    color: THEME.darkNavy,
    fontSize: 22,
    fontWeight: "bold",
    letterSpacing: 2,
  },
  buttonDisabled: {
    opacity: 0.5,
  },
});
