package org.example.sesotho_game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSession {
    private String playerName;
    private String category;
    private String level;
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private boolean levelPassed;
    private int lastPoints;

    public GameSession(String playerName, String category, String level, List<Question> questionPool) {
        this.playerName = playerName;
        this.category = category;
        this.level = level;
        this.questions = selectRandomQuestions(questionPool, 5);
        this.currentQuestionIndex = 0;
        this.score = 0; // Always starts at zero for new sessions
        this.levelPassed = false;
        this.lastPoints = 0;
    }

    private List<Question> selectRandomQuestions(List<Question> pool, int count) {
        List<Question> filtered = new ArrayList<>();
        for (Question q : pool) {
            if (q.getCategory().equalsIgnoreCase(category) && q.getLevel().equalsIgnoreCase(level)) {
                filtered.add(q);
            }
        }
        Collections.shuffle(filtered);
        return filtered.subList(0, Math.min(count, filtered.size()));
    }

    public Question getCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public boolean answerCurrentQuestion(int selectedOption) {
        boolean correct = getCurrentQuestion().isCorrect(selectedOption);
        if (correct) {
            lastPoints = calculatePointsForLevel();
            score += lastPoints;
        } else {
            lastPoints = 0;
        }
        currentQuestionIndex++;

        if (currentQuestionIndex >= questions.size()) {
            levelPassed = (score >= calculatePassingScore());
        }
        return correct;
    }

    private int calculatePointsForLevel() {
        switch (level.toLowerCase()) {
            case "easy": return 1;
            case "medium": return 2;
            case "hard": return 3;
            default: return 1;
        }
    }

    private int calculatePassingScore() {
        return (int) (questions.size() * 0.7 * calculatePointsForLevel());
    }

    public boolean hasMoreQuestions() {
        return currentQuestionIndex < questions.size();
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLastPoints() {
        return lastPoints;
    }

    public boolean isLevelPassed() {
        return levelPassed;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCategory() {
        return category;
    }

    public String getLevel() {
        return level;
    }

    // New method to get the final score for career updates
    public int getFinalScore() {
        return score; // Returns the score at game end
    }
}