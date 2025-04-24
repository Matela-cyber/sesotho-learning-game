package org.example.sesotho_game;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.io.*;
import java.nio.file.*;

public class GameScreen extends BorderPane {
    private GameSession session;
    private GameLandingPage mainApp;

    // UI Components
    private Label categoryTitleLabel;
    private Label difficultyLabel;
    private Label questionLabel;
    private ToggleGroup optionsGroup;
    private Label scoreLabel;
    private Label progressLabel;
    private Label timerLabel;

    // Timer components
    private Timeline timeline;
    private int timeRemaining;
    private int initialTime;

    // Background components
    private ImageView categoryBackground;
    private StackPane backgroundPane;
    private VBox contentBox;

    public GameScreen(GameSession session, GameLandingPage mainApp) {
        this.session = session;
        this.mainApp = mainApp;
        loadTimerSettings();
        setupBackground();
        setupUI();
        showQuestion();
        startTimer();
    }

    private void loadTimerSettings() {
        try {
            Path filePath = Paths.get("time.dat");
            if (Files.exists(filePath)) {
                String timeString = Files.readString(filePath).trim();
                initialTime = Integer.parseInt(timeString);
            } else {
                initialTime = 30; // Default time if file doesn't exist
            }
        } catch (Exception e) {
            System.err.println("Error loading timer settings: " + e.getMessage());
            initialTime = 30; // Default time on error
        }
        timeRemaining = initialTime;
    }

    private void setupBackground() {
        // Create the background image view
        categoryBackground = new ImageView();
        setCategoryBackground(session.getCategory());

        // Make background fill the entire space
        categoryBackground.setPreserveRatio(false);
        categoryBackground.fitWidthProperty().bind(mainApp.getPrimaryStage().widthProperty());
        categoryBackground.fitHeightProperty().bind(mainApp.getPrimaryStage().heightProperty());

        // Create a stack pane to hold background and content
        backgroundPane = new StackPane();
        backgroundPane.getChildren().add(categoryBackground);
        this.setCenter(backgroundPane);
    }

    private void setCategoryBackground(String category) {
        String imagePath = getBackgroundPath(category);

        try {
            Image bgImage = new Image(getClass().getResourceAsStream(imagePath));
            categoryBackground.setImage(bgImage);
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            // Fallback to default if error occurs
            try {
                Image defaultBg = new Image(getClass().getResourceAsStream(
                        "/org/example/sesotho_game/images/bg_default.png"));
                categoryBackground.setImage(defaultBg);
            } catch (Exception ex) {
                System.err.println("Could not load default background: " + ex.getMessage());
            }
        }
    }

    private String getBackgroundPath(String category) {
        switch(category.toLowerCase()) {
            case "lilotho": return "/org/example/sesotho_game/images/bg_lilotho.png";
            case "lipapali": return "/org/example/sesotho_game/images/bg_lipapali.png";
            case "maele": return "/org/example/sesotho_game/images/bg_maele.png";
            case "lijo": return "/org/example/sesotho_game/images/bg_lijo.png";
            case "puo": return "/org/example/sesotho_game/images/bg_puo.png";
            default: return "/org/example/sesotho_game/images/bg_default.png";
        }
    }

    private String getCategoryTitle(String category) {
        switch(category.toLowerCase()) {
            case "lilotho": return "LILOTHO";
            case "lipapali": return "LIPAPALI";
            case "maele": return "MAELE";
            case "lijo": return "LIJO";
            case "puo": return "PUO";
            default: return "SESOTHO GAME";
        }
    }

    private void setupUI() {
        // Create content container with semi-transparent background
        contentBox = new VBox(15);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-background-color: rgba(0,0,0,0.6); -fx-background-radius: 15;");
        contentBox.setPadding(new Insets(20));
        contentBox.setMaxWidth(800);

        // Category title
        categoryTitleLabel = new Label(getCategoryTitle(session.getCategory()));
        categoryTitleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        // Top info bar (score, progress, difficulty and timer)
        HBox topInfoBar = new HBox(20);
        scoreLabel = new Label("Score: " + session.getScore());
        progressLabel = new Label("Question: " + (session.getCurrentQuestionIndex()+1) + "/" + session.getTotalQuestions());
        difficultyLabel = new Label("Level: " + session.getLevel().toUpperCase());
        timerLabel = new Label("Time: " + timeRemaining);

        // Style all info labels
        String infoStyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";
        scoreLabel.setStyle(infoStyle);
        progressLabel.setStyle(infoStyle);
        difficultyLabel.setStyle(infoStyle);
        timerLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 14px; -fx-font-weight: bold;");

        // Create a grid for better organization of info
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(20);
        infoGrid.setVgap(5);
        infoGrid.setAlignment(Pos.CENTER);

        // First row
        infoGrid.add(scoreLabel, 0, 0);
        infoGrid.add(progressLabel, 1, 0);
        // Second row
        infoGrid.add(difficultyLabel, 0, 1);
        infoGrid.add(timerLabel, 1, 1);

        // Question display
        questionLabel = new Label();
        questionLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        questionLabel.setWrapText(true);
        questionLabel.setMaxWidth(750);

        // Answer options
        VBox optionsBox = new VBox(10);
        optionsGroup = new ToggleGroup();

        for (int i = 0; i < 4; i++) {
            RadioButton option = new RadioButton();
            option.setToggleGroup(optionsGroup);
            option.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
            option.setWrapText(true);
            option.setMaxWidth(750);
            optionsBox.getChildren().add(option);
        }

        // Submit button
        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-font-size: 16px; -fx-padding: 8px 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitBtn.setOnAction(e -> checkAnswer());

        // Add all components to content box
        contentBox.getChildren().addAll(
                categoryTitleLabel,
                infoGrid,  // Using the grid instead of the old HBox
                questionLabel,
                optionsBox,
                submitBtn
        );

        backgroundPane.getChildren().add(contentBox);
    }

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining--;
            timerLabel.setText("Time: " + timeRemaining);

            if (timeRemaining <= 0) {
                timeline.stop();
                timeUp();
            }
        }));  // Added missing parenthesis here
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void timeUp() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Time's Up!");
        alert.setHeaderText(null);
        alert.setContentText("You ran out of time! Moving to next question.");
        alert.showAndWait();

        // Automatically submit empty answer
        session.answerCurrentQuestion(-1);

        if (session.hasMoreQuestions()) {
            resetTimer();
            showQuestion();
        } else {
            endGameSession();
        }
    }

    private void resetTimer() {
        timeRemaining = initialTime;
        timerLabel.setText("Time: " + timeRemaining);
        timeline.playFromStart();
    }

    private void showQuestion() {
        Question current = session.getCurrentQuestion();
        if (current == null) return;

        questionLabel.setText(current.getQuestionText());

        // Set options
        int optionIndex = 0;
        for (javafx.scene.Node node : ((VBox)contentBox.getChildren().get(3)).getChildren()) {
            if (node instanceof RadioButton) {
                RadioButton rb = (RadioButton)node;
                rb.setSelected(false);
                if (optionIndex < current.getOptions().length) {
                    rb.setText(current.getOptions()[optionIndex++]);
                }
            }
        }

        // Update progress
        progressLabel.setText("Attempt: " + (session.getCurrentQuestionIndex()+1) + "/" + session.getTotalQuestions());
    }

    private void checkAnswer() {
        RadioButton selected = (RadioButton)optionsGroup.getSelectedToggle();
        int selectedIndex = -1;

        if (selected != null) {
            selectedIndex = ((VBox)contentBox.getChildren().get(3)).getChildren().indexOf(selected);
        }

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
            resetTimer();
            showQuestion();
        } else {
            timeline.stop();
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
                    career.updateStats(
                            session.getCategory(),
                            session.getLevel(),
                            session.getScore()
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