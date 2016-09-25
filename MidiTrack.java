// Josh Liu ID# 260612384

import java.util.ArrayList;
import java.util.Hashtable;

// Contains the array of MidiNotes to be converted into music. Method for Text to MidiNote conversion
public class MidiTrack{
	
	// table for note to pitch conversion
    private Hashtable<Character,Integer> noteToPitch;

    // array that holds converted Notes
    private ArrayList<MidiNote> notes;
    private int instrumentId;
    
    // The constructor for this class
    public MidiTrack(int instrumentId){
        notes = new ArrayList<MidiNote>();
        this.instrumentId = instrumentId;
        this.initPitchDictionary();
    }

    // This initialises the noteToPitch dictionary,
    // which will be used by you to convert note letters
    // to pitch numbers
    public void initPitchDictionary(){
        noteToPitch  = new Hashtable<Character, Integer>();
        noteToPitch.put('C', 60);
        noteToPitch.put('D', 62);
        noteToPitch.put('E', 64);
        noteToPitch.put('F', 65);
        noteToPitch.put('G', 67);
        noteToPitch.put('A', 69);
        noteToPitch.put('B', 71);
    }

    // GETTER METHODS
    public ArrayList<MidiNote> getNotes(){
        return notes;
    }
    
    public int getInstrumentId(){
        return instrumentId;
    }
    
    // This method converts notestrings like
    // <<3E3P2E2GP2EPDP8C<8B>
    // to an ArrayList of MidiNote objects 
    // ( the notes attribute of this class )
    public void loadNoteString(String notestring){
       
        // convert the letters in the notestring to upper case
        notestring = notestring.toUpperCase();
        // initialize variables
        int duration = 0;
        int pitch = 0;
        int octave = 0;
        String note = "";
        // an array of notes that correlate to a pitch or silence
        char[] validnotes = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'P'};
        // counter to keep track of the position in the string
        int start = 0;
        
        // loop through the input string character by character
        for (int i = 0; i < notestring.length(); i++) {
        	// if octave char, change octave accordingly. increment the position.
        	if (notestring.charAt(i) == '<') {
        		octave--;
        		start = i+1;
        	}
        	else if (notestring.charAt(i) == '>') {
        		octave++;
        		start = i+1;
        	}
        	else {
        		// loop through the array of valid Notes and detects a match at each position
            	for (char c: validnotes) {
            		if (notestring.charAt(i) == c) {
            			// if there's a match, cut a substring of the note (duration and note character) using start
            			note = notestring.substring(start, i+1);
            			MidiNote tempnote;
            			// if there is a number in the substring, duration equals that number (string is trimmed to only numbers)
            			if (note.matches(".*\\d+.*")) {
                			duration = Integer.valueOf(note.replaceAll("[\\p{Alpha}#!]", ""));
            			}
            			// otherwise the default is 1
            			else {
            				duration = 1;
            			}
            			// if it is a silence, create a silent note of the duration and increment position
            			if (note.contains("P")) {
            				tempnote = new MidiNote(60, duration);
            				tempnote.setSilent(true);
            				start = i + 1;
            			}
            			else {
            				// trims substring to only the letter and gets a pitch value from the hashtable
                			pitch += noteToPitch.get(note.replaceAll("[^A-G]", "").charAt(0));
                			// shifts the pitch for the current octave
                			pitch += (octave*12);
                			// checks if there is a sharp or flat after the letter and shifts the pitch accordingly.
                			// start is incremented differently depending if there is an accidental or not
                        	if (i != (notestring.length() - 1) && notestring.charAt(i + 1) == '#') { 
                        		pitch += 1;
                        		start = i + 2;
                        	}
                        	else if (i != (notestring.length() - 1) && notestring.charAt(i + 1) == '!') {
                        		pitch -= 1;
                        		start = i + 2;
                        	}
                        	else {
                        		start = i + 1;
                        	}
                        	// the note is created with the calculated pitch and duration
                   			tempnote = new MidiNote(pitch, duration);
            			}
            			// the note is added to the array
            			notes.add(tempnote);
            		}
            	}
        	}
        	// duration and pitch are reset for the next note (octave remains shifted)
            duration = 0;
            pitch = 0;   	
        }
        
    }

    public void revert(){
        ArrayList<MidiNote> reversedTrack = new ArrayList<MidiNote>();     
        for ( int i = notes.size() - 1; i >= 0; i--){
            MidiNote oldNote = notes.get(i);
            // create a newNote
            MidiNote newNote = new MidiNote(oldNote.getPitch(), oldNote.getDuration());
            
            // check if the note was a pause
            if(oldNote.isSilent()){
                newNote.setSilent(true);
            }
             
            // add the note to the new arraylist
            reversedTrack.add(newNote);
        }
        notes = reversedTrack;
    }

    // This will only be called if you try to run this file directly
    // You may use this to test your code.
    public static void main(String[] args){
       
    	String notestring = "<<3E3P2E2GP2EPDP8C<8B>3E3P2E2GP2EPDP8C<8B>";
        MusicInterpreter player = new MusicInterpreter();
        player.setBPM(200);
        MidiTrack track = new MidiTrack(99);
        track.loadNoteString(notestring);
        
        // player.loadSingleTrack(track);
        // player.play();
        // player.close();
        
    }
}

