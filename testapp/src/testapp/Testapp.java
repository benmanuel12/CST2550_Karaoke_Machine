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

    static File file = new File("test.mp4");
    static String source = file.toURI().toString();
    static Media video = new Media(source);

    static File file2 = new File("test2.mp4");
    static String source2 = file2.toURI().toString();
    static Media video2 = new Media(source2);
    
    // 1 Media to many MediaPlayer
    // 1 MediaPlayer to many MediaView
    // A MediaPlayer without a MediaView or vice versa shows no video

    @Override
    public void start(Stage primaryStage) {
        
        // at global scale
        // for each video you need
        //   create file
        //   create source
        //   create Media
        //   create MediaPlayer(Media)
        
        // create MediaView(DefaultMediaPlayer)
        // create currentPlayer variable
        
        // at function scale
        // if currentPlayer == null
        //   set mediaPlayer of media view to appropriate player
        //   play video
        // else
        //   stop currentPlayer
        //   set mediaPlayer of media view to appropriate player
        //   play video
        //   

        GridPane root = new GridPane();
        MediaView mediaView = new MediaView(player);

        Button one = new Button("One");
        Button two = new Button("Two");

        one.setOnAction((ActionEvent e) -> {

            MediaPlayer player = new MediaPlayer(video);
            System.out.println("Playing test.mp4");
            mediaView.setMediaPlayer(player);
            player.play();
        });

        two.setOnAction((ActionEvent e) -> {

            MediaPlayer player2 = new MediaPlayer(video2);
            System.out.println("Playing test2.mp4");
            mediaView.setMediaPlayer(player2);
            player2.play();
        });

        root.add(mediaView, 0, 0);
        root.add(one, 0, 1);
        root.add(two, 0, 2);

        Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
