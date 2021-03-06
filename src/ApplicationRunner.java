
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import javafx.scene.control.Button;
import java.util.HashMap;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ApplicationRunner extends Application {
    
    // Core data Structures
    static HashMap<String, Song> library = new HashMap<>();
    static LinkedList<Song> playlist = new LinkedList<>();
    
    // This code is only needed because I can't make a MediaView for showing the
    // video without a MediaPlayer and that player needs a Media to be constructed
    // and that needs a source from a File
    static File defaultfile = new File("test.mp4");
    static String defaultsource = defaultfile.toURI().toString();
    static Media defaultvideo = new Media(defaultsource);
    static MediaPlayer defaultplayer = new MediaPlayer(defaultvideo);
    
    // Core global variables
    static Song currentSong;
    static MediaPlayer currentPlayer;
    
    // Core GUI GridPanes
    static GridPane root = new GridPane();
    static GridPane leftPane = new GridPane();
    static GridPane midPane = new GridPane();
    static GridPane rightPane = new GridPane();
    static GridPane addSongPane = new GridPane();
    static GridPane librarySearchPane = new GridPane();
    static GridPane songControlPane = new GridPane();
    static GridPane playlistControlPane = new GridPane();
    
    // All other GUI nodes
    static Label status = new Label();

    static Label addSongLabel = new Label("Add New Song");
    static TextField titleInput = new TextField("Song Title");
    static TextField artistInput = new TextField("Artist");
    static TextField runningTimeInput = new TextField("Running Time");
    static TextField videoInput = new TextField("Video Link");
    static Button addSongButton = new Button("Add Song");

    static Button librarySearchButton = new Button("Search");
    static TextField librarySearchInput = new TextField("Song name here");
    static Label librarySearchOutput = new Label("Search results");

    static Label titleLabel = new Label("Song Title");
    static MediaView videoView = new MediaView(defaultplayer);

    static Button play = new Button("Play");
    static Button pause = new Button("Pause");
    static Button skip = new Button("Skip");

    static Button playlistAddButton = new Button("Add Song");
    static TextField playlistAddInput = new TextField("Song Name");
    static Button playlistDeleteButton = new Button("Delete Song");
    static TextField playlistDeleteInput = new TextField("Song Number");
    static Button refresh = new Button("Refresh Playlist View");

    static Label playlistContents = new Label("Playlist contents");

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Karaoke Machine");

        GridPane.setMargin(leftPane, new Insets(5));
        GridPane.setMargin(midPane, new Insets(5));
        GridPane.setMargin(rightPane, new Insets(5));
        GridPane.setMargin(addSongPane, new Insets(5));
        GridPane.setMargin(librarySearchPane, new Insets(5));
        GridPane.setMargin(songControlPane, new Insets(5));
        GridPane.setMargin(playlistControlPane, new Insets(5));

        leftPane.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        midPane.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        rightPane.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        addSongPane.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        playlistControlPane.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        status.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));

        root.add(leftPane, 0, 0);
        root.add(midPane, 1, 0);
        root.add(rightPane, 2, 0);
        root.add(status, 0, 1);
        GridPane.setColumnSpan(status, 3);
        status.setAlignment(Pos.CENTER);

        leftPane.add(addSongPane, 0, 0);
        leftPane.add(librarySearchPane, 0, 1);

        midPane.add(songControlPane, 0, 2);

        rightPane.add(playlistControlPane, 0, 0);

        // Left Pane
        addSongPane.add(addSongLabel, 1, 0);
        addSongPane.add(titleInput, 1, 1);
        addSongPane.add(artistInput, 1, 2);
        addSongPane.add(runningTimeInput, 1, 3);
        addSongPane.add(videoInput, 1, 4);
        addSongPane.add(addSongButton, 0, 5);
        addSongPane.setColumnSpan(addSongLabel, 2);
        addSongPane.setColumnSpan(addSongButton, 2);
        GridPane.setHalignment(addSongButton, HPos.CENTER);
        addSongButton.setOnAction((ActionEvent e) -> {
            addSong(titleInput.getText(), artistInput.getText(), runningTimeInput.getText(), videoInput.getText());
        });

        librarySearchOutput.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        librarySearchOutput.setPrefHeight(200);
        librarySearchOutput.setPrefWidth(300);

        librarySearchPane.add(librarySearchInput, 0, 0);
        librarySearchPane.add(librarySearchButton, 1, 0);
        librarySearchPane.add(librarySearchOutput, 0, 1);
        librarySearchPane.setColumnSpan(librarySearchOutput, 2);

        librarySearchButton.setOnAction((ActionEvent e) -> {
            searchLibrary(librarySearchInput.getText());
        });

        // Middle Pane
        titleLabel.setFont(Font.font("Deja Vu Sans", FontWeight.BOLD, 14));
        videoView.setVisible(false);

        GridPane.setHalignment(titleLabel, HPos.CENTER);

        midPane.add(titleLabel, 0, 0);
        midPane.add(videoView, 0, 1);

        songControlPane.add(play, 0, 0);
        songControlPane.add(pause, 1, 0);
        songControlPane.add(skip, 2, 0);

        play.setOnAction((ActionEvent e) -> {
            playSong();
        });
        pause.setOnAction((ActionEvent e) -> {
            pauseSong();
        });
        skip.setOnAction((ActionEvent e) -> {
            skipSong();
        });

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33);

        songControlPane.getColumnConstraints().addAll(col1, col2, col3);

        GridPane.setHalignment(play, HPos.CENTER);
        GridPane.setHalignment(pause, HPos.CENTER);
        GridPane.setHalignment(skip, HPos.CENTER);

        // Right Pane
        playlistControlPane.add(playlistAddButton, 0, 0);
        playlistControlPane.add(playlistAddInput, 1, 0);
        playlistControlPane.add(playlistDeleteButton, 0, 1);
        playlistControlPane.add(playlistDeleteInput, 1, 1);
        playlistControlPane.add(refresh, 0, 2);
        GridPane.setColumnSpan(refresh, 2);
        GridPane.setHalignment(refresh, HPos.CENTER);

        playlistAddButton.setOnAction((ActionEvent e) -> {
            addToPlaylist(playlistAddInput.getText());
        });
        playlistDeleteButton.setOnAction((ActionEvent e) -> {
            deleteFromPlaylist(playlistDeleteInput.getText());
        });
        refresh.setOnAction((ActionEvent e) -> {
            viewPlaylist();
        });

        playlistContents.setPrefWidth(300);
        playlistContents.setMaxWidth(300);
        playlistContents.setPrefHeight(300);
        playlistContents.setMaxHeight(300);
        playlistContents.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));

        rightPane.add(playlistContents, 0, 1);

        primaryStage.setScene(new Scene(root, 1300, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            importMultipleSongs(args[0]);
            launch(args);
        } else if (args.length > 1) {
            System.out.println("Too many arguments supplied, one required");
            System.exit(1);
        } else {
            System.out.println("No file argument supplied");
            System.exit(1);
        }

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
            System.out.println("library imported");

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    static void addSong(String name, String artist, String time, String link) {
        try {
            int newtime = Integer.parseInt(time);
            Song newSong = new Song(name, artist, newtime, link);
            library.put(newSong.getTitle(), newSong);
            status.setText("Song added");
        } catch (NumberFormatException ex) {
            status.setText("Song runtime is not a number, try again");
        }

    }

    static Song searchLibrary(String criteria) {
        if (library.get(criteria) != null) {
            String output = library.get(criteria).toString();
            librarySearchOutput.setText(output);
            return library.get(criteria);
        } else {
            librarySearchOutput.setText("No song with that name found");
            return library.get(criteria);
        }

    }

    static void addToPlaylist(String songName) {
        Song theSong = library.get(songName);
        if (theSong != null) {
            playlist.add(theSong);
            viewPlaylist();
        } else {
            status.setText("No song found");
        }

    }

    static void viewPlaylist() {
        String outputString = "";
        int i = 1;
        for (Song song : playlist) {
            outputString = outputString + i + " - " + song.getTitle() + " - " + song.getArtist() + "\n";
            i++;
        }
        if (outputString.length() == 0) {
            outputString += "No songs in playlist";
        }
        playlistContents.setText(outputString);
    }

    static void deleteFromPlaylist(String index) {
        int oldLength = playlist.size();
        try {
            int newIndex = Integer.parseInt(index);
            playlist.remove(newIndex - 1);
        } catch (IndexOutOfBoundsException ex) {
            status.setText("No song exists at that index");
        } catch (NumberFormatException ex) {
            status.setText("Non-numerical value supplied");
        }
        int newLength = playlist.size();
        if (newLength < oldLength) {
            System.out.println("Song removed");
        } else if (newLength > oldLength) {
            System.out.println("Error");
        } else {
            System.out.println("No change");
        }

        viewPlaylist();
    }

    static void playSong() {
        if (currentSong == null) {
            // no song playing
            skipSong();
        } else {
            // a song is loaded
            if (currentPlayer.getStatus() == Status.valueOf("PAUSED")) {
                currentPlayer.play();
            } else {
                System.out.println("Video is already playing");
            }
        }
    }

    static void pauseSong() {
        if (currentPlayer != null) {
            if (currentPlayer.getStatus() == Status.valueOf("PLAYING")) {
                currentPlayer.pause();
            } else {
                System.out.println("Video was not playing");
            }
        } else {
            System.out.println("No video found");
        }

    }

    static void skipSong() {
        if (playlist.size() > 0) {
            currentSong = playlist.pollFirst();
            viewPlaylist();
            if (currentPlayer != null) {
                currentPlayer.stop();
            }
            if (videoView.isVisible() == false) {
                videoView.setVisible(true);
            }
            System.out.println("Playing " + currentSong.getFileName());
            currentPlayer = convertCurrentSongtoMediaPlayer();
            titleLabel.setText(currentSong.getTitle());
            videoView.setMediaPlayer(currentPlayer);
            currentPlayer.play();
        } else {
            System.out.println("Playlist is empty, cant find next song");
        }
    }

    static MediaPlayer convertCurrentSongtoMediaPlayer() {
        if (currentSong == null) {
            status.setText("There is no available song to play");
            System.out.println("There is no available song to play");
            return null;
        } else {
            File file = new File(currentSong.getFileName());
            String source = file.toURI().toString();
            Media video = new Media(source);
            MediaPlayer player = new MediaPlayer(video);
            player.setOnEndOfMedia(() -> skipSong());
            return player;
        }
    }
}
