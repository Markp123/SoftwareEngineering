package SEngine;
/**
 * Software Engineering Project 2013 
 * 
 * Representation of PickUp instruction
 * 
 * @author DCRichards
 */
public class PickUp implements Instruction {
	
	private int state1;
	private int state2;
	
	/**
	 * Constructor for PickUp
	 * 
	 * @param state1 the first state option
	 * @param state2 the second state option
	 */
	public PickUp(int state1, int state2) {
		super();
		this.state1 = state1;
		this.state2 = state2;
	}
	
	/**
	 * 
	 * @return the first state option
	 */
	public int getState1() {
		return state1;
	}
	
	/**
	 * 
	 * @return the second state option
	 */
	public int getState2() {
		return state2;
	}
	
	/**
	 * @return string representing the instruction
	 */
	public String toString() {
		return "PickUp " + state1 + " " + state2;
	}
}