// Josh Liu ID# 260612384

import java.io.IOException;
import java.util.Scanner;

// Plays a song from a text file
public class PlaySong{
   
	public static void main( String[] args){
        
		// creating a scanner to take in the filepath from the user
		Scanner scan = new Scanner(System.in);
		System.out.println("input the file path of your song...");
		String path = scan.nextLine();
		scan.close();
		
		// Creating the interpreter and Song
    	MusicInterpreter myMusicPlayer = new MusicInterpreter();
        Song song = new Song();
        // the file is attempted to be read (put your file path in the method for it to work)
        // errors are caught and reported accordingly
        try {
        	song.loadSongFromFile(path);
        } catch (IOException e) {
        	System.out.println("error: file was not able to be read.");
        	e.printStackTrace();
        }  
        // loads the song into the interpreter and plays it 
        myMusicPlayer.loadSong(song);
        myMusicPlayer.play();
        myMusicPlayer.close();
    }
}