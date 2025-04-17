package org.example.sesotho_game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.StackPane;

public class VideoPlayer extends StackPane {
    public VideoPlayer(GameLandingPage mainApp) {
        try {
            String videoPath = getClass().getResource("/org/example/sesotho_game/vedios/h.mp4").toString();
            Media media = new Media(videoPath);
            MediaPlayer player = new MediaPlayer(media);
            MediaView viewer = new MediaView(player);

            // Make video fill the maximized window
            viewer.fitWidthProperty().bind(mainApp.getPrimaryStage().widthProperty());
            viewer.fitHeightProperty().bind(mainApp.getPrimaryStage().heightProperty());

            player.setOnEndOfMedia(() -> mainApp.showMainMenu());
            player.play();

            getChildren().add(viewer);
        } catch (Exception e) {
            System.err.println("Error loading video: " + e.getMessage());
            mainApp.showMainMenu();
        }
    }
}