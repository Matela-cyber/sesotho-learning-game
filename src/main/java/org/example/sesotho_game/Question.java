package org.example.sesotho_game;
public class Question {
    private String questionText;
    private String[] options;
    private int correctAnswer;

    private String category;
    private String level;

    public Question(String questionText, String[] options, int correctAnswer,
                    String category, String level) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.category = category;
        this.level = level;
    }

    public boolean isCorrect(int selectedOption) {
        return selectedOption == correctAnswer;
    }

    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public String getCategory() { return category; }
    public String getLevel() { return level; }
}