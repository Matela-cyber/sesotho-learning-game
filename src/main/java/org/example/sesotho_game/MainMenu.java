package org.example.sesotho_game;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.geometry.*;
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
                "Select a game mode from the left to begin learning.");
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

        String[] categories = {"Lilotho", "Lipapali", "Maele", "Culture", "History"};

        // Add category buttons
        for (int i = 0; i < categories.length; i++) {
            Button categoryBtn = createCategoryButton(categories[i]);
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
        btn.setOnAction(e -> showLevelSelection(category));
        return btn;
    }

    private void showLevelSelection(String category) {
        GridPane levelGrid = new GridPane();
        levelGrid.setAlignment(Pos.CENTER);
        levelGrid.setHgap(15);
        levelGrid.setVgap(15);

        String[] levels = {"Easy", "Medium", "Hard"};
        for (int i = 0; i < levels.length; i++) {
            Button levelBtn = new Button(levels[i]);
            levelBtn.setMinSize(120, 80);
            levelBtn.setStyle("-fx-font-size: 14px; " +
                    "-fx-background-color: rgba(46, 125, 50, 0.7); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold;");
            String level = levels[i].toLowerCase();
            levelBtn.setOnAction(e -> startGameSession(category, level));
            levelGrid.add(levelBtn, i, 0);
        }

        StackPane gridPane = new StackPane();
        gridPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); " +
                "-fx-background-radius: 15;");
        gridPane.getChildren().add(levelGrid);
        setCenter(gridPane);
    }

    private void startGameSession(String category, String level) {
        List<Question> questions = QuestionLoader.loadQuestions(category, level);
        if (questions.isEmpty()) {
            showAlert("Error", "No questions available for " + category + " - " + level);
            return;
        }

        GameSession session = new GameSession("QuickPlay", category, level, questions);
        mainApp.showGameScreen(session);
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

            // Main career button
            Button careerBtn = new Button(name);
            careerBtn.setMinSize(120, 80);
            careerBtn.setStyle("-fx-font-size: 14px; " +
                    "-fx-background-color: rgba(46, 125, 50, 0.7); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold;");
            careerBtn.setOnAction(e -> startCareer(name));

            // 3-dots menu button (positioned top-right)
            Button menuBtn = new Button("⋮");
            menuBtn.setStyle("-fx-font-size: 14px; " +
                    "-fx-background-color: rgba(160, 82, 45, 0.9); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-min-width: 25; " +
                    "-fx-min-height: 25;");
            menuBtn.setOnAction(e -> showCareerMenu(menuBtn, name));

            // Position menu button in top-right corner
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

    private void startCareer(String name) {
        CareerData career = CareerData.load(name);
        if (career == null) {
            showAlert("Career Error", "Could not load career data for " + name);
            return;
        }

        List<Question> questions = QuestionLoader.loadQuestions(
                career.getCurrentCategory(),
                career.getCurrentLevel()
        );

        if (questions.isEmpty()) {
            showAlert("Error", "No questions available for " +
                    career.getCurrentCategory() + " - " + career.getCurrentLevel());
            return;
        }

        GameSession session = new GameSession(name, career.getCurrentCategory(),
                career.getCurrentLevel(), questions);
        session.setScore(career.getScore());
        mainApp.showGameScreen(session);
    }

    private void setupSettingsGrid() {
        GridPane settingsGrid = new GridPane();
        settingsGrid.setAlignment(Pos.CENTER);
        settingsGrid.setHgap(15);
        settingsGrid.setVgap(15);

        String[] settings = {"Sound", "Language", "Difficulty",
                "Profile", "Help", "About",
                "Reset", "Save", "Exit"};
        for (int i = 0; i < settings.length; i++) {
            Button btn = new Button(settings[i]);
            btn.setMinSize(120, 80);
            btn.setStyle("-fx-font-size: 14px; " +
                    "-fx-background-color: rgba(70, 130, 180, 0.7); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold;");
            final String setting = settings[i];
            btn.setOnAction(e -> handleSetting(setting));
            settingsGrid.add(btn, i%3, i/3);
        }

        StackPane gridPane = new StackPane();
        gridPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); " +
                "-fx-background-radius: 15;");
        gridPane.getChildren().add(settingsGrid);
        setCenter(gridPane);
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Career Statistics");
            alert.setHeaderText("Stats for " + careerName);
            alert.setContentText(
                    "Score: " + career.getScore() + "\n" +
                            "Current Level: " + career.getCurrentLevel() + "\n" +
                            "Current Category: " + career.getCurrentCategory()
            );
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