// Josh Liu ID# 260612384

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;

// loads a song from a text file into Miditracks to be played
public class Song{
    
	String myName;
    int myBeatsPerMinute;
    String mySoundbank;
    int myInstrument;
    ArrayList<MidiTrack> myTracks;
    FileReader fr;
    BufferedReader br;
    
    // The constructor of this class
    public Song(){
        initialize();
    }
    
    public void initialize() {
    	// default attributes
        myTracks = new ArrayList<MidiTrack>();
    	myBeatsPerMinute = 200;
        mySoundbank = "";
        myInstrument = 1;
        myName = "Default_Name";
    }

    // GETTER METHODS
    
    public String getName(){
       return myName;
    }

    public String getSoundbank(){
       return mySoundbank;
    }
    
    public int getBPM(){
        return myBeatsPerMinute;
    }

    public ArrayList<MidiTrack> getTracks(){
        return myTracks;
    }

    //reads the text file and loads tracks/attributes from it
    public void loadSongFromFile(String file_path) throws IOException {
		
    	initialize();
    	fr = new FileReader(file_path);
        br = new BufferedReader(fr);
        String tempstring = ""; 
        String substring = "";
        
        // loop line by line until the end of the file
    	while ((tempstring = br.readLine()) != null) {
			
    		// skips over empty lines that may cause errors
    		if (tempstring.trim().isEmpty()) continue;
    		
    		// creates a string of the data contained after the "=" (known format)
    		substring = tempstring.substring(tempstring.indexOf('=') + 2, tempstring.length());
			// checks which attribute the line is referring to and sets it to the data accordingly
    		if (tempstring.startsWith("bpm")) {
				myBeatsPerMinute = Integer.valueOf(substring);
			}
			else if (tempstring.startsWith("name")) {
				myName = substring;
			}
			else if (tempstring.startsWith("soundbank")) {
				mySoundbank = substring;
			}
			else if (tempstring.startsWith("instrument")) {
				myInstrument = Integer.valueOf(substring);
			}
			else if (tempstring.startsWith("track")) {
		    	// loads the string into a MidiTrack and puts that track into the array of tracks
				MidiTrack track = new MidiTrack(myInstrument);
		    	track.loadNoteString(substring);
		    	myTracks.add(track);  
			}
    	}	
    }

    // reverses the tracks
    public void revert(){
        for (int i = 0; i<myTracks.size(); i++){
            myTracks.get(i).revert();
        }
    }
}