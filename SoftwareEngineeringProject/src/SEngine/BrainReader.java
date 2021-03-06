package SEngine;
import java.io.*;
import java.util.ArrayList;

/**
 * Software Engineering Project 2013
 * 
 * Handles the basic file IO for reading an ant brain
 * 
 * @author DCRichards
 */
public class BrainReader {
	
	BufferedReader reader;
	
	/**
	 * Constructor for reader
	 * 
	 * @param URL the URL of the file to read
	 */
	public BrainReader(String filename) {
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		} catch (FileNotFoundException fnf) {
			//error message 
		}
	}
	
	/**
	 * read each line of the file into a list of instructions
	 * 
	 * @return an array of instructions
	 */
	public ArrayList<String> read() {
		ArrayList<String> lines = new ArrayList<String>();
		String currentLine = null;
		try {
			//read each line of the file in turn
			while ((currentLine = reader.readLine()) != null) {
				lines.add(currentLine);
			}
		} catch (IOException ex) {
			//error message
		}
		return lines;
	}
}