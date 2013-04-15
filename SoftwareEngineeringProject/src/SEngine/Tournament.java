package SEngine;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * During the tournament, each pair of submissions is pitted against each other twice on each of the contest worlds
 * ---once with the first submission playing red and the second black, and once with the first playing black and the second red.
 *  A submission gains 2 points for each game it wins, and 1 point for each draw. 
 *  The submission with the most points wins the tournament. 
 *  The number of the worlds used during the tournament is unspecified, but will be large enough for determining a clear winner. 
 *  If there is nevertheless no clear winner, the tournament is repeated with a certain number of finalist submissions.
 */
public class Tournament {
	private List<BrainParser> brainsInTournament = new ArrayList<BrainParser>();
	private HashMap<BrainParser,Integer> brainScores = new HashMap<BrainParser,Integer>();
	private List<World> worldsInTournament = new ArrayList<World>();
	private int numberOfWorldsInTournament = 5;
	BufferedWriter testWriter;
	
	public static void main(String[] args) {
		Tournament t = new Tournament();
	}
	

	/**
	 * Constructor for the tournament class.
	 */
	public Tournament(){
		//String s = "TestWorlds/test42.world";
		//System.out.println(determineWhetherContestWorldValid(new World(s)));
		//this.test();
		setupTournament();
		System.out.println(holdTournament());
	}
	
	/**
	 * Test syntax checker on purpose built worlds and write results to an excel file.
	 */
	private void test(){
		try {
			testWriter = new BufferedWriter(new FileWriter("Testing2.xls"));
			testWriter.write("\tWorld 150 by 150\tBoarders rocky\tEdge free of elements\tRock Count\tFood blob count\tRed anthill valid\tBlack anthill valid\tWorld valid overall\n");
			
			for (int i = 1; i <= 55; i++){
				String s = "TestWorlds/test" + i + ".world";
				testWriter.write("test" + i + "\t");
				testWriter.write(determineWhetherContestWorldValid(new World(s)) + "\n");
			}
			testWriter.close();
		} catch (IOException e) {
			System.out.println("write error");
		}
	}
	

	/**
	 * Populate lists containing the brains in tournament and worlds in tournament. 
	 */
	private void setupTournament(){
		//populate brains
		this.uploadBrain(new BrainParser("brain1.txt"));
		this.uploadBrain(new BrainParser("brain2.txt"));
		this.uploadBrain(new BrainParser("horseshoe-1.txt"));
		this.uploadBrain(new BrainParser("snakebrain.txt"));
		
		
		if(brainsInTournamentAreValid()){
			//populate worlds
			int count = 0;
			while (count < numberOfWorldsInTournament){
				World theWorld = new World(150,150);
				if (determineWhetherContestWorldValid(theWorld)){
					worldsInTournament.add(theWorld);
					count++;
				}
			}
		} else {
			System.out.println("There are invalid brains in the tournament");
		}
	}
		
	/**
	 * Once the tournament has been setup, this class pits each brain against each other brain 
	 * twice (each brain plays as both red and black) per world. The winner of the tournament
	 * is the brain which receives the most points. Two points are allocated for a win, one for
	 * a draw.
	 * 
	 * @return winning brain
	 */
	private BrainParser holdTournament(){
		for (World theWorld : worldsInTournament){
			for (int i = 0; i < brainsInTournament.size(); i++){
				BrainParser b1 = brainsInTournament.get(i);

				for (int j = i; j < brainsInTournament.size(); j++){
					BrainParser b2 = brainsInTournament.get(j);
					if (b1 != b2){ 

						Game game1 = new Game(theWorld, b1,b2);
						String winner1 = game1.stats();
						if (winner1.equals("red")){
							brainScores.put(b1,brainScores.get(b1) + 2);
						} else if (winner1.equals("black")){
							brainScores.put(b2,brainScores.get(b2) + 2);
						} else {
							brainScores.put(b1,brainScores.get(b1) + 1);
							brainScores.put(b2,brainScores.get(b2) + 1);
						}

						/**
						 * Swap colours
						 */
						Game game2 = new Game(theWorld, b2,b1);
						String winner2 = game2.stats();
						if (winner2.equals("red")){
							brainScores.put(b2,brainScores.get(b1) + 2);
						} else if (winner2.equals("black")){
							brainScores.put(b1,brainScores.get(b2) + 2);
						} else {
							brainScores.put(b1,brainScores.get(b1) + 1);
							brainScores.put(b2,brainScores.get(b2) + 1);
						}
					}
				}
			}
		}
		return determineWinner();
	}
	
	/**
	 * Calculate which brain received the most points during the tournament
	 * 
	 * @return winning brain
	 */
	public BrainParser determineWinner(){
		BrainParser winner = null;
		int winningScore = 0;
		for (BrainParser brain : brainScores.keySet()){
			if(brainScores.get(brain) > winningScore){
				winner = brain;
				winningScore = brainScores.get(brain);
			}
		}
		return winner;
	}
		
	/**
	 * code which allows multiple brains to be uploaded to a tournament, tournament then checks
	 * whether brains are syntactically valid. Commented out cause this has been done in a 
	 * different section of program. Left code in just in case its needed in future.
	 */
	private void uploadBrain(BrainParser brain){
		brainsInTournament.add(brain);
		brainScores.put(brain,0);
	}
	
	public boolean brainsInTournamentAreValid(){
		//Check whether brain is syntactically well formed by comparing each line against regular expression above.
		boolean syntacticallyWellFormed = true;
		int brainCount = 0;
		while (brainCount < brainsInTournament.size() && syntacticallyWellFormed){
			syntacticallyWellFormed = brainsInTournament.get(brainCount++).isValid();
		}
		return syntacticallyWellFormed;
	}

//	private boolean determineWhetherBrainSyntacticallyValid(String brainInstruction){
//		//Setup Regular Expression String.
//		String lr = "(Left|Right)";
//		String st = "[0-9]{1,4}";
//		String i = "[0-5]";
//		String p = "[1-9][0-9]*";
//		String sensedir = "(Here|Ahead|LeftAhead|RightAhead)";
//		String cond = "(Friend|Foe|FriendWithFood|FoeWithFood|Food|Rock|Marker " + i + "|FoeMarker|Home|FoeHome)";
//		String instructionSyntax = "(Sense (" + sensedir + " " + st + " " + st + " " + cond +
//				")|Mark (" + i + " " + st + 
//				")|Unmark (" + i + " " + st +
//				")|PickUp (" + st + " " + st + 
//				")|Drop (" + st +
//				")|Turn (" + lr + " " + st +
//				")|Move (" + st + " " + st + 
//				")|Flip (" + p + " " + st + " " + st + 
//				"))( *;.*)?";
//		return brainInstruction.matches(instructionSyntax);
//	}
	
	/**
	 * Given a world, decide whether it meets the requirements to make it a tournament world:
	 * 
	 *      -The dimensions of the world are always 150 × 150 cells. 
	 *      
     *		-The cells on the perimeter are always rocky.
     *
     *		-Every world contains exactly the same elements, of particular shapes: 2 anthills, 14 rocks,
     *		  and 11 blobs of food. The anthills, in particular, are hexagons with sides of length 7. 
     *		  Also, a food blob is always a 5-by-5 rectangle, with each cell containing 5 food particles.
     *
     *		-The positions and orientations of the elements are chosen randomly, subject to the constraint 
     *		 that there is always at least one empty cell between non-food elements. Also, no elements overlap. 
     *		 (The anthill elements are 6-ways-symmetric, so their orientation actually does not matter. 
     *		 All ants are initially facing in direction 0.)
	 * 
	 * @param world
	 * @return whether the world is valid
	 */
	private boolean determineWhetherContestWorldValid(World world){
		boolean valid = true;
		
		/**
		 * For printing test results to an excel file
		 */
//		try {
//			testWriter.write((world.getColumns() == 150 && world.getRows() == 150) + "\t");
//			if((world.getColumns() == 150 && world.getRows() == 150)){
//				testWriter.write(checkRockyBoarders(world) + "\t");
//				testWriter.write(checkNoElementsOnEdge(world) + "\t");
//			}
//		} catch (IOException e) {
//		}
		valid = (world.getColumns() == 150 
			&& world.getRows() == 150
			&& checkRockyBoarders(world))
			&& checkNoElementsOnEdge(world)
			&& checkElementsValid(world);
		return valid;
			
	}
	
	/**
	 * Check whether all the elements on the boarder of a world are rocky
	 * 
	 * @param world
	 * @return  whether boarder is rocky
	 */
	private boolean checkRockyBoarders(World world){
		int count = 0;
		boolean rockyBoarder = true;
		while (count < 150 && rockyBoarder ){
			rockyBoarder = world.getCell(0, count).getIsRock() 
					&& world.getCell(149, count).getIsRock() 
					&& world.getCell(count, 0).getIsRock() 
					&& world.getCell(count, 149).getIsRock();
			count++;
		}
		
		return rockyBoarder;
	}
	
	/**
	 * Check that there are no elements right against the edge of the world.
	 * 
	 * @param world
	 * @return whether the edge is free 
	 */
	private boolean checkNoElementsOnEdge(World world){
		int count = 1;
		boolean edgeFreeOfElements = true;
		
		while (count < 146 && edgeFreeOfElements ){
			edgeFreeOfElements = world.getCell(1, count).getIsEmpty() 
					&& world.getCell(148, count).getIsEmpty() 
					&& world.getCell(count, 1).getIsEmpty() 
					&& world.getCell(count, 148).getIsEmpty();
			count++;
		}
		
		return edgeFreeOfElements;
	}
	
	/**
	 * Checks that the world contains the correct number of elements. Also  calls methods to check
	 * whether elements taking up multiple cells (for example ant hills and food blobs) are the correct
	 * shape, do not overlap and have a free cell in between.
	 * 
	 * @param world
	 * @return Whether the elements in world are valid.
	 */
	private boolean checkElementsValid(World world){
		int rockCount = 0, foodBlobCount = 0;
		boolean worldValid = true;
		List<Point> rAntHillLocations = new ArrayList<Point>();
		List<Point> bAntHillLocations = new ArrayList<Point>();	
		ArrayList<Cell> knownFoodCells = new ArrayList<Cell>();
		int i = 0;
		while (i < 150){
			int j = 0;
			while (j < 150 && worldValid){
				Cell currentCell = world.getCell(i, j);
				if (currentCell.getIsRock()){
					int emptyCount = 0;
					//When a rock has been found, checks to make sure its not a boarder rock.
					if (i!= 0 && j != 0 && i!= world.getRows()-1 && j != world.getColumns()-1){
						//if not then  checks whether the elements around the rock are empty
						int rockOffset = (i+1)%2;
						for (int y = 0-rockOffset; y <= 1-rockOffset; y++){
							if (world.getCell(i-1, j+y).getIsEmpty() && world.getCell(i+1, j+y).getIsEmpty()){
								emptyCount+=2;
							}
						}
						for (int y = -1; y <= 1; y++){
							if (world.getCell(i, j+y).getIsEmpty()){
								emptyCount++;
							}
						}
						rockCount++;
						worldValid = emptyCount == 6;
					}		
				} else if (currentCell.getIsRAntHill()){	
					rAntHillLocations.add(new Point(j,i));
				} else if (currentCell.getIsBAntHill()){
					bAntHillLocations.add(new Point(j,i));
				} else if (currentCell.getIsFood()){	
					//when food cell found, check whether full blob is present
					if(!knownFoodCells.contains(currentCell)){
						int count = 0;
						//check that elements above and below food blob are empty cells
						while (count < 6 && worldValid){
							worldValid = world.getCell(i-1, j+count-((i-1)%2)).getIsEmpty() && world.getCell(i+5, j+count-((i+5)%2)).getIsEmpty();
							count++;
						}
						//check that actual food particles are present
						int y = 0;
						while (worldValid && y < 5){
							int x = 0;
							worldValid = world.getCell(i + y, j + x - 1).getIsEmpty();
							while (worldValid && x < 5){
								Cell tempCell = world.getCell(i + y, j + x);
								worldValid = tempCell.getFoodAmount() == 5 && !knownFoodCells.contains(tempCell);
								knownFoodCells.add(tempCell);
								x++;
							}
							worldValid = world.getCell(i + y, j + x).getIsEmpty();
							y++;
						}
						foodBlobCount++;
					}
				}
				
				//make sure that no cell can be more than one type of element
				if (worldValid){
					worldValid = !(currentCell.getIsRock() && currentCell.getIsBAntHill() && currentCell.getIsRAntHill() && currentCell.getFoodAmount() > 0);
				}
				j++;
			}	
			i++;
		}
		
		/**
		 * Write variable values to test file.
		 */
//		try {
//			testWriter.write(rockCount + "\t");
//			testWriter.write(foodBlobCount + "\t");
//			testWriter.write(checkAnthill(rAntHillLocations,world) + "\t");
//			testWriter.write(checkAnthill(bAntHillLocations,world) + "\t");
//		} catch (IOException e) {
//		}

		return rockCount == 14 && worldValid && checkAnthill(rAntHillLocations,world) && checkAnthill(bAntHillLocations,world) && foodBlobCount == 11;
	}
	
	/**
	 * Given an arrayList of the coordinates in an ant hill, check whether the ant hill is the correct size and shape,
	 * also ensure that there is no elements next to or overlapping the ant hill.
	 * 
	 * @param points
	 * @param world
	 * @return Whether the anthill is valid
	 */
	private boolean checkAnthill(List<Point> points, World world){
		try{
			int rowLengthCount = 7;
			int pointsCheckedCount = 0;
			int i = 0;
			
			int negativeXOffset = 0;
			boolean valid = points.size() == 127;;
			int xStart = (int) points.get(0).getX();
			int yStart = (int) points.get(0).getY();
			int yOffset = 0;
			
			
			//check whether rows above and below anthill are free
			int count = 0;
			while (count < 8 && valid){
				valid = world.getCell( yStart - 1, xStart -(yStart-1)%2 + count).getIsEmpty() && world.getCell(yStart + 13, xStart -(yStart+13)%2 + count).getIsEmpty();
				count++;
			}
			
			//check first half of hexagon
			while (rowLengthCount < 13 && valid){
				int thisRowLengthCount = i+rowLengthCount;
				valid = world.getCell(yStart+yOffset, xStart+i-1).getIsEmpty();
				while(i < thisRowLengthCount && valid){		
					valid = points.get(pointsCheckedCount).getX() == xStart+i && points.get(pointsCheckedCount).getY() == yStart+yOffset;
					i++; pointsCheckedCount++;
				}
				valid = world.getCell(yStart+yOffset, xStart+i).getIsEmpty();
				negativeXOffset+=(yOffset++ + yStart + 1)%2;
				i = 0 - negativeXOffset;
				rowLengthCount++;
			}
			//check second half of hexagon
			while (rowLengthCount >= 7 && valid){
				int thisRowLengthCount = i+rowLengthCount;
				valid = world.getCell(yStart+yOffset, xStart+i-1).getIsEmpty();
				while(i < thisRowLengthCount && valid){		
					valid = points.get(pointsCheckedCount).getX() == xStart+i && points.get(pointsCheckedCount).getY() == yStart+yOffset;
					i++; pointsCheckedCount++;
				}
				valid = world.getCell(yStart+yOffset, xStart+i).getIsEmpty();
				negativeXOffset-=(yOffset++ + yStart)%2;
				i = 0 - negativeXOffset;
				rowLengthCount--;
			}
			return valid;
		}catch (IndexOutOfBoundsException e){
			return false;
		}
	}
}
