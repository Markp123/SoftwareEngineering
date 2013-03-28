import java.util.Random;

public class Game {
	private World world;
	private enum turn{
		Left, Right;
	}
	private enum senseDir{
		Here,Ahead,LeftAhead,RightAhead;
	}
	private enum Condition{
		Friend, Foe, FriendWithFood, FoeWithFood, Food, Rock, Marker, FoeMarker, Home, FoeHome;
	}
	public Game(World world){
		this.world = world;
	}
	private boolean ant_is_alive(int id){
		return false;
	}
	private int[] find_ant(int id){
		return null;
	}
	private Ant ant_at(int[] p){
		return null;
	}
	private int food_at(int[] p){
		return null;
	}
	private void set_food_at(int[] p, int food){
		
	}
	private void set_marker_at(int[] p, Colour colour, int i){
		
	}
	private void clear_marker_at(int[] p, Colour colour, int i){
		
	}
	private void Turn(turn lr, int direction){
		
	}
	private void clear_ant_at(int[] p){
		
	}
    private void set_ant_at(int[] newp, Ant a){
    	
    }
	private boolean rocky(int[] p){
		return false;
	}
	private void check_for_surrounded_ants(int[] newp){
		
	}
	private boolean some_ant_is_at(int[] newp){
		return false;
	}
	private Instruction get_instruction(Colour colour, int state){
		return;
	}
	private void step(int id){
	  if (ant_is_alive(id)){
	    int[] p = find_ant(id);
	    Ant a = ant_at(p);
	    if(a.getResting() > 0){
	      a.setResting(a.getResting()-1);
	    }
	    else{
	      switch(get_instruction(a.getColour(), a.getState())){
	        case Sense(sensedir, st1, st2, cond):
		        int[] p2 = sensed_cell(p, a.getDirection(), sensedir);
		        int st;
		        if(cell_matches(p2, cond, a.getColour())){
		        	st = st1;
		      	}
		        else{
		        	st = st2;
		        }
		        a.setState(st);
		        break;
	        case Mark(i, st):
	        	set_marker_at(p, a.getColour(), i);
	        	a.setState(st);
	        	break;
	        case Unmark(i, st):
	        	clear_marker_at(p, a.getColour(), i);
	        	a.setState(st);
	        	break;
	        case PickUp(st1, st2):
	        	if(a.isHas_food() || food_at(p) == 0){
	        		a.setState(st2);
	        	}
	        	else{
	        		set_food_at(p, food_at(p) - 1);
	            	a.setHas_food(true);
	            	a.setState(st1);
	        	}
	            break;
	        case Drop(st):
		        if(a.isHas_food()){
			        set_food_at(p, food_at(p) + 1);
			        a.setHas_food(false);
			        a.setState(st);
		        }
	          break;
	        case Turn(lr, st):
		        setDirection(a, turn(lr, a.getDirection()));
	        	a.setState(st);
		        break;
	        case Move(st1, st2):
	        	int[] newp = adjacent_cell(p, a.getDirection());
		        if(rocky(newp) || some_ant_is_at(newp)){
		            set_state(a, st2);
		        }
		        else{
		            clear_ant_at(p);
		            set_ant_at(newp, a);
		            a.setState(st1);
		            a.setResting(14);
		            check_for_surrounded_ants(newp);
		        }
		        break;
	        case Flip(n, st1, st2):
	        	int st;
		        if(randomint(n) == 0){
		        	  st = st1
		        }
		        else{
		        	st = st2;
		        }
		        a.setState(st);
		        break;
	      }
	    }
	  }
	}
	
	private int[] sensed_cell(int[] p, int direction, senseDir sense_dir){
		switch(sense_dir){
		case Here: return p;
		break;
		case Ahead: return adjacent_cell(p, direction);
		break;
		case LeftAhead: return adjacent_cell(p, turn(Left, direction));
		break;
		case RightAhead: return adjacent_cell(p, turn(Right, direction));
		break;
		}
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
}
