import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Karaoke {

    static ArrayList<Song> library = new ArrayList<>();
    static Queue<Song> playlist = new LinkedList<>();
    static Song currentSong;

    public static void main(String[] args) {

        importMultipleSongs(args[0]);

    }

    static void importMultipleSongs(String path){
        String filePath = System.getProperty("user.dir") + File.separator + path;
        try {
            InputStream inputStream = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] array = line.split("\t");
                Song newSong = new Song(array[0], array[1], Integer.parseInt(array[2]), array[3]);
                library.add(newSong);
            }
            Collections.sort(library);
            for (Song song : library) {
                System.out.println(song.getTitle());
            };
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    static void importOneSong(String name, String artist, int time, String link){
        Song newSong = new Song(name, artist, time, link);
        library.add(newSong);
    }

    static Song searchLibrary(String criteria){
        if (binarySearch(criteria) != 0) {

        } 
        
    }

    static void addToPlaylist(Song song){
        playlist.add(song);
    }

    static void playSong(){
        if (playlist.size() > 0){
            currentSong = playlist.remove();
            // play the song
        } else {
            System.out.println("Playlist is empty");
            // show this on screen
        }
    }

    static void viewPlaylist(){
        for (Song song : playlist){
            System.out.println(song);
        }
        // refit to print to ui
    }

    static void deleteFromPlaylist(int index){
        playlist.remove(index);
    }

    static void binarySearch(String input){};
}