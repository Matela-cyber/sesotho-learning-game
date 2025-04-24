package org.example.sesotho_game;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CareerData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String playerName;
    private int totalScore;
    private String currentCategory;
    private String currentLevel;
    private Map<String, Map<String, CategoryStats>> categoryStats = new HashMap<>();

    public CareerData(String playerName) {
        this.playerName = playerName;
        this.totalScore = 0;
        this.currentCategory = "Lilotho";
        this.currentLevel = "easy";
        initializeStats();
    }

    private void initializeStats() {
        String[] categories = {"Lilotho", "Lipapali", "Maele", "Lijo", "Puo"}; // Updated categories
        String[] levels = {"easy", "medium", "hard"};

        for (String category : categories) {
            Map<String, CategoryStats> levelStats = new HashMap<>();
            for (String level : levels) {
                levelStats.put(level, new CategoryStats());
            }
            categoryStats.put(category, levelStats);
        }
    }

    public void updateStats(String category, String level, int score) {
        CategoryStats stats = categoryStats.get(category).get(level);

        stats.gamesPlayed++;  // Increment game count

        // Update high score if current score is better
        if (score > stats.highScore) {
            stats.highScore = score;
        }

        // Add to total points (for average calculation)
        stats.totalPoints += score;

        // Update global total score
        totalScore += score;

        currentCategory = category;
        currentLevel = level;
    }

    public static class CategoryStats implements Serializable {
        private static final long serialVersionUID = 1L;
        public int gamesPlayed = 0;
        public int highScore = 0;
        public int totalPoints = 0; // New field for calculating average
        public boolean unlocked = false;

        // Calculate average score on demand
        public double getAverageScore() {
            return gamesPlayed > 0 ? (double) totalPoints / gamesPlayed : 0.0;
        }
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

    // Getters
    public String getPlayerName() { return playerName; }
    public int getTotalScore() { return totalScore; }
    public String getCurrentCategory() { return currentCategory; }
    public String getCurrentLevel() { return currentLevel; }
    public Map<String, Map<String, CategoryStats>> getCategoryStats() { return categoryStats; }
}