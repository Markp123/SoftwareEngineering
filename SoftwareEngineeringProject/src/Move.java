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
	 * 
	 * @return
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

}