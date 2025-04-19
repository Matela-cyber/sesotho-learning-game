package org.example.sesotho_game;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CareerData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String playerName;
    private int score;
    private String currentCategory;
    private String currentLevel;

    public CareerData(String playerName) {
        this.playerName = playerName;
        this.score = 0;
        this.currentCategory = "Lilotho";
        this.currentLevel = "easy";
    }

    public boolean save() {
        try {
            Path dirPath = Paths.get("src/main/resources/org/example/sesotho_game/files");
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            Path filePath = dirPath.resolve(playerName + ".dat");
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(filePath))) {
                oos.writeObject(this);
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error saving career: " + e.getMessage());
            return false;
        }
    }

    public static CareerData load(String name) {
        try {
            Path filePath = Paths.get("src/main/resources/org/example/sesotho_game/files", name + ".dat");
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(filePath))) {
                return (CareerData) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading career: " + e.getMessage());
            return null;
        }
    }

    // Getters and setters
    public String getPlayerName() { return playerName; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public String getCurrentCategory() { return currentCategory; }
    public void setCurrentCategory(String currentCategory) { this.currentCategory = currentCategory; }
    public String getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(String currentLevel) { this.currentLevel = currentLevel; }
}