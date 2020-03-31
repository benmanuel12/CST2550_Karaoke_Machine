

public class Song implements Comparable<Song>{
    private String title;
    private String artist;
    private int runningTime;
    private String fileName;

    public Song(String title, String artist, int runningTime, String fileName){
        this.title = title;
        this.artist = artist;
        this.runningTime = runningTime;
        this.fileName = fileName;
    }

    public String getTitle(){
        return this.title;
    }

    public String getArtist(){
        return this.artist;
    }

    public int getRunningTime(){
        return this.runningTime;
    }

    public String getFileName(){
        return this.fileName;
    }

    @Override
    public String toString(){
        return "\nTitle: " + title + "\nArtist: " + artist + "\nRunning time: " + runningTime + "\nFile name: " + fileName;
    }
    
    @Override
    public int compareTo(Song other){
        return getTitle().compareTo(other.getTitle());
    }
}