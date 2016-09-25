// Josh Liu ID# 260612384

import java.util.ArrayList;
import java.util.Hashtable;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

// takes in a Song and converts it to a readable text file
public class SongWriter{
    
	private Hashtable<Integer,String> pitchToNote;
    
    // The constructor of this class
    public SongWriter(){
        this.initPitchToNoteDictionary();
    }
    
    // This initialises the pitchToNote dictionary,
    // which will be used by you to convert pitch numbers
    // to note letters
    public void initPitchToNoteDictionary(){
        pitchToNote  = new Hashtable<Integer, String>();
        pitchToNote.put(60, "C");
        pitchToNote.put(61, "C#");
        pitchToNote.put(62, "D");
        pitchToNote.put(63, "D#");
        pitchToNote.put(64, "E");
        pitchToNote.put(65, "F");
        pitchToNote.put(66, "F#");
        pitchToNote.put(67, "G");
        pitchToNote.put(68, "G#");
        pitchToNote.put(69, "A");
        pitchToNote.put(70, "A#");
        pitchToNote.put(71, "B");
    }

    // This method converts a single MidiNote to its notestring representation
    public String noteToString(MidiNote note){
        
    	// creates a string and appends the duration of the note, and then the letter (using the hashtable)
    	// octaves are not accounted for in this method (other than shifting the pitch to fit a key in the table)
    	String result = "";
        if (note.getDuration() > 1) {
            result += note.getDuration();
        }
        if (note.isSilent()) {
        	result += "P";
        }
        else {
        	result += pitchToNote.get(note.getPitch() - note.getOctave()*12); 
        }
        return result;
    }

    // This method converts a MidiTrack to its notestring representation.
    // You should use the noteToString method here
    public  String trackToString(MidiTrack track){
        
    	ArrayList<MidiNote> notes = track.getNotes();
        // contains the entire string of the notes 
    	String result = "";
        // keeps track of what the octave is 
        int previous_octave = 0;
        
        // loops through the array of notes
        for (MidiNote n: notes) {
	        if (!n.isSilent()) {
	        	// detect changes in octave and append the correct character(s) to the result
	        	if (n.getOctave() > previous_octave) {
	        		for (int i = previous_octave; i < n.getOctave(); i++) {
	            		result += ">";
	        		}
	        	}
	        	else if (n.getOctave() < previous_octave) {
	        		for (int j = n.getOctave(); j < previous_octave; j++) {
	            		result += "<";
	        		}
	        	}
	        	// set previous octave to the current octave
	        	previous_octave = n.getOctave();
	        }	
	        // append the string representation of the note to the result
        	result += noteToString(n);
        }
        return result;
    }

    // writes the song into a specified file (path is inputed). Does not create a file but only
    // overwrites an existing one.
    public void writeToFile(Song s1, String file_path) throws IOException {
    	
    	// find the file 
    	FileWriter fw = new FileWriter(file_path);
    	BufferedWriter bw = new BufferedWriter(fw);
    	// writes the attributes in the correct order (default attributes are also written)
    	bw.write("bpm = " + s1.getBPM());
    	bw.newLine();
    	bw.write("name = " + s1.getName());
    	bw.newLine();
    	bw.write("soundbank = " + s1.getSoundbank());
    	bw.newLine();
    	// writes the tracks with the corresponding instruments, using trackToString
    	for (MidiTrack m: s1.myTracks) {
        	bw.write("instrument = " + m.getInstrumentId());
        	bw.newLine();
        	bw.write("track = " + trackToString(m));
        	bw.newLine();
    	}	
    	bw.close();
    }

    public static void main( String[] args){
        
    	// scanner to take in path
    	Scanner scan = new Scanner(System.in);
    	System.out.println("Enter the file path of the song to be reversed...");
    	String path = scan.nextLine();
    	scan.close();
    	    
    	// creating the song from the file
    	Song song = new Song();
        try {
        	song.loadSongFromFile(path);
        } catch (IOException e) {
        	System.out.println("error: file was not able to be read.");
        	e.printStackTrace();
        }  
        // reverting the song
        song.revert();
        
        // changing the path name to the reverse file
    	path = path.replaceAll(Pattern.quote(".txt"), "_reverse.txt");          
       
    	// creating a reverse file in the same directory and writing the reversed song
    	SongWriter sw = new SongWriter();
        try {
        	File file = new File(path);
        	file.createNewFile();
        } catch (IOException ex) {
        	System.out.println("error: path not found/file unable to be created.");
        	ex.printStackTrace();
        }
        
        try {
			sw.writeToFile(song, path);
		} catch (IOException e) {
			System.out.println("error: could not find file to write to.");
			e.printStackTrace();
		}
        
    }
}