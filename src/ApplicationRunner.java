import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.util.HashMap;

public class ApplicationRunner extends Application {

    static HashMap<String, Song> library = new HashMap<>();
    /*
     * This is a good data structure as the task requires that the structure allow:
     * - Searching for a song - Adding songs to the structure
     * 
     * Since a HashMap works off of key - value pairs, you can simply give the key
     * (here the song title) and the Song object is returned with O(1) complexity
     * This same concept is applied to adding items to the structure
     */
    static LinkedList<Song> playlist = new LinkedList<>();
    /*
     * This is a good data structure as the task requires that the structure allow:
     * - Adding songs to the structure - Playing the first song in the structure
     * (essentially accessing the first element and then deleting it from the
     * structure) - Viewing the playlist - Deleting presumably any item from it (if
     * it was just the first and viewing wasn't wanted, a Queue abstraction over the
     * LinkedList would do)
     */
    static Song currentSong;

    @Override
    public void start(Stage primaryStage) {

        /*
         * Media media = new Media("test.mp4"); MediaPlayer player = new
         * MediaPlayer(media); player.setAutoPlay(true);
         */

        primaryStage.setTitle("Karaoke Machine");
        GridPane root = new GridPane();
        GridPane leftPane = new GridPane();
        GridPane midPane = new GridPane();
        GridPane rightPane = new GridPane();
        GridPane addSongPane = new GridPane();
        GridPane librarySearchPane = new GridPane();
        GridPane songControlPane = new GridPane();
        GridPane playlistControlPane = new GridPane();

        root.add(leftPane, 0, 0);
        root.add(midPane, 1, 0);
        root.add(rightPane, 2, 0);

        leftPane.add(addSongPane, 0, 0);
        leftPane.add(librarySearchPane, 0, 1);

        midPane.add(songControlPane, 0, 2);

        rightPane.add(playlistControlPane, 0, 0);

        // Left Pane
        Label addSongLabel = new Label("Add New Song");
        TextField titleInput = new TextField("Song Title");
        TextField artistInput = new TextField("Artist");
        TextField runningTimeInput = new TextField("Running Time");
        TextField videoInput = new TextField("Video Link");
        Button addSongButton = new Button("Add Song");

        addSongPane.add(addSongLabel, 1, 0);
        addSongPane.add(titleInput, 1, 1);
        addSongPane.add(artistInput, 1, 2);
        addSongPane.add(runningTimeInput, 1, 3);
        addSongPane.add(videoInput, 1, 4);
        addSongPane.add(addSongButton, 0, 5);
        addSongPane.setColumnSpan(addSongLabel, 2);

        Button librarySearchButton = new Button("Search");
        TextField librarySearchInput = new TextField("Song name here");
        TextArea librarySearchOutput = new TextArea("Search results");

        librarySearchPane.add(librarySearchInput, 0, 0);
        librarySearchPane.add(librarySearchButton, 1, 0);
        librarySearchPane.add(librarySearchOutput, 0, 1);
        librarySearchPane.setColumnSpan(librarySearchOutput, 2);

        // Middle Pane
        Label titleLabel = new Label("Song Title");
        TextField videoView = new TextField("Video should go here");

        midPane.add(titleLabel, 0, 0);
        midPane.add(videoView, 0, 1);

        primaryStage.setScene(new Scene(root, 750, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        importMultipleSongs(args[0]);
        launch(args);
    }

    static void importMultipleSongs(String path) {
        String filePath = System.getProperty("user.dir") + File.separator + path;
        try {
            InputStream inputStream = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] array = line.split("\t");
                Song newSong = new Song(array[0], array[1], Integer.parseInt(array[2]), array[3]);
                library.put(newSong.getTitle(), newSong);
            }
            // System.out.println(library);

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    static void importOneSong(String name, String artist, int time, String link) {
        Song newSong = new Song(name, artist, time, link);
        library.put(newSong.getTitle(), newSong);
    }

    static void searchLibrary(String criteria) {

    }

    static void addToPlaylist(Song song) {
        playlist.add(song);
    }

    static void playSong() {
        if (playlist.size() > 0) {
            // currentSong = playlist.remove();
            // play the song
        } else {
            System.out.println("Playlist is empty");
            // show this on screen
        }
    }

    static void viewPlaylist() {
        for (Song song : playlist) {
            System.out.println(song);
        }
        // refit to print to ui
    }

    static void deleteFromPlaylist(int index) {
        playlist.remove();
    }

}
