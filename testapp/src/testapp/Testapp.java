package testapp;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Testapp extends Application {

    static MediaPlayer currentPlayer;

    /*
    static File file = new File("test.mp4");
    static String source = file.toURI().toString();
    static Media video = new Media(source);

    static File file2 = new File("test2.mp4");
    static String source2 = file2.toURI().toString();
    static Media video2 = new Media(source2);
     */
    // 1 Media to many MediaPlayer
    // 1 MediaPlayer to many MediaView
    // A MediaPlayer without a MediaView or vice versa shows no video
    @Override
    public void start(Stage primaryStage) {

        GridPane root = new GridPane();
        File defaultfile = new File("test.mp4");
        String defaultsource = defaultfile.toURI().toString();
        Media defaultvideo = new Media(defaultsource);
        MediaPlayer defaultplayer = new MediaPlayer(defaultvideo);
        MediaView mediaView = new MediaView(defaultplayer);
        mediaView.setVisible(false);
        currentPlayer = defaultplayer;

        Button one = new Button("One");
        Button two = new Button("Two");

        one.setOnAction(
                (ActionEvent e) -> {
                    if (mediaView.isVisible() == false) {
                        mediaView.setVisible(true);
                    }
                    File file = new File("test.mp4");
                    String source = file.toURI().toString();
                    Media video = new Media(source);
                    MediaPlayer player = new MediaPlayer(video);
                    currentPlayer.stop();
                    System.out.println("Playing test.mp4");
                    currentPlayer = player;
                    mediaView.setMediaPlayer(currentPlayer);
                    currentPlayer.play();
                }
        );

        two.setOnAction(
                (ActionEvent e) -> {
                    if (mediaView.isVisible() == false) {
                        mediaView.setVisible(true);
                    }
                    File file = new File("test2.mp4");
                    String source = file.toURI().toString();
                    Media video = new Media(source);
                    MediaPlayer player = new MediaPlayer(video);
                    currentPlayer.stop();
                    System.out.println("Playing test2.mp4");
                    mediaView.setMediaPlayer(player);
                    currentPlayer = player;
                    currentPlayer.play();
                }
        );

        root.add(mediaView, 0, 0);
        root.add(one, 0, 1);
        root.add(two, 0, 2);

        Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
