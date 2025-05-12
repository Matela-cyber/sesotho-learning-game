package org.example.sesotho_game;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MainMenu extends BorderPane {
    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 50;
    private GridPane careerGrid;
    private TextArea infoText;
    private GameLandingPage mainApp;
    private String activeButton = "";
    private List<String> careerNames = new ArrayList<>();

    public MainMenu(GameLandingPage mainApp) {
        this.mainApp = mainApp;
        loadCareerNames();
        setupBackground();
        setupLeftMenu();
        setupContentArea();
    }

    private void loadCareerNames() {
        try {
            Path dirPath = Paths.get("src/main/resources/org/example/sesotho_game/files");
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.dat")) {
                for (Path file : stream) {
                    String name = file.getFileName().toString().replace(".dat", "");
                    careerNames.add(name);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading career names: " + e.getMessage());
        }
    }

    private void setupBackground() {
        try {
            Image bgImage = new Image(getClass().getResourceAsStream("/org/example/sesotho_game/images/h.png"));
            BackgroundImage backgroundImg = new BackgroundImage(
                    bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true)
            );
            this.setBackground(new Background(backgroundImg));
        } catch (Exception e) {
            this.setStyle("-fx-background-color: #2c3e50;");
        }
    }

    private void setupLeftMenu() {
        VBox leftMenu = new VBox(10);
        leftMenu.setAlignment(Pos.TOP_LEFT);
        leftMenu.setPadding(new Insets(50, 0, 0, 50));

        leftMenu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); " +
                "-fx-background-radius: 0 15 15 0; " +
                "-fx-padding: 20 20 20 50;");

        Button quickPlayBtn = createMenuButton("Quick Play", "quickplay");
        Button playCareerBtn = createMenuButton("Play Career", "career");
        Button settingsBtn = createMenuButton("Settings", "settings");
        Button infoBtn = createMenuButton("Info", "info");
        Button quitBtn = createMenuButton("Quit", "quit");

        leftMenu.getChildren().addAll(quickPlayBtn, playCareerBtn, settingsBtn, infoBtn, quitBtn);
        setLeft(leftMenu);
    }

    private void setupContentArea() {
        careerGrid = new GridPane();
        careerGrid.setAlignment(Pos.CENTER);
        careerGrid.setHgap(15);
        careerGrid.setVgap(15);
        careerGrid.setPadding(new Insets(20));

        infoText = new TextArea();
        infoText.setWrapText(true);
        infoText.setEditable(false);
        infoText.setStyle("-fx-font-size: 16px; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-color: rgba(139, 69, 19, 0.3); " +
                "-fx-control-inner-background:rgba(139, 69, 19, 0.3); " +
                "-fx-border-width: 0;");
        infoText.setText("Lumela! Rea u amohela!\n\n" +
                "This game will teach you about Sesotho culture.\n" +
                "Select a play career from the left to begin learning for best experience \nor\nquick play for over view .");
    }

    private void showContent(String type) {
        getChildren().remove(getCenter());

        switch (type) {
            case "quickplay":
                setupQuickPlayGrid();
                break;
            case "career":
                setupCareerGrid();
                break;
            case "settings":
                setupSettingsGrid();
                break;
            case "info":
                setCenter(infoText);
                break;
            case "quit":
                confirmQuit();
                break;
        }
    }


    private void setupQuickPlayGrid() {
        GridPane quickPlayGrid = new GridPane();
        quickPlayGrid.setAlignment(Pos.CENTER);
        quickPlayGrid.setHgap(15);
        quickPlayGrid.setVgap(15);
        quickPlayGrid.setPadding(new Insets(20));

        String[] categories = {"Lilotho", "Lipapali", "Maele", "Lijo", "Puo"};

        for (int i = 0; i < categories.length; i++) {
            final String category = categories[i];
            Button categoryBtn = createCategoryButton(category);
            categoryBtn.setOnAction(e -> showLevelSelection(category, "QuickPlay"));
            quickPlayGrid.add(categoryBtn, i % 3, i / 3);
        }

        StackPane gridPane = new StackPane();
        gridPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); " +
                "-fx-background-radius: 15;");
        gridPane.getChildren().add(quickPlayGrid);
        setCenter(gridPane);
    }

    private Button createCategoryButton(String category) {
        Button btn = new Button(category);
        btn.setMinSize(150, 100);
        btn.setStyle("-fx-font-size: 16px; " +
                "-fx-background-color: rgba(70, 130, 180, 0.7); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold;");
        return btn;
    }

    private void showLevelSelection(String category, String mode) {
        GridPane levelGrid = new GridPane();
        levelGrid.setAlignment(Pos.CENTER);
        levelGrid.setHgap(15);
        levelGrid.setVgap(15);

        String[] levels = {"Easy", "Medium", "Hard"};
        for (int i = 0; i < levels.length; i++) {
            Button levelBtn = new Button(levels[i]);
            levelBtn.setMinSize(120, 80);

            // Default style for all buttons
            String buttonStyle = "-fx-font-size: 14px; " +
                    "-fx-background-color: rgba(46, 125, 50, 0.7); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold;";

            // Disable medium and hard for quick play
            if (mode.equals("QuickPlay")) {  // Fixed: Added missing parenthesis
                if (i > 0) { // Medium and Hard buttons
                    buttonStyle = "-fx-font-size: 14px; " +
                            "-fx-background-color: rgba(150, 150, 150, 0.7); " +
                            "-fx-text-fill: #cccccc; " +
                            "-fx-font-weight: bold;";

                    // Add tooltip explaining why it's disabled
                    Tooltip tooltip = new Tooltip("Start a career to play Medium and Hard levels");
                    levelBtn.setTooltip(tooltip);

                    levelBtn.setOnAction(e -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Career Required");
                        alert.setHeaderText("Medium and Hard Levels");
                        alert.setContentText("Please start a career to play Medium and Hard levels.\n" +
                                "These levels track your progress and achievements.");
                        alert.showAndWait();
                    });
                } else { // Easy button
                    String level = levels[i].toLowerCase();
                    levelBtn.setOnAction(e -> startGameSession("QuickPlay", category, level));
                }
            } else { // Career mode - all buttons enabled
                String level = levels[i].toLowerCase();
                levelBtn.setOnAction(e -> startCareerGameSession(CareerData.load(mode), category, level));
            }

            levelBtn.setStyle(buttonStyle);
            levelGrid.add(levelBtn, i, 0);
        }

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            if (mode.equals("QuickPlay")) {
                setupQuickPlayGrid();
            } else {
                setupCareerGrid();
            }
        });
        levelGrid.add(backBtn, 1, 1);

        StackPane gridPane = new StackPane();
        gridPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); " +
                "-fx-background-radius: 15;");
        gridPane.getChildren().add(levelGrid);
        setCenter(gridPane);
    }
    private void setupCareerGrid() {
        careerGrid.getChildren().clear();

        // Add "+" button to create new career
        Button addButton = new Button("+");
        addButton.setMinSize(120, 80);
        addButton.setStyle("-fx-font-size: 24px; " +
                "-fx-background-color: rgba(46, 125, 50, 0.7); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold;");
        addButton.setOnAction(e -> createNewCareer());
        careerGrid.add(addButton, 0, 0);

        // Add existing career buttons with 3-dots menu
        int row = 0;
        int col = 1;
        for (String name : careerNames) {
            StackPane careerPane = new StackPane();
            careerPane.setAlignment(Pos.TOP_RIGHT);

            Button careerBtn = new Button(name);
            careerBtn.setMinSize(120, 80);
            careerBtn.setStyle("-fx-font-size: 14px; " +
                    "-fx-background-color: rgba(46, 125, 50, 0.7); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold;");
            careerBtn.setOnAction(e -> startCareer(name));

            Button menuBtn = new Button("⋮");
            menuBtn.setStyle("-fx-font-size: 14px; " +
                    "-fx-background-color: rgba(160, 82, 45, 0.9); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-min-width: 25; " +
                    "-fx-min-height: 25;");
            menuBtn.setOnAction(e -> showCareerMenu(menuBtn, name));

            StackPane.setAlignment(menuBtn, Pos.TOP_RIGHT);
            StackPane.setMargin(menuBtn, new Insets(5, 5, 0, 0));

            careerPane.getChildren().addAll(careerBtn, menuBtn);
            careerGrid.add(careerPane, col, row);

            col++;
            if (col > 2) {
                col = 0;
                row++;
            }
        }

        StackPane gridPane = new StackPane();
        gridPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); " +
                "-fx-background-radius: 15;");
        gridPane.getChildren().add(careerGrid);
        setCenter(gridPane);
    }

    private void setupSettingsGrid() {
        VBox settingsBox = new VBox(20);
        settingsBox.setAlignment(Pos.CENTER);
        settingsBox.setPadding(new Insets(20));

        // Settings Title
        Label settingsTitle = new Label("GAME SETTINGS");
        settingsTitle.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        // ===== TIME SETTINGS =====
        Label timeLabel = new Label("Question Time (seconds):");
        timeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        int currentTime = loadTimeSetting();
        Slider timeSlider = new Slider(10, 60, currentTime);
        timeSlider.setMajorTickUnit(10);
        timeSlider.setMinorTickCount(5);
        timeSlider.setShowTickLabels(true);
        timeSlider.setShowTickMarks(true);
        timeSlider.setSnapToTicks(true);
        timeSlider.setPrefWidth(300);

        Label timeValue = new Label(String.valueOf(currentTime));
        timeValue.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        timeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            timeValue.setText(String.valueOf(newVal.intValue()));
        });

        Button saveTimeBtn = new Button("Save Time");
        saveTimeBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveTimeBtn.setOnAction(e -> {
            saveTimeSetting((int)timeSlider.getValue());
            showAlert("Success", "Time per question set to " + (int)timeSlider.getValue() + " seconds");
        });

        HBox timeSetting = new HBox(10, timeLabel, timeSlider, timeValue, saveTimeBtn);
        timeSetting.setAlignment(Pos.CENTER);

        // ===== MUSIC SETTINGS =====
        Label musicLabel = new Label("Background Music:");
        musicLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        List<String> songs = AudioManager.getSongNames();
        ComboBox<String> songSelector = new ComboBox<>();
        songSelector.getItems().addAll(songs);
        songSelector.setValue(songs.get(AudioManager.getCurrentSongIndex()));

        // Volume Control
        Label volumeLabel = new Label("Volume:");
        volumeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setPrefWidth(150);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            AudioManager.setVolume(newVal.doubleValue() / 100);
        });

        Button playPauseBtn = new Button("Pause");
        playPauseBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        playPauseBtn.setOnAction(e -> {
            AudioManager.togglePlayPause();
            playPauseBtn.setText(
                    AudioManager.mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING
                            ? "Pause" : "Play");
        });

        songSelector.setOnAction(e -> {
            int selectedIndex = songSelector.getSelectionModel().getSelectedIndex();
            AudioManager.playSong(selectedIndex);
        });

        GridPane musicGrid = new GridPane();
        musicGrid.setHgap(10);
        musicGrid.setVgap(10);
        musicGrid.setAlignment(Pos.CENTER);

        musicGrid.add(new Label("Current Song:"), 0, 0);
        musicGrid.add(songSelector, 1, 0);
        musicGrid.add(playPauseBtn, 2, 0);
        musicGrid.add(volumeLabel, 0, 1);
        musicGrid.add(volumeSlider, 1, 1, 2, 1);

        // ===== HELP SECTION =====
        Button helpBtn = new Button("Help");
        helpBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        helpBtn.setOnAction(e -> showHelp());

        // ===== BACK BUTTON =====
        Button backBtn = new Button("Back to Menu");
        backBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        backBtn.setOnAction(e -> showContent("quickplay"));

        // Add all components to settings box
        settingsBox.getChildren().addAll(
                settingsTitle,
                new Separator(),
                timeSetting,
                new Separator(),
                musicLabel,
                musicGrid,
                new Separator(),
                helpBtn,
                backBtn
        );

        StackPane settingsPane = new StackPane();
        settingsPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-background-radius: 15;");
        settingsPane.getChildren().add(settingsBox);
        setCenter(settingsPane);
    }
    private int loadTimeSetting() {
        try {
            Path timeFile = Paths.get("time.dat");
            if (Files.exists(timeFile)) {
                String content = Files.readString(timeFile).trim();
                return Integer.parseInt(content);
            }
        } catch (Exception e) {
            System.err.println("Error loading time setting: " + e.getMessage());
        }
        return 30; // default value
    }

    private void saveTimeSetting(int seconds) {
        try {
            Files.writeString(Paths.get("time.dat"), String.valueOf(seconds));
        } catch (IOException e) {
            System.err.println("Could not save time setting: " + e.getMessage());
        }
    }

    private void showHelp() {
        TextArea helpText = new TextArea();
        helpText.setEditable(false);
        helpText.setWrapText(true);
        helpText.setText("SESOTHO LEARNING GAME HELP\n\n" +
                "1. Select a game mode (Quick Play or Career)\n" +
                "2. Choose a category and difficulty level\n" +
                "3. Answer questions before time runs out\n" +
                "4. Earn points for correct answers\n\n" +
                "SETTINGS:\n" +
                "- Adjust question time limit (10-60 seconds)\n" +
                "- Toggle sound on/off");

        helpText.setStyle("-fx-font-size: 14px; -fx-text-fill: white; " +
                "-fx-control-inner-background: #333;");

        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Help");
        helpAlert.setHeaderText("Game Instructions");
        helpAlert.getDialogPane().setContent(helpText);
        helpAlert.getDialogPane().setPrefSize(500, 400);
        helpAlert.showAndWait();
    }


    private void startCareer(String name) {
        CareerData career = CareerData.load(name);
        if (career == null) {
            showAlert("Career Error", "Could not load career data for " + name);
            return;
        }
        showCareerCategories(career);
    }

    private void showCareerCategories(CareerData career) {
        GridPane categoryGrid = new GridPane();
        categoryGrid.setAlignment(Pos.CENTER);
        categoryGrid.setHgap(15);
        categoryGrid.setVgap(15);
        categoryGrid.setPadding(new Insets(20));

        String[] categories = {"Lilotho", "Lipapali", "Maele", "Lijo", "Puo"}; // Updated categories

        for (int i = 0; i < categories.length; i++) {
            final String category = categories[i]; // Create final copy
            Button categoryBtn = createCategoryButton(category);
            categoryBtn.setOnAction(e -> showCareerLevels(career, category));
            categoryGrid.add(categoryBtn, i % 3, i / 3);
        }

        Button backBtn = new Button("Back to Careers");
        backBtn.setOnAction(e -> setupCareerGrid());
        categoryGrid.add(backBtn, 1, 2);

        StackPane gridPane = new StackPane();
        gridPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); " +
                "-fx-background-radius: 15;");
        gridPane.getChildren().add(categoryGrid);
        setCenter(gridPane);
    }

    private void showCareerLevels(CareerData career, String category) {
        VBox levelsBox = new VBox(10);
        levelsBox.setAlignment(Pos.CENTER);
        levelsBox.setPadding(new Insets(20));

        String[] levels = {"Easy", "Medium", "Hard"};
        String[] levelKeys = {"easy", "medium", "hard"};

        for (int i = 0; i < levels.length; i++) {
            String level = levels[i];
            String levelKey = levelKeys[i];
            CareerData.CategoryStats stats = career.getCategoryStats().get(category).get(levelKey);

            Accordion levelAccordion = new Accordion();
            TitledPane statsPane = new TitledPane();
            statsPane.setText(level + " - Played: " + stats.gamesPlayed);
            statsPane.setExpanded(false);

            // Stats content
            VBox statsContent = new VBox(10);
            statsContent.setStyle("-fx-padding: 10px;");
            statsContent.getChildren().addAll(
                    new Label("Games Played: " + stats.gamesPlayed),
                    new Label("Best Single Game: " + stats.highScore),
                    new Label(String.format("Average: %.1f", stats.getAverageScore())),
                    new Label("Total Points: " + stats.totalPoints)
            );

            // Play button - only enabled if level is unlocked
            Button playBtn = new Button("Play " + level);
            playBtn.setDisable(!stats.unlocked && i > 0); // Disable if not unlocked (except Easy)

            if (i > 0 && !stats.unlocked) {
                playBtn.setTooltip(new Tooltip("You must pass the " + levels[i-1] + " level first"));
            }

            playBtn.setStyle("-fx-background-color: " +
                    (playBtn.isDisabled() ? "#cccccc" : "#4CAF50") +
                    "; -fx-text-fill: white;");

            playBtn.setOnAction(e -> startCareerGameSession(career, category, levelKey));
            statsContent.getChildren().add(playBtn);

            statsPane.setContent(statsContent);
            statsPane.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            levelAccordion.getPanes().add(statsPane);
            levelsBox.getChildren().add(levelAccordion);
        }

        // Back button
        Button backBtn = new Button("Back to Categories");
        backBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        backBtn.setOnAction(e -> showCareerCategories(career));
        levelsBox.getChildren().add(backBtn);

        setCenter(levelsBox);
    }
    private void startCareerGameSession(CareerData career, String category, String level) {
        List<Question> questions = QuestionLoader.loadQuestions(category, level);
        if (questions.isEmpty()) {
            showAlert("Error", "No questions available for " + category + " - " + level);
            return;
        }

        // Create new session with score=0
        GameSession session = new GameSession(career.getPlayerName(), category, level, questions);

        // Don't set any initial score here - let it start fresh
        mainApp.showGameScreen(session);
    }
    private void startGameSession(String playerName, String category, String level) {
        List<Question> questions = QuestionLoader.loadQuestions(category, level);
        if (questions.isEmpty()) {
            showAlert("Error", "No questions available for " + category + " - " + level);
            return;
        }

        GameSession session = new GameSession(playerName, category, level, questions);
        mainApp.showGameScreen(session);
    }




    private void handleSetting(String setting) {
        switch (setting.toLowerCase()) {
            case "sound":
                showAlert("Settings", "Sound options would go here");
                break;
            case "language":
                showAlert("Settings", "Language selection would go here");
                break;
            case "difficulty":
                showAlert("Settings", "Difficulty adjustment would go here");
                break;
            case "profile":
                showAlert("Settings", "Profile management would go here");
                break;
            case "help":
                showAlert("Help", "Game instructions and help would go here");
                break;
            case "about":
                showAlert("About", "Sesotho Learning Game\nVersion 1.0");
                break;
            case "reset":
                handleReset();
                break;
            case "save":
                showAlert("Save", "Game progress saved");
                break;
            case "exit":
                confirmQuit();
                break;
        }
    }

    private void handleReset() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reset Game");
        alert.setHeaderText("Reset all game data?");
        alert.setContentText("This will delete all careers and progress. Are you sure?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                Path dirPath = Paths.get("src/main/resources/org/example/sesotho_game/files");
                if (Files.exists(dirPath)) {
                    Files.walk(dirPath)
                            .filter(Files::isRegularFile)
                            .forEach(file -> {
                                try {
                                    Files.delete(file);
                                } catch (IOException e) {
                                    System.err.println("Error deleting file: " + file);
                                }
                            });
                    careerNames.clear();
                    setupCareerGrid();
                }
            } catch (IOException e) {
                showAlert("Error", "Could not reset game data: " + e.getMessage());
            }
        }
    }

    private void showCareerMenu(Button menuBtn, String careerName) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> deleteCareer(careerName));

        MenuItem renameItem = new MenuItem("Rename");
        renameItem.setOnAction(e -> renameCareer(careerName));

        MenuItem statsItem = new MenuItem("View Stats");
        statsItem.setOnAction(e -> showCareerStats(careerName));

        contextMenu.getItems().addAll(deleteItem, renameItem, statsItem);
        contextMenu.show(menuBtn, Side.BOTTOM, 0, 0);
    }

    private void showCareerStats(String careerName) {
        CareerData career = CareerData.load(careerName);
        if (career != null) {
            StringBuilder statsText = new StringBuilder();
            statsText.append("Career: ").append(careerName).append("\n");
            statsText.append("Total Score: ").append(career.getTotalScore()).append("\n\n");

            statsText.append("Category Breakdown:\n");
            for (Map.Entry<String, Map<String, CareerData.CategoryStats>> categoryEntry :
                    career.getCategoryStats().entrySet()) {
                String category = categoryEntry.getKey();
                statsText.append("\n").append(category).append(":\n");

                for (Map.Entry<String, CareerData.CategoryStats> levelEntry :
                        categoryEntry.getValue().entrySet()) {
                    String level = levelEntry.getKey();
                    CareerData.CategoryStats stats = levelEntry.getValue();

                    if (stats.gamesPlayed > 0) {
                        statsText.append("  ").append(level).append(":\n");
                        statsText.append("    Games Played: ").append(stats.gamesPlayed).append("\n");
                        statsText.append("    Best Score: ").append(stats.highScore).append("\n");
                        statsText.append(String.format("    Average Score: %.1f\n", stats.getAverageScore()));
                        statsText.append("    Total Points: ").append(stats.totalPoints).append("\n");
                    }
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Career Statistics");
            alert.setHeaderText("Detailed Stats for " + careerName);
            alert.setContentText(statsText.toString());
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }
    }

    private void deleteCareer(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Career");
        alert.setHeaderText("Delete " + name + "?");
        alert.setContentText("Are you sure you want to delete this career?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                Path filePath = Paths.get("src/main/resources/org/example/sesotho_game/files", name + ".dat");
                Files.deleteIfExists(filePath);
                careerNames.remove(name);
                setupCareerGrid();
            } catch (IOException e) {
                showAlert("Error", "Could not delete career: " + e.getMessage());
            }
        }
    }

    private void renameCareer(String oldName) {
        TextInputDialog dialog = new TextInputDialog(oldName);
        dialog.setTitle("Rename Career");
        dialog.setHeaderText("Rename " + oldName);
        dialog.setContentText("Enter new name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> {
            if (!newName.trim().isEmpty() && !newName.equals(oldName)) {
                try {
                    Path oldPath = Paths.get("src/main/resources/org/example/sesotho_game/files", oldName + ".dat");
                    Path newPath = Paths.get("src/main/resources/org/example/sesotho_game/files", newName + ".dat");
                    Files.move(oldPath, newPath);

                    int index = careerNames.indexOf(oldName);
                    if (index != -1) {
                        careerNames.set(index, newName);
                        setupCareerGrid();
                    }
                } catch (IOException e) {
                    showAlert("Error", "Could not rename career: " + e.getMessage());
                }
            }
        });
    }

    private void createNewCareer() {
        TextInputDialog dialog = new TextInputDialog("Player" + (careerNames.size() + 1));
        dialog.setTitle("New Career");
        dialog.setHeaderText("Create New Career");
        dialog.setContentText("Enter player name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                CareerData career = new CareerData(name);
                if (career.save()) {
                    careerNames.add(0, name);
                    setupCareerGrid();
                } else {
                    showAlert("Error", "Could not create career file");
                }
            }
        });
    }

    private void confirmQuit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Game");
        alert.setHeaderText("O batla ho tsoa?");
        alert.setContentText("Are you sure you want to quit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            mainApp.getPrimaryStage().close();
        } else {
            activeButton = "";
            resetButtonStyles();
        }
    }

    private void resetButtonStyles() {
        VBox leftMenu = (VBox) getLeft();
        for (javafx.scene.Node node : leftMenu.getChildren()) {
            if (node instanceof Button) {
                node.setStyle(getButtonStyle(false));
            }
        }
    }

    private String getButtonStyle(boolean active) {
        String base = "-fx-background-color: rgba(139, 69, 19, 0.7); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 16px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-border-color: #f5d742; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 1);";

        if (active) {
            return base + " -fx-background-color: rgba(160, 82, 45, 0.9); " +
                    "-fx-scale-x: 1.02; " +
                    "-fx-scale-y: 1.02;";
        }
        return base;
    }

    private Button createMenuButton(String text, String type) {
        Button button = new Button(text);
        button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setMaxSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setStyle(getButtonStyle(false));

        button.setOnMouseEntered(e -> button.setStyle(getButtonStyle(true)));
        button.setOnMouseExited(e -> {
            if (!activeButton.equals(type)) {
                button.setStyle(getButtonStyle(false));
            }
        });

        button.setOnAction(e -> {
            activeButton = type;
            resetButtonStyles();
            button.setStyle(getButtonStyle(true));
            showContent(type);
        });

        return button;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}