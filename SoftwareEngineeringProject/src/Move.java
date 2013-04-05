/**
 * Software Engineering Project 2013 
 * 
 * Representation of Move instruction
 * 
 * @author DCRichards
 */
public class Move implements Instruction {
	
	private int state1;
	private int state2;
	
	/**
	 * Constructor for Move
	 * 
	 * @param st1
	 * @param st2
	 */
	public Move(int st1, int st2) {
		this.state1 = st1;
		this.state2 = st2;
	}
	
	/**
	 * 
	 * @return the first state
	 */
	public int getState1() {
		return state1;
	}
	
	/**
	 * 
	 * @return the second state
	 */
	public int getState2() {
		return state2;
	}
	
	/**
	 * @return string representing the instruction
	 */
	public String toString() {
		return "Move " + state1 + " " + state2;
	}

}