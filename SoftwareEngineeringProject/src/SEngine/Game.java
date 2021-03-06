
package SEngine;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Point;
import java.lang.Thread;

public class Game {
	private World world;
	private List<Point> cellsToUpdateList = new ArrayList<Point>();
	
	public Game(World world, BrainParser b1, BrainParser b2){
//		ArrayList<Instruction> brain1 = new BrainParser("src/cleverbrain3.brain").parseBrain();
//		ArrayList<Instruction> brain2 = new BrainParser("src/cleverbrain4.brain").parseBrain();
		
		ArrayList<Instruction> brain1 = b1.parseBrain();
		ArrayList<Instruction> brain2 = b2.parseBrain();
		this.world = world;
		int antR = 0;
		int antB = 0;
		for(int x = 0; x<world.getRows(); x++){
			for(int y = 0; y<world.getColumns(); y++){
				Cell c = world.getCell(x,y);
				if(c.getIsRAntHill()){
					c.setAnt(new Ant(Colour.RED, antR, brain1));
					cellsToUpdateList.add(new Point(x,y));
					antR++;
				}
				else if(c.getIsBAntHill()){
					c.setAnt(new Ant(Colour.BLACK, antB, brain2));
					cellsToUpdateList.add(new Point(x,y));
					antB++;
				}
			}
		}
	}
	
	
//	public static void main(String[] args) {
//		new Game(new World(150,150),new BrainParser("brain2.txt"),new BrainParser("horseshoe-1.txt"));
//	}
	
	public String stats(){
		int rAntsFood = 0;
		int bAntsFood = 0;
		for(int x = 0; x<world.getRows(); x++){
			for(int y = 0; y<world.getColumns(); y++){
				Cell c = world.getCell(x,y);
				if(c.getIsRAntHill()){
					rAntsFood += c.getFoodAmount();
				}
				else if(c.getIsBAntHill()){
					bAntsFood += c.getFoodAmount();
				}
			}
		}
		if(bAntsFood > rAntsFood){
			return "black";
		}
		else if(rAntsFood > bAntsFood){
			return "red";
		}
		else{
			return "draw";
		}
	}
    /**
     * checks whether the ant of this id is still alive
     * 
     * @param id int
     * @return boolean
     */
	private boolean ant_is_alive(int id, Colour col){
		boolean found = false;
		int x = 0;
		int y = 0;
		while(x < world.getRows() && !found){
			while(y < world.getColumns() && !found){
				Cell c = world.getCell(x,y);
				if(c.isAnt()){
					if(c.getAnt().getId()==id && c.getAnt().getColour()==col){
						found = true;
					}
				}
				y++;
			}
			y = 0;
			x++;
		}
		return found;
	}
	
    /**
     * Finds the position of the ant given it's id
     * 
     * @param id int
     * @return p int[] array that represents cell position
     */
	public int[] find_ant(int id, Colour col){
		int p[] = new int[2];
		if(ant_is_alive(id, col)){
			boolean found = false;
			int x = 0;
			int y = 0;
			while(x < world.getRows() && !found){
				while(y < world.getColumns() && !found){
					p[0] = x;
					p[1] = y;
					Cell c = world.getCell(p[0],p[1]);
					if(c.isAnt()){
						if(c.getAnt().getId()==id && c.getAnt().getColour()==col){
							found = true;
						}
					}
					y++;
				}
				y = 0;
				x++;
			}
		}
		else{
			return null;
		}
		return p;
	}
	
    /**
     * Checks whether this cell in this position is a rock
     * 
     * @param p int[] that represents cell position
     * @return boolean
     */
	private boolean rocky(int[] p){
		return world.getCell(p[0],p[1]).getIsRock();
	}
	
    /**
     * Returns the ant at given position
     * 
     * @param p in[] that represents cell position
     * @return c.getAnt() Ant at the cell position
     */
	private Ant ant_at(int[] p){
		Cell c = world.getCell(p[0],p[1]);
		return c.getAnt();
	}
	
    /**
     * Checks how many food there is in the given position
     * 
     * @param p int[] that represents cell position
     * @return int of amount of food
     */
	private int food_at(int[] p){
		Cell c = world.getCell(p[0],p[1]);
		if(c.getIsFood()){
			return c.getFoodAmount();
		}
		else{
			return 0;
		}
	}
    /**
     * Method to access cell and set the food amount
     * 
     * @param p int[] that represents cell position
     * @param food int the amount of food to set
     */
	private void set_food_at(int[] p, int food){
		world.getCell(p[0], p[1]).setFood(true);
		world.getCell(p[0], p[1]).setFoodAmount(food);
		cellsToUpdateList.add(new Point(p[0], p[1]));
	}
    /**
     * Method to access cell and set the marker
     * 
     * @param p int[] that represents cell position
     * @param colour Colour which team the marker is for
     * @param i int the marker no. to be set
     */
	public void set_marker_at(int[] p, Colour colour, int i){
		if(check_any_marker_at(p, other_color(colour))){//check for enemy markers
		}
		else{//if there are no enemy markers then let it mark
			if(colour == Colour.BLACK){
				world.getCell(p[0], p[1]).setBMarker(i);
			}
			else{
				world.getCell(p[0], p[1]).setRMarker(i);
			}
		}
	}
    /**
     * Method to access cell and remove the marker
     * 
     * @param p int[] that represents cell position
     * @param colour Colour which team the marker is for
     * @param i int the marker no. to be removed
     */
	public void clear_marker_at(int[] p, Colour colour, int i){
		if(colour == Colour.BLACK){
			world.getCell(p[0], p[1]).removeBMarker(i);
		}
		else{
			world.getCell(p[0], p[1]).removeRMarker(i);
		}
	}
    /**
     * Method to access cell and remove the ant
     * 
     * @param p int[] that represents cell position
     */
	private void clear_ant_at(int[] p){
		world.getCell(p[0], p[1]).removeAnt();
		cellsToUpdateList.add(new Point(p[0], p[1]));
	}
    /**
     * Method to access cell and insert the ant
     * 
     * @param p int[] that represents cell position
     * @param a Ant the ant to be inserted
     */
    public void set_ant_at(int[] p, Ant a){
    	world.getCell(p[0], p[1]).setAnt(a);
    	cellsToUpdateList.add(new Point(p[0], p[1]));
    }
    /**
     * Method to access cell and check if an ant exists in this cell
     * 
     * @param p int[] that represents cell position
     * @return world.getCell(p[0], p[1]).isAnt() boolean for whether an ant exists
     */
	private boolean some_ant_is_at(int[] p){
		return world.getCell(p[0], p[1]).isAnt();
	}
	
    /**
     * Counts the number of enemy ants surrounding the ant at this position
     * 
     * @param p int[] that represents cell position
     * @param c Colour of the ant being surrounded
     * @return int number of ants
     */
    public int adjacent_ants(int[] p, Colour c){
    	int antNum = 0;
    	for (int d = 0; d <= 5; d++){
    		if(world.getCell(adjacent_cell(p,d)[0],adjacent_cell(p,d)[1]).isAnt()){
		    	if(world.getCell(adjacent_cell(p,d)[0],adjacent_cell(p,d)[1]).getAnt().getColour()==other_color(c)){
		    		antNum++;
		    	}
    		}
	    }
    	return antNum;
    }
    
    /**
     * returns a new direction of int after turning
     * 
     * @param lr enum representing left or right
     * @param direction int representing direction
     * @return direction int of it's new direction
     */
	public int Turn(LeftRight lr, int direction){
		int dir=0;
		switch(lr){
		case LEFT:
				dir = (direction+5) % 6;
			break;
		case RIGHT:
				dir = (direction+1) % 6;
			break;
		}
		return dir;
	}
	
    /**
     * Switches colour from RED to BLACK and vice versa
     * 
     * @param c Colour to change
     * @return col the colour that has been changed
     */
	public Colour other_color(Colour c){
		Colour col = null;
		switch(c){
		case RED:
			col = Colour.BLACK;
			break;
		case BLACK:
			col = Colour.RED;
			break;
		}
		return col;
	}
	
    /**
     * Checks if an ant is being surrounded enough to be killed and kills it if so
     * 
     * @param p int[] that represents cell position
     */
    public void check_for_surrounded_ant_at(int[] p){
    	Ant a;
    	if (some_ant_is_at(p)){
    		a = ant_at(p);
    		if (adjacent_ants(p, a.getColour()) >= 5) {
            	clear_ant_at(p);// need to check if method kills the ant
            	int i = 0;
            	if(a.isHas_food()){
            		i = 1;
            	}
            	set_food_at(p, food_at(p) + 3 + i);	
            }
    	}
    }
    
    /**
     * Runs method to check for ants being surrounded in given position and the positions around it
     * 
     * @param p int[] that represents cell position
     */
    public void check_for_surrounded_ants(int[] p){
    	check_for_surrounded_ant_at(p);
	    for (int d = 0; d <= 5; d++){
	    	if(adjacent_cell(p,d)[0]>=0 && adjacent_cell(p,d)[0] < world.getRows() && adjacent_cell(p,d)[1]>=0 && adjacent_cell(p,d)[1] < world.getColumns()){
		    	if(world.getCell(adjacent_cell(p,d)[0],adjacent_cell(p,d)[1]).isAnt()){
		    		check_for_surrounded_ant_at(adjacent_cell(p,d));
		    	}
	    	}
	    }
    }
	
    /**
     * Step method that makes an ant make a move
     * 
     * @param id int the id of the ant to move
     */
	private void step(int id, Colour colour){
	  if (ant_is_alive(id, colour)){
	    int[] p = find_ant(id, colour);
	    Ant a = ant_at(p);
	    if(a.getResting() > 0){
	      a.setResting(a.getResting()-1);
	    }
	    else{
	    	Instruction instr = a.getInstruction();
	    	String name = instr.getClass().getName();
	    	if(name.equals("SEngine.Sense")){
	    		Sense instr2 = (Sense)a.getInstruction();
	    		int[] p2 = sensed_cell(p, a.getDirection(), instr2.getDirection());
		        int st;
		        if(instr2.getCondition()==Condition.MARKER){//if it's a marker condition
		        	if(cell_matches(p2, instr2.getMarker(), a.getColour())){
			        	st = instr2.getState1();
			      	}
			        else{
			        	st = instr2.getState2();
			        }
		        }
		        else{
			        if(cell_matches(p2, instr2.getCondition(), a.getColour())){
			        	st = instr2.getState1();
			      	}
			        else{
			        	st = instr2.getState2();
			        }
		        }
		        a.setState(st);
	    	}
	    	else if(name.equals("SEngine.Mark")){
	    		Mark instr2 = (Mark)a.getInstruction();
	    		set_marker_at(p, a.getColour(), instr2.getMarker());
	        	a.setState(instr2.getState());
	    	}
	    	else if(name.equals("SEngine.Unmark")){
	    		Unmark instr2 = (Unmark)a.getInstruction();
	    		clear_marker_at(p, a.getColour(), instr2.getMarker());
	        	a.setState(instr2.getState());
	    	}
	    	else if(name.equals("SEngine.PickUp")){
	    		PickUp instr2 = (PickUp)a.getInstruction();
	    		if(a.isHas_food() || food_at(p) == 0){
	        		a.setState(instr2.getState2());
	        	}
	        	else{
	        		set_food_at(p, food_at(p) - 1);
	            	a.setHas_food(true);
	            	a.setState(instr2.getState1());
	        	}
	    	}
	    	else if(name.equals("SEngine.Drop")){
	    		Drop instr2 = (Drop)a.getInstruction();
	    		 if(a.isHas_food()){
				        set_food_at(p, food_at(p) + 1);
				        a.setHas_food(false);
				        a.setState(instr2.getState());
			        }
	    	}
	    	else if(name.equals("SEngine.Turn")){
	    		Turn instr2 = (Turn)a.getInstruction();
	    		a.setDirection(Turn(instr2.getDirection(), a.getDirection()));
	        	a.setState(instr2.getState());
	    	}
	    	else if(name.equals("SEngine.Move")){
	    		Move instr2 = (Move)a.getInstruction();
	    		int[] newp = adjacent_cell(p, a.getDirection());
		        if(rocky(newp) || some_ant_is_at(newp)){
		            a.setState(instr2.getState2());
		        }
		        else{
		            clear_ant_at(p);
		            set_ant_at(newp, a);
		            a.setState(instr2.getState1());
		            a.setResting(14);
		            check_for_surrounded_ants(newp);
		        }
	    	}
	    	else if(name.equals("SEngine.Flip")){
	    		Flip instr2 = (Flip)a.getInstruction();
	    		int st;
		        if(randomint(instr2.getP()) == 0){
		        	  st = instr2.getState1();
		        }
		        else{
		        	st = instr2.getState2();
		        }
		        a.setState(st);
	    	}
	    }
	  }
	}
	
    /**
     * Checks if the cell matches the given condition
     * 
     * @param p int[] that represents cell position
     * @param condition Condition enum of a status in a cell
     * @param colour Colour the colour of the ant
     * @return boolean of whether the condition is met
     */
	public boolean cell_matches(int[] p, Condition condition, Colour colour) {
		boolean bool = false;
		//System.out.println(p[0] + " " + p[1]);
		switch(condition){
		case FRIEND:
			if(world.getCell(p[0],p[1]).isAnt()){
				bool = (world.getCell(p[0],p[1]).getAnt().getColour() == colour);
			}
			else{
				bool = false;
			}
			break;
		case FOE:
			if(world.getCell(p[0],p[1]).isAnt()){
				bool = (world.getCell(p[0],p[1]).getAnt().getColour() == other_color(colour));
			}
			else{
				bool = false;
			}
			break;
		case FRIENDWITHFOOD:
			if(world.getCell(p[0],p[1]).isAnt()){
				bool = (world.getCell(p[0],p[1]).getAnt().isHas_food() && world.getCell(p[0],p[1]).getAnt().getColour() == colour);
			}
			else{
				bool = false;
			}
			break;
		case FOEWITHFOOD:
			if(world.getCell(p[0],p[1]).isAnt()){
				bool = (world.getCell(p[0],p[1]).getAnt().isHas_food() && world.getCell(p[0],p[1]).getAnt().getColour() == other_color(colour));
			}
			else{
				bool = false;
			}
			break;
		case FOOD:
			bool = world.getCell(p[0],p[1]).getIsFood();
			break;
		case ROCK:
			bool = world.getCell(p[0],p[1]).getIsRock();
			break;
		case FOEMARKER:
			bool = check_any_marker_at(p, other_color(colour));
			break;
		case HOME:
			if(colour==Colour.RED){
				bool = world.getCell(p[0],p[1]).getIsRAntHill();
			}
			else{
				bool = world.getCell(p[0],p[1]).getIsBAntHill();
			}
			break;
		case FOEHOME:
			if(colour==Colour.BLACK){
				bool = world.getCell(p[0],p[1]).getIsRAntHill();
			}
			else{
				bool = world.getCell(p[0],p[1]).getIsBAntHill();
			}
			break;
		case MARKER://should never be called because a check statement is run before this function does
			break;
		}
		return bool;
	}

	public boolean check_any_marker_at(int[] pos, Colour colour){
		boolean bool = false;
		int i = 0;
		Cell c = world.getCell(pos[0],pos[1]);
		if (colour==Colour.RED){
			while(!bool && i <6){
				bool = c.getRMarker()[i];
				i++;
			}
		}
		else{
			while(!bool && i <6){
				bool = c.getBMarker()[i];
				i++;
			}
		}
		return bool;
	}
	
	public boolean cell_matches(int[] p, int marker, Colour colour){
		Boolean bool;
		if (colour == Colour.RED){
			bool = world.getCell(p[0], p[1]).getRMarker()[marker];
		}
		else{
			bool = world.getCell(p[0], p[1]).getBMarker()[marker];
		}
		return bool;
	}
    /**
     * returns the cell to be seensed after being given the direction attributes
     * 
     * @param p int[] that represents cell position
     * @param direction int that represents the direction an ant is facing
     * @param sense_dir Direction an enum of where the ant should sense
     * @return x int[] the position of the cell to be sensed
     */
	public int[] sensed_cell(int[] p, int direction, Direction sense_dir){
		int[] x = new int[2];
		switch(sense_dir){
		case HERE: x = p;
		break;
		case AHEAD: x = adjacent_cell(p, direction);
		break;
		case LEFTAHEAD: x = adjacent_cell(p, Turn(LeftRight.LEFT, direction));
		break;
		case RIGHTAHEAD: x = adjacent_cell(p, Turn(LeftRight.RIGHT, direction));
		break;
		}
		return x;
	}

    /**
     * Returns the cell position towards the direction given
     * 
     * @param p int[] that represents cell position
     * @param int direction the direction to face
     * @return p int[] the new amended cell position
     */
	public int[] adjacent_cell(int[] newp, int direction){
		int[] p = newp.clone();
	    switch(direction){
		    case 0: p[0]+=1;
		     break;
	     	case 1: 
	     		if((p[1] % 2) == 0){
	     		}
	     		else{
				p[0]+=1;
				}
				p[1]+=1;
			break;
		     case 2: 
		    	 if((p[1] % 2) == 0){
		    		 p[0]-=1;
		    	 }
		    	 p[1]+=1;
		    break;
		     case 3: p[0]-=1;
		    break;
		     case 4: 
		    	 if((p[1] % 2) == 0){
		    		 p[0]-=1;
		    	 }
		    	 p[1]-=1;
		    break;
		     case 5:
		    	 if((p[1] % 2) == 0){
		    	 }
		    	 else{
		    		 p[0]+=1;
		    	 }
		    	 p[1]-=1;
		    break;
		     }
		return p;
	}
	
    /**
     * Random number generator for the thinking of the ant
     * 
     * @param n int the probability chance
     * @return generator.nextInt(n) the int it produced
     */
	public int randomint(int n){
		Random generator = new Random();
		return generator.nextInt(n);
	}
	
    /**
     * The method to run and start the game
     */
	public void runGame()
	{
		WorldGUI view = new WorldGUI(world);

		for(int rounds=0; rounds<1000; rounds++){
			for(int i = 0; i<127; i++){//127 ants per team in the game?
				step(i, Colour.RED);
				step(i, Colour.BLACK);
			}
			//refresh/update representation here
			if(rounds%15 == 0){
				view.update(cellsToUpdateList);
				cellsToUpdateList.clear();
			}
		}
		for(int x = 0; x<world.getRows(); x++){
			for(int y = 0; y<world.getColumns(); y++){
				Cell c = world.getCell(x,y);
				if(c.isAnt()){
					c.removeAnt();
				}
			}
		}
		view.endGame();
	}
}
