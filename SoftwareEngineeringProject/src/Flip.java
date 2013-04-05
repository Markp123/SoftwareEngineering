/**
 * Software Engineering Project 2013 
 * 
 * Representation of Flip instruction
 * 
 * @author DCRichards
 */
public class Flip implements Instruction {
	
	private int p;
	private int state1;
	private int state2;
	
	/**
	 * Constructor for Flip
	 * 
	 * @param p 	  the random number value
	 * @param state1  the first state option
	 * @param state2  the second state option
	 */
	public Flip(int p, int state1, int state2) {
		this.p = p;
		this.state1 = state1;
		this.state2 = state2;
	}
	
	/**
	 * 
	 * @return the random number value
	 */
	public int getP() {
		return p;
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
		 return "Flip " + p + " " + state1 + " " + state2;
	}
}