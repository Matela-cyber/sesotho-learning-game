package org.example.sesotho_game;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.ImageView;
import javafx.geometry.*;
import javafx.scene.text.*;

public class GameScreen extends BorderPane {
    private GameSession session;
    private GameLandingPage mainApp;

    private Label questionLabel;
    private ImageView imageView;
    private MediaView mediaView;
    private ToggleGroup optionsGroup;
    private Label scoreLabel;
    private Label progressLabel;

    public GameScreen(GameSession session, GameLandingPage mainApp) {
        this.session = session;
        this.mainApp = mainApp;
        setupUI();
        showQuestion();
    }

    private void setupUI() {
        // Top: Score and progress
        HBox topBar = new HBox(20);
        scoreLabel = new Label("Score: " + session.getScore());
        progressLabel = new Label("Question 1 of " + session.getTotalQuestions());
        topBar.getChildren().addAll(scoreLabel, progressLabel);
        topBar.setAlignment(Pos.CENTER);
        setTop(topBar);

        // Center: Question and media
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);

        questionLabel = new Label();
        questionLabel.setWrapText(true);
        questionLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);

        mediaView = new MediaView();
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(400);

        centerBox.getChildren().addAll(questionLabel, imageView, mediaView);
        setCenter(centerBox);

        // Bottom: Answer options
        VBox optionsBox = new VBox(10);
        optionsGroup = new ToggleGroup();

        for (int i = 0; i < 4; i++) {
            RadioButton option = new RadioButton();
            option.setToggleGroup(optionsGroup);
            option.setStyle("-fx-font-size: 14px;");
            optionsBox.getChildren().add(option);
        }

        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(e -> checkAnswer());
        submitBtn.setStyle("-fx-font-size: 14px; -fx-padding: 5px 15px;");

        optionsBox.getChildren().add(submitBtn);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(10));
        setBottom(optionsBox);
    }

    private void showQuestion() {
        Question current = session.getCurrentQuestion();
        if (current == null) return;

        questionLabel.setText(current.getQuestionText());

        // Clear previous media
        imageView.setImage(null);
        mediaView.setMediaPlayer(null);

        // Show new media
        if (current.getVideoClip() != null) {
            MediaPlayer player = new MediaPlayer(current.getVideoClip());
            mediaView.setMediaPlayer(player);
            player.play();
        } else if (current.getImage() != null) {
            imageView.setImage(current.getImage());
        }

        // Set options
        int optionIndex = 0;
        for (javafx.scene.Node node : ((VBox)getBottom()).getChildren()) {
            if (node instanceof RadioButton) {
                RadioButton rb = (RadioButton)node;
                rb.setSelected(false);
                if (optionIndex < current.getOptions().length) {
                    rb.setText(current.getOptions()[optionIndex++]);
                }
            }
        }

        // Update progress
        progressLabel.setText("Question " + (session.getCurrentQuestionIndex()+1) +
                " of " + session.getTotalQuestions());
    }

    private void checkAnswer() {
        RadioButton selected = (RadioButton)optionsGroup.getSelectedToggle();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Answer Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an answer before submitting.");
            alert.showAndWait();
            return;
        }

        int selectedIndex = ((VBox)getBottom()).getChildren().indexOf(selected);
        boolean correct = session.answerCurrentQuestion(selectedIndex);

        // Show feedback
        Alert feedback = new Alert(Alert.AlertType.INFORMATION);
        feedback.setTitle(correct ? "Correct!" : "Incorrect");
        feedback.setHeaderText(correct ? "Ho lokile!" : "Ha ho lokile");
        feedback.setContentText(correct ? "You earned " + session.getLastPoints() + " points!" :
                "Try again next time!");
        feedback.showAndWait();

        // Update score
        scoreLabel.setText("Score: " + session.getScore());

        if (session.hasMoreQuestions()) {
            showQuestion();
        } else {
            endGameSession();
        }
    }

    private void endGameSession() {
        Alert result = new Alert(Alert.AlertType.INFORMATION);
        result.setTitle("Game Over");

        if (session.isLevelPassed()) {
            result.setHeaderText("Level Passed!");
            result.setContentText("Congratulations! Your score: " + session.getScore());

            if (!session.getPlayerName().equals("QuickPlay")) {
                CareerData career = CareerData.load(session.getPlayerName());
                if (career != null) {
                    // Only update with THIS game's score (session.getScore())
                    career.updateStats(
                            session.getCategory(),
                            session.getLevel(),
                            session.getScore() // This is just the current game's score
                    );
                    career.save();
                }
            }
        } else {
            result.setHeaderText("Level Failed");
            result.setContentText("Try again! Your score: " + session.getScore());
        }

        result.showAndWait();
        mainApp.showMainMenu();
    }
}