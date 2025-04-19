package org.example.sesotho_game;

import javafx.scene.media.Media;
import javafx.scene.image.Image;

public class Question {
    private String questionText;
    private String[] options;
    private int correctAnswer;
    private Media videoClip;
    private Image image;
    private String category;
    private String level;

    public Question(String questionText, String[] options, int correctAnswer,
                    String mediaPath, String category, String level, boolean isVideo) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.category = category;
        this.level = level;

        try {
            if (isVideo) {
                this.videoClip = new Media(getClass().getResource(mediaPath).toString());
            } else {
                this.image = new Image(getClass().getResourceAsStream(mediaPath));
            }
        } catch (Exception e) {
            System.err.println("Error loading media: " + e.getMessage());
        }
    }

    public boolean isCorrect(int selectedOption) {
        return selectedOption == correctAnswer;
    }

    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public Media getVideoClip() { return videoClip; }
    public Image getImage() { return image; }
    public String getCategory() { return category; }
    public String getLevel() { return level; }
}