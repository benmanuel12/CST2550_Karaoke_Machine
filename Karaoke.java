import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;

public class Karaoke extends Application {

    //static ArrayList<Song> library = new ArrayList<>();
    static HashMap<String, Song> library = new HashMap<>();
    /*
    This is a good data structure as the task requires that the structure allow:
    - Searching for a song
    - Adding songs to the structure

    Since a HashMap works off of key - value pairs, you can simply give the key (here the song title) and the Song object is returned with O(1) complexity
    This same concept is applied to adding items to the structure
    */
    static LinkedList<Song> playlist = new LinkedList<>();
    /*
    This is a good data structure as the task requires that the structure allow:
    - Adding songs to the structure
    - Playing the first song in the structure (essentially accessing the first element and then deleting it from the structure)
    - Viewing the playlist
    - Deleting presumably any item from it (if it was just the first and viewing wasn't wanted, a Queue abstraction over the LinkedList would do)
    */
    static Song currentSong;

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
            currentSong = playlist.remove();
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

    /*
    GUI Required Features
    - Title widget
    - Video widget
    - Video Control bar
        - Play button
        - Pause button
        - Skip button
    - Playlist Control bar
        - Add song button
        - View playlist button
        - Delete song button
        - Playlist contents output widget
    - Library Control bar
        - Add song button
        - Search library button
        - Search results widget
    */

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World!");
        
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
        
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
