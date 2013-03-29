/**
 * Software Engineering Project 2013 
 * 
 * Representation of Mark instruction
 * 
 * @author DCRichards
 */
public class Mark implements Instruction {
	
	private int marker;
	private int state;
	
	/**
	 * Constructor for Mark 
	 * 
	 * @param marker the marker to place
	 * @param state  the state to move to
	 */
	public Mark(int marker, int state) {
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
}
