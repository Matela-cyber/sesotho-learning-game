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
        String[] categories = {"Lilotho", "Lipapali", "Maele", "Lijo", "Puo"};
        String[] levels = {"easy", "medium", "hard"};

        for (String category : categories) {
            Map<String, CategoryStats> levelStats = new HashMap<>();
            for (String level : levels) {
                CategoryStats stats = new CategoryStats();
                // Unlock easy level by default
                if (level.equals("easy")) {
                    stats.unlocked = true;
                }
                levelStats.put(level, stats);
            }
            categoryStats.put(category, levelStats);
        }
    }

    // In CareerData.java
    public void updateStats(String category, String level, int score) {
        CategoryStats stats = categoryStats.get(category).get(level);

        stats.gamesPlayed++;
        if (score > stats.highScore) {
            stats.highScore = score;
        }
        stats.totalPoints += score;

        // Check if level was passed (70% of max possible score)
        int passingScore = calculatePassingScore(level);
        if (score >= passingScore) {
            stats.passed = true;

            // Unlock next level if this one was passed
            String nextLevel = getNextLevel(level);
            if (nextLevel != null) {
                // Only unlock if not already unlocked
                if (!categoryStats.get(category).get(nextLevel).unlocked) {
                    categoryStats.get(category).get(nextLevel).unlocked = true;
                    System.out.println("Unlocked " + category + " - " + nextLevel); // Debug log
                }
            }
        }

        totalScore += score;
        currentCategory = category;
        currentLevel = level;
    }

    private String getNextLevel(String currentLevel) {
        switch (currentLevel.toLowerCase()) {
            case "easy": return "medium";
            case "medium": return "hard";
            case "hard": return null;
            default: return null;
        }
    }
    private int calculatePassingScore(String level) {
        // 70% of max possible score for the level
        int questionsPerLevel = 5; // Assuming 5 questions per level
        int pointsPerQuestion = 0;

        switch (level.toLowerCase()) {
            case "easy": pointsPerQuestion = 1; break;
            case "medium": pointsPerQuestion = 2; break;
            case "hard": pointsPerQuestion = 3; break;
        }

        return (int)(questionsPerLevel * pointsPerQuestion * 0.7);
    }

    public String getUnlockRequirement(String category, String nextLevel) {
        // Determine which level needs to be passed to unlock the requested level
        String requiredLevel = "";
        switch (nextLevel.toLowerCase()) {
            case "medium":
                requiredLevel = "easy";
                break;
            case "hard":
                requiredLevel = "medium";
                break;
            default:
                return "This level cannot be unlocked.";
        }

        // Check if the required level has been passed
        if (categoryStats.containsKey(category) &&
                categoryStats.get(category).containsKey(requiredLevel)) {
            if (!categoryStats.get(category).get(requiredLevel).passed) {
                int passingScore = calculatePassingScore(requiredLevel);
                return String.format("You need to pass the %s level first (score at least %d points).",
                        requiredLevel, passingScore);
            }
        }

        return "This level should be unlocked. If not, try completing the previous level again.";
    }
    public static class CategoryStats implements Serializable {
        private static final long serialVersionUID = 1L;
        public int gamesPlayed = 0;
        public int highScore = 0;
        public int totalPoints = 0;
        public boolean unlocked = false;
        public boolean passed = false;

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

    // Helper method to check if a level is unlocked
    public boolean isLevelUnlocked(String category, String level) {
        if (!categoryStats.containsKey(category)) return false;
        if (!categoryStats.get(category).containsKey(level)) return false;
        return categoryStats.get(category).get(level).unlocked;
    }

    // Helper method to check if a level was passed
    public boolean isLevelPassed(String category, String level) {
        if (!categoryStats.containsKey(category)) return false;
        if (!categoryStats.get(category).containsKey(level)) return false;
        return categoryStats.get(category).get(level).passed;
    }
}