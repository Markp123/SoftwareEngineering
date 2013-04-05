package SEngine;

public class Ant {
	private Colour colour;
	private int state;
	private int resting;
	private int direction;
	private boolean has_food;
	private int brainNum;
	public Ant(Colour colour, int brainNum){
		direction = 0;
		has_food = false;
		state = 0;
		resting = 0;
		this.colour = colour;
		this.brainNum = brainNum;
	}
	public int getBrainNum() {
		return brainNum;
	}
	public void setBrainNum(int brainNum) {
		this.brainNum = brainNum;
	}
	public Colour getColour() {
		return colour;
	}
	public void setColour(Colour colour) {
		this.colour = colour;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getResting() {
		return resting;
	}
	public void setResting(int resting) {
		this.resting = resting;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public boolean isHas_food() {
		return has_food;
	}
	public void setHas_food(boolean has_food) {
		this.has_food = has_food;
	}
}
