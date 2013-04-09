package SEngine;
/**
 * Software Engineering Project 2013 
 * 
 * Representation of Drop instruction
 * 
 * @author DCRichards
 */
public class Drop implements Instruction {
	
	private int state;
	
	/**
	 * Constructor for Drop
	 * 
	 * @param state the state to move to
	 */
	public Drop(int state) {
		super();
		this.state = state;
	}
	
	/**
	 * 
	 * @return the state to move to
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * @return the string representing the instruction
	 */
	public String toString() {
		return "Drop " + state;
	}
	
	
}
