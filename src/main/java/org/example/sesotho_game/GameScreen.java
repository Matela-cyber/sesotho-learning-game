package org.example.sesotho_game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.util.Duration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
        categoryBackground = new ImageView();
        setCategoryBackground(session.getCategory());

        categoryBackground.setPreserveRatio(false);
        categoryBackground.fitWidthProperty().bind(mainApp.getPrimaryStage().widthProperty());
        categoryBackground.fitHeightProperty().bind(mainApp.getPrimaryStage().heightProperty());

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
        contentBox = new VBox(15);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-background-color: rgba(0,0,0,0.6); -fx-background-radius: 15;");
        contentBox.setPadding(new Insets(20));
        contentBox.setMaxWidth(800);

        categoryTitleLabel = new Label(getCategoryTitle(session.getCategory()));
        categoryTitleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(20);
        infoGrid.setVgap(5);
        infoGrid.setAlignment(Pos.CENTER);

        scoreLabel = new Label("Score: " + session.getScore());
        progressLabel = new Label("Question: " + (session.getCurrentQuestionIndex()+1) + "/" + session.getTotalQuestions());
        difficultyLabel = new Label("Level: " + session.getLevel().toUpperCase());
        timerLabel = new Label("Time: " + timeRemaining);

        String infoStyle = "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;";
        scoreLabel.setStyle(infoStyle);
        progressLabel.setStyle(infoStyle);
        difficultyLabel.setStyle(infoStyle);
        timerLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 14px; -fx-font-weight: bold;");

        infoGrid.add(scoreLabel, 0, 0);
        infoGrid.add(progressLabel, 1, 0);
        infoGrid.add(difficultyLabel, 0, 1);
        infoGrid.add(timerLabel, 1, 1);

        questionLabel = new Label();
        questionLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        questionLabel.setWrapText(true);
        questionLabel.setMaxWidth(750);

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

        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-font-size: 16px; -fx-padding: 8px 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitBtn.setOnAction(e -> checkAnswer());

        HBox controlButtons = new HBox(10);
        controlButtons.setAlignment(Pos.CENTER);

        Button quitButton = new Button("Quit");
        quitButton.setStyle("-fx-font-size: 16px; -fx-padding: 8px 20px; -fx-background-color: #f44336; -fx-text-fill: white;");
        quitButton.setOnAction(e -> confirmQuit());

        controlButtons.getChildren().add(quitButton);

        contentBox.getChildren().addAll(
                categoryTitleLabel,
                infoGrid,
                questionLabel,
                optionsBox,
                submitBtn,
                controlButtons
        );

        backgroundPane.getChildren().add(contentBox);
    }

    private void startTimer() {
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(1),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        timeRemaining--;
                        timerLabel.setText("Time: " + timeRemaining);

                        if (timeRemaining <= 0) {
                            timeline.stop();
                            timeUp();
                        }
                    }
                }
        );

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void timeUp() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Time's Up!");
        alert.setHeaderText(null);
        alert.setContentText("You ran out of time! Moving to next question.");
        alert.showAndWait();

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

        progressLabel.setText("Question: " + (session.getCurrentQuestionIndex()+1) + "/" + session.getTotalQuestions());
    }

    private void checkAnswer() {
        RadioButton selected = (RadioButton)optionsGroup.getSelectedToggle();
        int selectedIndex = -1;

        if (selected != null) {
            selectedIndex = ((VBox)contentBox.getChildren().get(3)).getChildren().indexOf(selected);
        }

        boolean correct = session.answerCurrentQuestion(selectedIndex);

        Alert feedback = new Alert(Alert.AlertType.INFORMATION);
        feedback.setTitle(correct ? "Correct!" : "Incorrect");
        feedback.setHeaderText(correct ? "Ho lokile!" : "Ha ho lokile");
        feedback.setContentText(correct ? "You earned " + session.getLastPoints() + " points!" :
                "Try again next time!");
        feedback.showAndWait();

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
        // Always update career data if not QuickPlay
        if (!session.getPlayerName().equals("QuickPlay")) {
            updateCareerData();
        }

        try {
            // Load appropriate image
            String imagePath = session.isLevelPassed() ?
                    "/org/example/sesotho_game/images/happy popae.png" :
                    "/org/example/sesotho_game/images/angry popae.png";
            Image resultImage = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(resultImage);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            // Create dialog with just "Okay" button
            Alert resultDialog = new Alert(Alert.AlertType.INFORMATION);
            resultDialog.setTitle("Game Over");
            resultDialog.getButtonTypes().setAll(ButtonType.OK);

            // Set content
            String resultText = session.isLevelPassed() ?
                    "Level Passed! \nNext level your career in this category is unlocked \nYour score: " + session.getScore() :
                    "Level Failed\nYour score: " + session.getScore();

            VBox content = new VBox(20, imageView, new Label(resultText));
            content.setAlignment(Pos.CENTER);
            content.setPadding(new Insets(20));
            resultDialog.getDialogPane().setContent(content);

            // Return to main menu when dialog closes
            resultDialog.setOnHidden(e -> mainApp.showMainMenu());
            resultDialog.showAndWait();
        } catch (Exception e) {
            // Fallback if image loading fails
            Alert result = new Alert(Alert.AlertType.INFORMATION);
            result.setTitle("Game Over");
            result.setContentText("Your score: " + session.getScore());
            result.showAndWait();
            mainApp.showMainMenu();
        }
    }
    private void updateCareerData() {
        try {
            CareerData career = CareerData.load(session.getPlayerName());
            if (career == null) {
                career = new CareerData(session.getPlayerName());
            }

            career.updateStats(
                    session.getCategory(),
                    session.getLevel(),
                    session.getScore()
            );

            if (!career.save()) {
                System.err.println("Failed to save career data");
            }
        } catch (Exception e) {
            System.err.println("Error updating career data: " + e.getMessage());
        }
    }

    private void showGameOverDialog() {
        Alert resultDialog = new Alert(Alert.AlertType.INFORMATION);
        resultDialog.setTitle("Game Over");

        String resultText = session.isLevelPassed() ?
                "Level Passed!\nYour score: " + session.getScore() :
                "Level Failed\nYour score: " + session.getScore();

        resultDialog.setHeaderText(resultText);

        // Add only OK button
        resultDialog.getButtonTypes().setAll(ButtonType.OK);

        // Return to main menu after dialog closes
        resultDialog.setOnHidden(e -> mainApp.showMainMenu());
        resultDialog.showAndWait();
    }

    private void confirmQuit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Game");
        alert.setHeaderText("Are you sure you want to quit?");
        alert.setContentText("Your progress will not be saved if you quit now.");

        ButtonType quitButton = new ButtonType("Quit", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(quitButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == quitButton) {
            mainApp.showMainMenu();
        }
    }
}