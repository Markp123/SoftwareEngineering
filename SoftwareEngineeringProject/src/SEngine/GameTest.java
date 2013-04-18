package SEngine;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class GameTest {
	ArrayList<Instruction> brain1 = new BrainParser("C:/Users/mpp27/cleverbrain1.brain").parseBrain();
	ArrayList<Instruction> brain2 = new BrainParser("C:/Users/mpp27/cleverbrain1.brain").parseBrain();
	@Test
	public void testAntsCreation() {
		World world = new World(150,150);
		new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int antR = 0;
		int antB = 0;
		for(int x = 0; x<world.getRows(); x++){
			for(int y = 0; y<world.getColumns(); y++){
				Cell c = world.getCell(x,y);
				if(c.isAnt()){
					if(c.getAnt().getColour()==Colour.RED){
						antR++;
					}
					else if(c.getAnt().getColour()==Colour.BLACK){
						antB++;
					}
				}
			}
		}
		assertEquals(127,antR);
		assertEquals(127,antB);
	}
	@Test
	public void testDraw(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		assertEquals("draw",game.stats());
	}
	@Test
	public void testRedWins(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int i = 0;
		int y = 0;
		int[] p = new int[2];
		boolean found = false;
		while(i < world.getRows() && !found){
			while(y < world.getColumns() && !found){
				p[0] = i;
				p[1] = y;
				Cell c = world.getCell(p[0],p[1]);
				if(c.getIsRAntHill()){
					found = true;
				}
				y++;
			}
			y = 0;
			i++;
		}
		world.getCell(p[0],p[1]).setFoodAmount(1);
		assertEquals("red",game.stats());
	}
	@Test
	public void testBlackWins(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int i = 0;
		int y = 0;
		int[] p = new int[2];
		boolean found = false;
		while(i < world.getRows() && !found){
			while(y < world.getColumns() && !found){
				p[0] = i;
				p[1] = y;
				Cell c = world.getCell(p[0],p[1]);
				if(c.getIsBAntHill()){
					found = true;
				}
				y++;
			}
			y = 0;
			i++;
		}
		world.getCell(p[0],p[1]).setFoodAmount(1);
		assertEquals("black",game.stats());
	}
	@Test
	public void testFindAnt(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int[] p = game.find_ant(5,Colour.RED);
		assertEquals(5,world.getCell(p[0],p[1]).getAnt().getId());
		assertEquals(Colour.RED,world.getCell(p[0],p[1]).getAnt().getColour());
	}
	@Test
	public void testMartialArts(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int i = 0;
		int y = 0;
		int[] p = new int[2];
		boolean found = false;
		while(i < world.getRows() && !found){
			while(y < world.getColumns() && !found){
				p[0] = i;
				p[1] = y;
				Cell c = world.getCell(p[0],p[1]);
				if(c.getIsRAntHill()){
					found = true;
				}
				y++;
			}
			y = 0;
			i++;
		}
		world.getCell(p[0], p[1]).setAnt(new Ant(Colour.BLACK, 200, brain1));
		game.check_for_surrounded_ants(p);
		assertEquals(0, world.getCell(p[0],p[1]).getFoodAmount());
		p[0]++;
		p[0]++;
		p[1]++;
		p[1]++;
		world.getCell(p[0], p[1]).setAnt(new Ant(Colour.BLACK, 200, brain1));
		game.check_for_surrounded_ants(p);
		assertEquals(3, world.getCell(p[0],p[1]).getFoodAmount());
		world.getCell(p[0], p[1]).setAnt(new Ant(Colour.BLACK, 200, brain1));
		world.getCell(game.adjacent_cell(p, 0)[0],game.adjacent_cell(p, 0)[1]).setAnt(new Ant(Colour.BLACK, 201, brain1));
		game.check_for_surrounded_ants(p);
		assertEquals(6, world.getCell(p[0],p[1]).getFoodAmount());
		
	}
	@Test
	public void testAdjacent_ants(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int i = 0;
		int y = 0;
		int[] p = new int[2];
		boolean found = false;
		while(i < world.getRows() && !found){
			while(y < world.getColumns() && !found){
				p[0] = i;
				p[1] = y;
				Cell c = world.getCell(p[0],p[1]);
				if(c.getIsRAntHill()){
					found = true;
				}
				y++;
			}
			y = 0;
			i++;
		}
		p[0]++;
		p[0]++;
		p[1]++;
		p[1]++;
		world.getCell(p[0], p[1]).setAnt(new Ant(Colour.BLACK, 200, brain1));
		world.getCell(game.adjacent_cell(p, 0)[0],game.adjacent_cell(p, 0)[1]).setAnt(new Ant(Colour.BLACK, 201, brain1));
		assertEquals(1,game.adjacent_ants(p, Colour.RED));
		assertEquals(5,game.adjacent_ants(p, Colour.BLACK));
	}
	@Test
	public void testAdjacent_cell(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int[] p = new int[2];
		int[] ap = new int[2];
		p[0] = 0;
		p[1] = 0;
		ap[0] = 1;
		ap[1] = 0;
		assertEquals(ap[0], game.adjacent_cell(p, 0)[0]);
		assertEquals(ap[1], game.adjacent_cell(p, 0)[1]);
		p[0] = 0;
		p[1] = 0;
		ap[0] = 0;
		ap[1] = 1;
		assertEquals(ap[0], game.adjacent_cell(p, 1)[0]);
		assertEquals(ap[1], game.adjacent_cell(p, 1)[1]);
		p[0] = 1;
		p[1] = 0;
		ap[0] = 0;
		ap[1] = 1;
		assertEquals(ap[0], game.adjacent_cell(p, 2)[0]);
		assertEquals(ap[1], game.adjacent_cell(p, 2)[1]);
		p[0] = 1;
		p[1] = 1;
		ap[0] = 0;
		ap[1] = 1;
		assertEquals(ap[0], game.adjacent_cell(p, 3)[0]);
		assertEquals(ap[1], game.adjacent_cell(p, 3)[1]);
		p[0] = 4;
		p[1] = 2;
		ap[0] = 3;
		ap[1] = 1;
		assertEquals(ap[0], game.adjacent_cell(p, 4)[0]);
		assertEquals(ap[1], game.adjacent_cell(p, 4)[1]);
		p[0] = 3;
		p[1] = 2;
		ap[0] = 3;
		ap[1] = 1;
		assertEquals(ap[0], game.adjacent_cell(p, 5)[0]);
		assertEquals(ap[1], game.adjacent_cell(p, 5)[1]);
	}
	@Test
	public void testTurn(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		assertEquals(4, game.Turn(LeftRight.LEFT, 5));
		assertEquals(3, game.Turn(LeftRight.LEFT, 4));
		assertEquals(2, game.Turn(LeftRight.LEFT, 3));
		assertEquals(1, game.Turn(LeftRight.LEFT, 2));
		assertEquals(0, game.Turn(LeftRight.LEFT, 1));
		assertEquals(5, game.Turn(LeftRight.LEFT, 0));
		assertEquals(0, game.Turn(LeftRight.RIGHT, 5));
		assertEquals(5, game.Turn(LeftRight.RIGHT, 4));
		assertEquals(4, game.Turn(LeftRight.RIGHT, 3));
		assertEquals(3, game.Turn(LeftRight.RIGHT, 2));
		assertEquals(2, game.Turn(LeftRight.RIGHT, 1));
		assertEquals(1, game.Turn(LeftRight.RIGHT, 0));
	}
	@Test
	public void testOtherColour(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		assertEquals(Colour.RED, game.other_color(Colour.BLACK));
		assertEquals(Colour.BLACK, game.other_color(Colour.RED));
	}
	@Test
	public void test_check_any_marker_at(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		world.getCell(0, 0).setBMarker(1);
		world.getCell(1, 1).setRMarker(1);
		int[] p = new int[2];
		p[0] = 0;
		p[1] = 0;
		assertEquals(true, game.check_any_marker_at(p, Colour.BLACK));
		p[0] = 1;
		p[1] = 1;
		assertEquals(true, game.check_any_marker_at(p, Colour.RED));
	}
	@Test
	public void test_marker(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int[] p = new int[2];
		p[0] = 0;
		p[1] = 0;
		game.set_marker_at(p, Colour.BLACK, 1);
		assertEquals(true, game.cell_matches(p, 1, Colour.BLACK));
	}
	@Test//check that it does not allow ants to mark cells already marked by enemy ants
	public void test_stop_marker(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int[] p = new int[2];
		p[0] = 0;
		p[1] = 0;
		game.set_marker_at(p, Colour.BLACK, 1);
		game.set_marker_at(p, Colour.RED, 5);
		assertEquals(false, game.cell_matches(p, 5, Colour.RED));
	}
	@Test
	public void test_random_int(){//quick manual check from running a few times that it is generating the correct numbers and randomly
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		System.out.println("random number generator, variable 2");
		System.out.println(game.randomint(2));
		System.out.println(game.randomint(2));
		System.out.println(game.randomint(2));
		System.out.println(game.randomint(2));
		System.out.println(game.randomint(2));
		System.out.println("random number generator, variable 4");
		System.out.println(game.randomint(4));
		System.out.println(game.randomint(4));
		System.out.println(game.randomint(4));
		System.out.println(game.randomint(4));
		System.out.println(game.randomint(4));
		System.out.println(game.randomint(4));
	}
	@Test
	public void test_sense_friend(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int[] p = new int[2];
		p[0] = 0;
		p[1] = 0;
		game.set_ant_at(p, new Ant(Colour.RED, 1, brain1));
		assertEquals(true, game.cell_matches(p, Condition.FRIEND, Colour.RED));
		assertEquals(false, game.cell_matches(p, Condition.FRIEND, Colour.BLACK));
	}
	@Test
	public void test_sense_foe(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int[] p = new int[2];
		p[0] = 0;
		p[1] = 0;
		game.set_ant_at(p, new Ant(Colour.RED, 1, brain1));
		assertEquals(false, game.cell_matches(p, Condition.FOE, Colour.RED));
		assertEquals(true, game.cell_matches(p, Condition.FOE, Colour.BLACK));
	}
	@Test
	public void test_sense_friend_with_food(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int[] p = new int[2];
		p[0] = 0;
		p[1] = 0;
		Ant a = new Ant(Colour.RED, 1, brain1);
		game.set_ant_at(p, a);
		assertEquals(false, game.cell_matches(p, Condition.FRIENDWITHFOOD, Colour.RED));
		a.setHas_food(true);
		assertEquals(true, game.cell_matches(p, Condition.FRIENDWITHFOOD, Colour.RED));
	}
	@Test
	public void test_sense_foe_with_food(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int[] p = new int[2];
		p[0] = 0;
		p[1] = 0;
		Ant a = new Ant(Colour.RED, 1, brain1);
		game.set_ant_at(p, a);
		assertEquals(false, game.cell_matches(p, Condition.FOEWITHFOOD, Colour.BLACK));
		a.setHas_food(true);
		assertEquals(true, game.cell_matches(p, Condition.FOEWITHFOOD, Colour.BLACK));
	}
	@Test
	public void test_sense_objects(){
		World world = new World(150,150);
		Game game = new Game(world, new BrainParser("C:/Users/mpp27/cleverbrain1.brain"), new BrainParser("C:/Users/mpp27/cleverbrain1.brain"));
		int[] p = new int[2];
		p[0] = 0;
		p[1] = 0;
		//food
		assertEquals(false, game.cell_matches(p, Condition.FOOD, Colour.BLACK));
		assertEquals(false, game.cell_matches(p, Condition.FOOD, Colour.RED));
		world.getCell(p[0], p[1]).setFood(true);
		world.getCell(p[0], p[1]).setFoodAmount(6);
		assertEquals(true, game.cell_matches(p, Condition.FOOD, Colour.BLACK));
		assertEquals(true, game.cell_matches(p, Condition.FOOD, Colour.RED));
		
		//rock
		p[0]=1;
		p[1]=1;
		assertEquals(false, game.cell_matches(p, Condition.ROCK, Colour.BLACK));
		assertEquals(false, game.cell_matches(p, Condition.ROCK, Colour.RED));
		world.getCell(p[0], p[1]).setRock(true);
		assertEquals(true, game.cell_matches(p, Condition.ROCK, Colour.BLACK));
		assertEquals(true, game.cell_matches(p, Condition.ROCK, Colour.RED));

		//foemarker
		p[0]=2;
		p[1]=2;
		assertEquals(false, game.cell_matches(p, Condition.FOEMARKER, Colour.BLACK));
		assertEquals(false, game.cell_matches(p, Condition.FOEMARKER, Colour.RED));
		world.getCell(p[0], p[1]).setBMarker(5);
		assertEquals(false, game.cell_matches(p, Condition.FOEMARKER, Colour.BLACK));
		assertEquals(true, game.cell_matches(p, Condition.FOEMARKER, Colour.RED));

		//home
		p[0]=3;
		p[1]=3;
		assertEquals(false, game.cell_matches(p, Condition.HOME, Colour.BLACK));
		assertEquals(false, game.cell_matches(p, Condition.HOME, Colour.RED));
		world.getCell(p[0], p[1]).setBAntHill(true);
		assertEquals(true, game.cell_matches(p, Condition.HOME, Colour.BLACK));
		assertEquals(false, game.cell_matches(p, Condition.HOME, Colour.RED));
		//foe home
		p[0]=4;
		p[1]=4;
		assertEquals(false, game.cell_matches(p, Condition.FOEHOME, Colour.BLACK));
		assertEquals(false, game.cell_matches(p, Condition.FOEHOME, Colour.RED));
		world.getCell(p[0], p[1]).setBAntHill(true);
		assertEquals(true, game.cell_matches(p, Condition.FOEHOME, Colour.RED));
		assertEquals(false, game.cell_matches(p, Condition.FOEHOME, Colour.BLACK));
	}
	//edited check marker at(restriction), check any markers at(typo), adjacent cells(typo) methods in game
}