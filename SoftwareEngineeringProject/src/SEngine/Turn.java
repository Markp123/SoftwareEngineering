package SEngine;
/**
 * Software Engineering Project 2013 
 * 
 * Representation of Turn instruction
 * 
 * @author DCRichards
 */
public class Turn implements Instruction {
	
	private LeftRight lr;
	private int state;
	
	/**
	 * Constructor for Turn
	 * 
	 * @param direction the direction to turn
	 * @param state 	the state to move to
	 */
	public Turn(LeftRight lr, int state) {
		super();
		this.lr = lr;
		this.state = state;
	}
	
	/**
	 * 
	 * @return the direction to turn
	 */
	public LeftRight getDirection() {
		return lr;
	}
	
	/**
	 * 
	 * @return the state to move to
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * @return string representing the instruction
	 */
	public String toString() {
		return "Turn " + lr.toString() + " " + state;
	}
}