package SEngine;
/**
 * Software Engineering Project 2013 
 * 
 * Representation of Unmark instruction
 * 
 * @author DCRichards
 *
 */
public class Unmark implements Instruction {
	
	private int marker;
	private int state;
	
	/**
	 * Constructor for Mark 
	 * 
	 * @param marker the marker to place
	 * @param state  the state to move to
	 */
	public Unmark(int marker, int state) {
		super();
		this.marker = marker;
		this.state = state;
	}
	
	/**
	 * 
	 * @return the number of the marker
	 */
	public int getMarker() {
		return marker;
	}
	
	/**
	 * 
	 * @return the state
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * @return string representing the instruction
	 */
	public String toString() {
		return "Unmark " + marker + " " + state;
	}
}
