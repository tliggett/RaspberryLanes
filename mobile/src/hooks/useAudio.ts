import { useRef, useCallback, useEffect } from "react";
import { Audio } from "expo-av";
import { TrackTheme } from "../models/types";

const TRACK_AUDIO: Record<TrackTheme, number> = {
  Classic: require("../assets/audio/theme.wav"),
  Christmas: require("../assets/audio/bells.wav"),
  Beach: require("../assets/audio/surfer.wav"),
  Chocolate: require("../assets/audio/chocolate.wav"),
  Rainbow: require("../assets/audio/rroad.wav"),
};

// Title screen uses the classic theme
const TITLE_AUDIO = require("../assets/audio/theme.wav");

export function useAudio() {
  const soundRef = useRef<Audio.Sound | null>(null);

  const configure = useCallback(async () => {
    await Audio.setAudioModeAsync({
      playsInSilentModeIOS: true,
      staysActiveInBackground: false,
    });
  }, []);

  useEffect(() => {
    configure();
  }, []);

  const playMusic = useCallback(async (asset: number, loop = true) => {
    try {
      if (soundRef.current) {
        await soundRef.current.stopAsync();
        await soundRef.current.unloadAsync();
        soundRef.current = null;
      }

      const { sound } = await Audio.Sound.createAsync(asset, {
        isLooping: loop,
        volume: 0.7,
      });
      soundRef.current = sound;
      await sound.playAsync();
    } catch (e) {
      console.warn("Audio playback failed:", e);
    }
  }, []);

  const playTitleMusic = useCallback(async () => {
    await playMusic(TITLE_AUDIO);
  }, [playMusic]);

  const playTrackMusic = useCallback(
    async (track: TrackTheme) => {
      await playMusic(TRACK_AUDIO[track]);
    },
    [playMusic]
  );

  const stopMusic = useCallback(async () => {
    try {
      if (soundRef.current) {
        await soundRef.current.stopAsync();
        await soundRef.current.unloadAsync();
        soundRef.current = null;
      }
    } catch (e) {
      console.warn("Audio stop failed:", e);
    }
  }, []);

  useEffect(() => {
    return () => {
      if (soundRef.current) {
        soundRef.current.unloadAsync();
      }
    };
  }, []);

  return { playTitleMusic, playTrackMusic, stopMusic };
}
