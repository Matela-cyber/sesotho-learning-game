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

        Stage stage = mainApp.getPrimaryStage();
        stage.setMaximized(true);
        stage.setIconified(false);
        stage.setResizable(false);
    }

    private void loadCareerNames() {
        try {
            Path dirPath = Paths.get("src/main/resources/org/example/sesotho_game/files");
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.txt")) {
                for (Path file : stream) {
                    String name = file.getFileName().toString().replace(".txt", "");
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

        for (int i = 0; i < 9; i++) {
            Button btn = new Button("Game " + (i+1));
            btn.setMinSize(120, 80);
            btn.setStyle("-fx-font-size: 14px; " +
                    "-fx-background-color: rgba(70, 130, 180, 0.7); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold;");
            quickPlayGrid.add(btn, i%3, i/3);
        }

        StackPane gridPane = new StackPane();
        gridPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); " +
                "-fx-background-radius: 15;");
        gridPane.getChildren().add(quickPlayGrid);
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
            settingsGrid.add(btn, i%3, i/3);
        }

        StackPane gridPane = new StackPane();
        gridPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); " +
                "-fx-background-radius: 15;");
        gridPane.getChildren().add(settingsGrid);
        setCenter(gridPane);
    }

    private void showCareerMenu(Button menuBtn, String careerName) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> deleteCareer(careerName));

        MenuItem renameItem = new MenuItem("Rename");
        renameItem.setOnAction(e -> renameCareer(careerName));

        contextMenu.getItems().addAll(deleteItem, renameItem);
        contextMenu.show(menuBtn, Side.BOTTOM, 0, 0);
    }

    private void deleteCareer(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Career");
        alert.setHeaderText("Delete " + name + "?");
        alert.setContentText("Are you sure you want to delete this career?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                Path filePath = Paths.get("src/main/resources/org/example/sesotho_game/files", name + ".txt");
                Files.deleteIfExists(filePath);
                careerNames.remove(name);
                setupCareerGrid();
            } catch (IOException e) {
                System.err.println("Error deleting career: " + e.getMessage());
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
                    Path oldPath = Paths.get("src/main/resources/org/example/sesotho_game/files", oldName + ".txt");
                    Path newPath = Paths.get("src/main/resources/org/example/sesotho_game/files", newName + ".txt");
                    Files.move(oldPath, newPath);

                    int index = careerNames.indexOf(oldName);
                    if (index != -1) {
                        careerNames.set(index, newName);
                        setupCareerGrid();
                    }
                } catch (IOException e) {
                    System.err.println("Error renaming career: " + e.getMessage());
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
                createCareerFile(name);
                careerNames.add(0, name); // Add to beginning of list
                setupCareerGrid(); // Refresh the grid
            }
        });
    }

    private void createCareerFile(String name) {
        try {
            Path filePath = Paths.get("src/main/resources/org/example/sesotho_game/files", name + ".txt");
            Files.write(filePath, Collections.emptyList(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Error creating career file: " + e.getMessage());
        }
    }

    private void startCareer(String name) {
        // Implementation for starting a career
        System.out.println("Starting career: " + name);
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
}