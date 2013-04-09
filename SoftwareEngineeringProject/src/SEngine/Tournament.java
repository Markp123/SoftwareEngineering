package SEngine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Tournament {
//	private List<AntBrain> brainsInTournament = new ArrayList<AntBrain>();

	public static void main(String[] args) {
		Tournament t = new Tournament();
	}
	
	public Tournament(){
//		this.uploadBrain(new AntBrain("brain1.txt"));
//		this.uploadBrain(new AntBrain("brain2.txt"));
		//System.out.println(checkIfBrainsInTournamentAreValid());
		World theWorld = new World(150,150);
		new WorldModel(theWorld).printWorld();
		System.out.println(determineWhetherContestWorldValid(theWorld));
	}
	
//	private void uploadBrain(AntBrain brain){
//		brainsInTournament.add(brain);
//	}
	
//	public boolean checkIfBrainsInTournamentAreValid(){
//		//Check whether brain is syntactically well formed by comparing each line against regular expression above.
//		boolean syntacticallyWellFormed = true;
//		int brainCount = 0;
//		while (brainCount < brainsInTournament.size() && syntacticallyWellFormed){
//			List<String> instructions = brainsInTournament.get(brainCount++).getInstructions();
//			int instructionCount = 0;
//			while(syntacticallyWellFormed && instructionCount < instructions.size()){
//				syntacticallyWellFormed = determineWhetherBrainSyntacticallyValid(instructions.get(instructionCount++));
//			}
//		}
//		return syntacticallyWellFormed;
//	}

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
	
	
	private boolean determineWhetherContestWorldValid(World world){
		boolean valid = true;
		valid = (world.getColumns() == 150 
			&& world.getRows() == 150
			&& checkRockyBoarders(world))
			&& checkCorrectNumberOFElements(world);
		return valid;
			
	}
		
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
	
	private boolean checkCorrectNumberOFElements(World world){
		int rockCount = 0, emptyCount = 0;
		boolean foodBlobsValid = true;
		List<Point> rAntHillLocations = new ArrayList<Point>();
		List<Point> bAntHillLocations = new ArrayList<Point>();	
		ArrayList<Cell> knownFoodCells = new ArrayList<Cell>();
		int i = 0;
		while (i < 150){
			int j = 0;
			while (j < 150 && foodBlobsValid){
				Cell currentCell = world.getCell(i, j);
				if (currentCell.getIsRock()){
					rockCount++;
				} else if (currentCell.getIsRAntHill()){	
					rAntHillLocations.add(new Point(j,i));
				} else if (currentCell.getIsBAntHill()){
					bAntHillLocations.add(new Point(j,i));
				} else if (currentCell.getIsFood()){	
					//when food cell found, check whether full blob is present
					if(!knownFoodCells.contains(currentCell)){
						int y = 0;
						while (foodBlobsValid && y < 5){
							int x = 0;
							while (foodBlobsValid && x < 5){
								Cell tempCell = world.getCell(i + y, j + x);
								foodBlobsValid = tempCell.getFoodAmount() == 5 && !knownFoodCells.contains(tempCell);
								knownFoodCells.add(tempCell);
								x++;
							}
							y++;
						}			
					}
				} else {
					emptyCount++;
				}
				j++;
			}	
			i++;
		}
		return rockCount == 610 && checkAnthill(rAntHillLocations) && checkAnthill(bAntHillLocations) && foodBlobsValid;
	}
	
	
	private boolean checkAnthill(List<Point> points){
		try{
			int rowLengthCount = 6;
			int pointsCheckedCount = 0;
			int i = 0;
			
			int negativeXOffset = 0;
			boolean valid = true;
			int xStart = (int) points.get(0).getX();
			int yStart = (int) points.get(0).getY();
			int yOffset = 0;
			
			//check first half of hexagon
			while (rowLengthCount < 11 && valid){
				int thisRowLengthCount = i+rowLengthCount;
				while(i < thisRowLengthCount && valid){		
					valid = points.get(pointsCheckedCount).getX() == xStart+i && points.get(pointsCheckedCount).getY() == yStart+yOffset;
					i++; pointsCheckedCount++;
				}
				negativeXOffset+=(yOffset++ + yStart + 1)%2;
				i = 0 - negativeXOffset;
				rowLengthCount++;
			}
			//check second half of hexagon
			while (rowLengthCount >= 6 && valid){
				int thisRowLengthCount = i+rowLengthCount;
				while(i < thisRowLengthCount && valid){		
					valid = points.get(pointsCheckedCount).getX() == xStart+i && points.get(pointsCheckedCount).getY() == yStart+yOffset;
					i++; pointsCheckedCount++;
				}
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
