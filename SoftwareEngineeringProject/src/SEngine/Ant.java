package SEngine;

import java.util.ArrayList;

public class Ant {
	private Colour colour;
	private int state;
	private int resting;
	private int direction;
	private boolean has_food;
	private ArrayList<Instruction> brain;
	public Ant(Colour colour, ArrayList<Instruction> brain){
		direction = 0;
		has_food = false;
		state = 0;
		resting = 0;
		this.colour = colour;
		this.brain = brain;
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
	/** returns the instruction from the brain of the state in this ant **/
	public ArrayList<Instruction> getBrain() {
		return brain;
	}

	public void setState(int state) {
		this.state = state;
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
	public Instruction getInstruction() {
		return brain.get(state);
	}

	public void setBrain(ArrayList<Instruction> brain) {
		this.brain = brain;
	}
}