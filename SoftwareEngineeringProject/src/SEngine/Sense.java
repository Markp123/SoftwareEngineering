package SEngine;
/**
 * Software Engineering Project 2013 
 * 
 * Representation of Sense instruction
 * 
 * @author DCRichards
 *
 */
public class Sense implements Instruction {
	
	private Direction direction;
	private int state1;
	private int state2;
	private Condition condition;
	private Integer marker;
	
	/**
	 * Constructor for Sense
	 * 
	 * @param direction the direction to sense
	 * @param state1 	the first state option
	 * @param state2 	the second state option
	 * @param condition the condition of the state change
	 * @param marker	the marker for the condition if applicable
	 */
	public Sense(Direction direction, int state1, int state2, Condition condition, int marker) {
		super();
		this.direction = direction;
		this.state1 = state1;
		this.state2 = state2;
		this.condition = condition;
		this.marker = marker;
	}
	
	/**
	 * Constructor for Sense
	 * 
	 * @param direction the direction to sense
	 * @param state1 	the first state option
	 * @param state2 	the second state option
	 * @param condition the condition of the state change
	 */
	public Sense(Direction direction, int state1, int state2, Condition condition) {
		super();
		this.direction = direction;
		this.state1 = state1;
		this.state2 = state2;
		this.condition = condition;
		this.marker = null;
	}
	
	/**
	 * 
	 * @return the sense direction
	 */
	public Direction getDirection() {
		return direction;
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
	 * 
	 * @return the state change condition
	 */
	public Condition getCondition() {
		return condition;
	}
	
	/**
	 * 
	 * @return the marker for the mark condition or null if not applicable
	 */
	public int getMarker(){
		return this.marker;
	}
	
	public String toString() {
		return "Sense " + direction.toString() + " " + state1 + " " 
				+ state2 + " " + condition.toString() + " " + marker;
	}
}