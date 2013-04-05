package SEngine;

import java.util.Random;

public class Game {
	private World world;
	public Game(World world){
		this.world = world;
	}
	private boolean ant_is_alive(int id){
		return world.getAnt(id);
	}
	private int[] find_ant(int id){
		int p[] = new int[2];
		if(ant_is_alive(id)){
			boolean found;
			int x = 0;
			int y = 0;
			while(x < 150 && y < 150 || found){
				p[0] = x;
				p[1] = y;
				Cell c = world.getCell(p[0],p[1]);
				if(c.getAnt().getID()==id){
					found = true;
				}
				x++;
				y++;
			}
		}
		else{
			return null;
		}
		return p;
	}
	private boolean rocky(int[] p){
		return world.getCell(p[0],p[1]).getIsRock();
	}
	private Ant ant_at(int[] p){
		Cell c = world.getCell(p[0],p[1]);
		return null;
	}
	private int food_at(int[] p){
		Cell c = world.getCell(p[0],p[1]);
		if(c.getIsFood()){
			return c.getFoodAmount();
		}
		else{
			return 0;
		}
	}
	private void set_food_at(int[] p, int food){
		world.getCell(p[0], p[1]).setFood(true);
		world.getCell(p[0], p[1]).setFoodAmount(food);
	}
	private void set_marker_at(int[] p, Colour colour, int i){
		
	}
	private void clear_marker_at(int[] p, Colour colour, int i){
		
	}
	private void clear_ant_at(int[] p){
		
	}
    private void set_ant_at(int[] newp, Ant a){
    	
    }
	private boolean some_ant_is_at(int[] newp){
		return false;
	}
    private int adjacent_ants(int[] p, Colour c){
    	return 0;//returns amount of enemy ants around the ant in this position
    }
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

    private void check_for_surrounded_ants(int[] p){
    	check_for_surrounded_ant_at(p);
	    for (int d = 0; d <= 5; d++){
	    	check_for_surrounded_ant_at(adjacent_cell(p,d));
	    }
    }
	
	private void step(int id){
	  if (ant_is_alive(id)){
	    int[] p = find_ant(id);
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
	
	private boolean cell_matches(int[] p2, Condition condition, Colour colour) {
		// TODO Auto-generated method stub
		return false;
	}
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

	private int[] adjacent_cell(int[] p, int direction){
    	switch(direction){
    	case 0: p[0]+=1;
    			break;
    	case 1: if((p[1] % 2) == 0){
				}
				else{
					p[0]+=1;
				}
				p[1]+=1;
				break;
    	case 2: if((p[1] % 2) == 0){
    				p[0]-=1;
				}
				p[1]+=1;
				break;
    	case 3: p[0]-=1;
				break;
    	case 4: if((p[1] % 2) == 0){
    				p[0]-=1;
				}
				p[1]-=1;
				break;
    	case 5: if((p[1] % 2) == 0){
    				p[0]+=1;
				}
				p[0]-=1;
				break;
    	}
    	return p;
	}
	
	private int randomint(int n){
		Random generator = new Random();
		return generator.nextInt(n);
	}
	private void runGame(){
		for(int rounds=0; rounds<300000; rounds++){
			for(int i = 0; i<182; i++){//182 ants in the game?
				step(i);
				i++;
			}
		}
	}
}
