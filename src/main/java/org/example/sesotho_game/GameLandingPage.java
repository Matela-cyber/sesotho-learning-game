package org.example.sesotho_game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameLandingPage extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Configure window to start maximized and stay that way
        primaryStage.setMaximized(true);
        primaryStage.setIconified(false);
        primaryStage.setResizable(false);

        // First show the intro video
        VideoPlayer videoPlayer = new VideoPlayer(this);
        StackPane root = new StackPane(videoPlayer);
        Scene scene = new Scene(root);

        primaryStage.setTitle("Sesotho Learning Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showMainMenu() {
        MainMenu mainMenu = new MainMenu(this);
        Scene scene = new Scene(mainMenu, 1370, 700);
        primaryStage.setScene(scene);
        // Ensure window stays maximized after transition
        primaryStage.setMaximized(true);
        primaryStage.setIconified(false);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}