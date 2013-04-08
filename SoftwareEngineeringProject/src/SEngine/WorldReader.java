package SEngine;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Software Engineering Project 2013
 * 
 * Handles the basic file IO for reading a world file
 * 
 * @author DCRichards
 */
public class WorldReader {
	
	BufferedReader reader;
	int rows;
	int columns;
	String[][] worldArray;
	
	/**
	 * constructor for WorldReader
	 * 
	 * @param filename the location of the world file
	 */
	public WorldReader(String filename) {
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		} catch (FileNotFoundException fnf) {
			//error message 
		}
	}
	
	/**
	 * read in from the world file
	 * 
	 * @return a 2D string representation of the world
	 */
	public String[][] read() {
		try {
			StringTokenizer line;
			rows = Integer.parseInt(reader.readLine());
			columns = Integer.parseInt(reader.readLine());
			worldArray = new String[rows][columns];
			//iterate through each row and column and store symbol
			for (int i = 0; i < columns; i++) {
				line = new StringTokenizer(reader.readLine());
				for (int j = 0; j < rows; j++) {
					worldArray[i][j] = line.nextToken();
				}
			}
			return worldArray;
		} catch (IOException ioe) {
			//error
			return null;
		}
	}
	
	/**
	 * 
	 * @return the number of rows
	 */
	public int getRows() {
		return this.rows;
	}
	
	/**
	 * 
	 * @return the number of columns
	 */
	public int getColumns() {
		return this.columns;
	}
}
