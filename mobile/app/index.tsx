import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  ScrollView,
  Dimensions,
  Alert,
} from "react-native";
import { useRouter } from "expo-router";
import { useGameStore } from "../src/state/gameStore";
import { hasSavedGame, loadGame } from "../src/hooks/useGamePersistence";
import { useAudio } from "../src/hooks/useAudio";
import { TrackTheme } from "../src/models/types";
import { THEME } from "../src/theme/colors";

const TRACKS: { name: TrackTheme; label: string; color: string }[] = [
  { name: "Classic", label: "Classic", color: "#FFD073" },
  { name: "Christmas", label: "Christmas", color: "#FF4444" },
  { name: "Beach", label: "Beach", color: "#44BBFF" },
  { name: "Chocolate", label: "Chocolate", color: "#8B4513" },
  { name: "Rainbow", label: "Rainbow", color: "#FF69B4" },
];

export default function TitleScreen() {
  const [playerName, setPlayerName] = useState("");
  const [selectedTrack, setSelectedTrack] = useState<TrackTheme>("Classic");
  const [savedGameExists, setSavedGameExists] = useState(false);
  const router = useRouter();
  const { newGame, loadGame: loadGameState } = useGameStore();
  const { playTitleMusic, stopMusic } = useAudio();

  useEffect(() => {
    hasSavedGame().then(setSavedGameExists);
    playTitleMusic();
    return () => { stopMusic(); };
  }, []);

  const handleNewGame = () => {
    stopMusic();
    const name = playerName.trim() || "Player";
    newGame(name, selectedTrack);
    router.push("/game");
  };

  const handleLoadGame = async () => {
    stopMusic();
    const saved = await loadGame();
    if (saved) {
      loadGameState(saved);
      router.push("/game");
    } else {
      Alert.alert("No Save", "No saved game found.");
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>RASPBERRY{"\n"}LANES</Text>

      <View style={styles.inputSection}>
        <Text style={styles.label}>NAME</Text>
        <TextInput
          style={styles.input}
          value={playerName}
          onChangeText={setPlayerName}
          placeholder="Enter your name"
          placeholderTextColor="#666"
          autoCapitalize="words"
        />
      </View>

      <View style={styles.trackSection}>
        <Text style={styles.label}>MAP</Text>
        <ScrollView
          horizontal
          showsHorizontalScrollIndicator={false}
          contentContainerStyle={styles.trackList}
        >
          {TRACKS.map((track) => (
            <TouchableOpacity
              key={track.name}
              style={[
                styles.trackItem,
                {
                  borderColor:
                    selectedTrack === track.name
                      ? THEME.gold
                      : "transparent",
                  backgroundColor: track.color,
                },
              ]}
              onPress={() => setSelectedTrack(track.name)}
            >
              <Text style={styles.trackLabel}>{track.label}</Text>
            </TouchableOpacity>
          ))}
        </ScrollView>
      </View>

      <View style={styles.buttonSection}>
        <TouchableOpacity style={styles.button} onPress={handleNewGame}>
          <Text style={styles.buttonText}>NEW GAME</Text>
        </TouchableOpacity>

        {savedGameExists && (
          <TouchableOpacity
            style={[styles.button, styles.loadButton]}
            onPress={handleLoadGame}
          >
            <Text style={styles.buttonText}>LOAD GAME</Text>
          </TouchableOpacity>
        )}
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: THEME.darkNavy,
    alignItems: "center",
    justifyContent: "center",
    padding: 20,
  },
  title: {
    fontSize: 56,
    fontWeight: "bold",
    color: THEME.crimson,
    textAlign: "center",
    marginBottom: 40,
    letterSpacing: 3,
  },
  inputSection: {
    width: "100%",
    maxWidth: 350,
    marginBottom: 20,
  },
  label: {
    color: THEME.crimson,
    fontSize: 20,
    fontWeight: "bold",
    marginBottom: 6,
  },
  input: {
    backgroundColor: THEME.white,
    borderRadius: 8,
    padding: 12,
    fontSize: 16,
    color: THEME.darkNavy,
  },
  trackSection: {
    width: "100%",
    maxWidth: 350,
    marginBottom: 30,
  },
  trackList: {
    gap: 10,
    paddingVertical: 8,
  },
  trackItem: {
    paddingHorizontal: 20,
    paddingVertical: 14,
    borderRadius: 12,
    borderWidth: 3,
    minWidth: 100,
    alignItems: "center",
  },
  trackLabel: {
    color: THEME.white,
    fontWeight: "bold",
    fontSize: 14,
    textShadowColor: "rgba(0,0,0,0.5)",
    textShadowRadius: 2,
    textShadowOffset: { width: 1, height: 1 },
  },
  buttonSection: {
    width: "100%",
    maxWidth: 350,
    gap: 12,
  },
  button: {
    backgroundColor: THEME.gold,
    paddingVertical: 18,
    borderRadius: 12,
    alignItems: "center",
  },
  loadButton: {
    backgroundColor: THEME.crimson,
  },
  buttonText: {
    color: THEME.darkNavy,
    fontSize: 20,
    fontWeight: "bold",
    letterSpacing: 1,
  },
});
