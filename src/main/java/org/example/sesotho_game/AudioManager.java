package org.example.sesotho_game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AudioManager {
    static MediaPlayer mediaPlayer;
    private static List<String> songPaths = new ArrayList<>();
    private static int currentSongIndex = 0;

    public static void initialize() {
        // Load all audio files from the audios directory
        File audioDir = new File("src/main/resources/org/example/sesotho_game/audios");
        if (audioDir.exists() && audioDir.isDirectory()) {
            File[] files = audioDir.listFiles((dir, name) ->
                    name.endsWith(".mp3") || name.endsWith(".wav"));

            if (files != null) {
                for (File file : files) {
                    songPaths.add(file.toURI().toString());
                }
            }
        }

        // Load default song (first song or specific one)
        if (!songPaths.isEmpty()) {
            playSong(0); // Play first song by default
        }
    }
    public static void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public static MediaPlayer.Status getStatus() {
        return mediaPlayer != null ? mediaPlayer.getStatus() : null;
    }
    public static void playSong(int index) {
        if (index >= 0 && index < songPaths.size()) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            currentSongIndex = index;
            Media media = new Media(songPaths.get(index));
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    public static void togglePlayPause() {
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
        }
    }

    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public static List<String> getSongNames() {
        List<String> names = new ArrayList<>();
        for (String path : songPaths) {
            names.add(new File(path).getName().replace(".mp3", "").replace(".wav", ""));
        }
        return names;
    }

    public static int getCurrentSongIndex() {
        return currentSongIndex;
    }
}