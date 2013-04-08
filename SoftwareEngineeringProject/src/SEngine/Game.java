
package SEngine;
import java.util.Random;
import java.lang.Thread;

public class Game {
	private World world;
	public Game(World world){
		this.world = world;
		int antR = 0;
		int antB = 0;
		for(int x = 0; x<150; x++){
			for(int y = 0; y<150; y++){
				Cell c = world.getCell(x,y);
				if(c.getIsRAntHill()){
					c.setAnt(new Ant(Colour.RED, antR, brain1));
					antR++;
				}
				else if(c.getIsBAntHill()){
					c.setAnt(new Ant(Colour.BLACK, antB, brain2));
					antB++;
				}
			}
		}
	}
	
	public void stats(){
		int rAntsFood = 0;
		int bAntsFood = 0;
		for(int x = 0; x<150; x++){
			for(int y = 0; y<150; y++){
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
			//blk ants win
		}
		else if(rAntsFood > bAntsFood){
			//red ants win
		}
		else{
			//same food amount
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
		while(x < 150 && y < 150 && !found){
			while(x < 150 && y < 150 && !found){
				Cell c = world.getCell(x,y);
				if(c.getAnt().getId()==id && c.getAnt().getColour()==col){
					found = true;
				}
				y++;
			}
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
	private int[] find_ant(int id, Colour col){
		int p[] = new int[2];
		if(ant_is_alive(id, col)){
			boolean found = false;
			int x = 0;
			int y = 0;
			while(x < 150 && y < 150 && !found){
				while(x < 150 && y < 150 && !found){
					p[0] = x;
					p[1] = y;
					Cell c = world.getCell(p[0],p[1]);
					if(c.getAnt().getId()==id && c.getAnt().getColour()==col){
						found = true;
					}
					y++;
				}
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
	}
    /**
     * Method to access cell and set the marker
     * 
     * @param p int[] that represents cell position
     * @param colour Colour which team the marker is for
     * @param i int the marker no. to be set
     */
	private void set_marker_at(int[] p, Colour colour, int i){
		if(colour == Colour.BLACK){
			world.getCell(p[0], p[1]).setBMarker(i);
		}
		else{
			world.getCell(p[0], p[1]).setRMarker(i);
		}
	}
    /**
     * Method to access cell and remove the marker
     * 
     * @param p int[] that represents cell position
     * @param colour Colour which team the marker is for
     * @param i int the marker no. to be removed
     */
	private void clear_marker_at(int[] p, Colour colour, int i){
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
	}
    /**
     * Method to access cell and insert the ant
     * 
     * @param p int[] that represents cell position
     * @param a Ant the ant to be inserted
     */
    private void set_ant_at(int[] p, Ant a){
    	world.getCell(p[0], p[1]).setAnt(a);
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
    private int adjacent_ants(int[] p, Colour c){
    	int antNum = 0;
    	for (int d = 0; d <= 5; d++){
	    	if(world.getCell(adjacent_cell(p,d)[0],adjacent_cell(p,d)[1]).getAnt().getColour()==other_color(c)){
	    		antNum++;
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
	private int Turn(LeftRight lr, int direction){
		int dir=0;
		switch(lr){
		case LEFT:
			if(direction==0){
				dir = 5;
			}
			else{
				dir = (direction-1);
			}
			break;
		case RIGHT:
			if(direction==5){
				dir = 0;
			}
			else{
				dir = (direction+1);
			}
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
	private Colour other_color(Colour c){
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
    private void check_for_surrounded_ant_at(int[] p){
    	Ant a;
    	if (some_ant_is_at(p)){
    		a = ant_at(p);
    		if (adjacent_ants(p, other_color(a.getColour())) >= 5) {
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
    private void check_for_surrounded_ants(int[] p){
    	check_for_surrounded_ant_at(p);
	    for (int d = 0; d <= 5; d++){
	    	check_for_surrounded_ant_at(adjacent_cell(p,d));
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
	    	if(name.equals("Sense")){
	    		Sense instr2 = (Sense)a.getInstruction();
	    		int[] p2 = sensed_cell(p, a.getDirection(), instr2.getDirection());
		        int st;
		        if(cell_matches(p2, instr2.getCondition(), a.getColour())){
		        	st = instr2.getState1();
		      	}
		        else{
		        	st = instr2.getState2();
		        }
		        a.setState(st);
	    	}
	    	else if(name.equals("Mark")){
	    		Mark instr2 = (Mark)a.getInstruction();
	    		set_marker_at(p, a.getColour(), instr2.getMarker());
	        	a.setState(instr2.getState());
	    	}
	    	else if(name.equals("Unmark")){
	    		Unmark instr2 = (Unmark)a.getInstruction();
	    		clear_marker_at(p, a.getColour(), instr2.getMarker());
	        	a.setState(instr2.getState());
	    	}
	    	else if(name.equals("PickUp")){
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
	    	else if(name.equals("Drop")){
	    		Drop instr2 = (Drop)a.getInstruction();
	    		 if(a.isHas_food()){
				        set_food_at(p, food_at(p) + 1);
				        a.setHas_food(false);
				        a.setState(instr2.getState());
			        }
	    	}
	    	else if(name.equals("Turn")){
	    		Turn instr2 = (Turn)a.getInstruction();
	    		a.setDirection(Turn(instr2.getDirection(), a.getDirection()));
	        	a.setState(instr2.getState());
	    	}
	    	else if(name.equals("Move")){
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
	    	else if(name.equals("Flip")){
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
	private boolean cell_matches(int[] p, Condition condition, Colour colour) {
		boolean bool = false;
		switch(condition){
		case FRIEND:
			bool = (world.getCell(p[0],p[1]).getAnt().getColour() == colour);
			break;
		case FOE:
			bool = (world.getCell(p[0],p[1]).getAnt().getColour() == other_color(colour));
			break;
		case FRIENDWITHFOOD:
			bool = (world.getCell(p[0],p[1]).getAnt().isHas_food() && world.getCell(p[0],p[1]).getAnt().getColour() == colour);
			break;
		case FOEWITHFOOD:
			bool = (world.getCell(p[0],p[1]).getAnt().isHas_food() && world.getCell(p[0],p[1]).getAnt().getColour() == other_color(colour));
			break;
		case FOOD:
			bool = world.getCell(p[0],p[1]).getIsFood();
			break;
		case ROCK:
			bool = world.getCell(p[0],p[1]).getIsRock();
			break;
		case MARKER:
			bool = false; //marker not implemented yet
			break;
		case FOEMARKER:
			bool = false; //marker not implemented yet
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
	private int[] sensed_cell(int[] p, int direction, Direction sense_dir){
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
	private int[] adjacent_cell(int[] p, int direction){
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
		    		 p[0]+=1;
		    	 }
		    	 p[0]-=1;
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
	private int randomint(int n){
		Random generator = new Random();
		return generator.nextInt(n);
	}
	
    /**
     * The method to run and start the game
     */
	private void runGame(){
		for(int rounds=0; rounds<300000; rounds++){
			for(int i = 0; i<91; i++){//91 ants per team in the game?
			step(i, Colour.RED);
			step(i, Colour.BLACK);
			i++;
			}
			try{
				Thread.sleep(1000);//sleep for 1000ms
			}
			catch(Exception e){
				//If thread interrupted by another thread
			}
			//refresh/update representation here
		}
	}
}