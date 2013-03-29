/**
 * Software Engineering Project 2013 
 * 
 * Representation of Turn instruction
 * 
 * @author DCRichards
 */
public class Turn implements Instruction {
	
	private Direction direction;
	private int state;
	
	/**
	 * Constructor for Turn
	 * 
	 * @param direction the direction to turn
	 * @param state 	the state to move to
	 */
	public Turn(Direction direction, int state) {
		super();
		this.direction = direction;
		this.state = state;
	}
	
	/**
	 * 
	 * @return the direction to turn
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * 
	 * @return the state to move to
	 */
	public int getState() {
		return state;
	}
}