package SEngine;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Software Engineering Project 2013
 * 
 * A class to parse the ant brain instructions 
 * 
 * @author DCRichards
 */
public class BrainParser {
	
	private ArrayList<String> brainInstructions;
	private ArrayList<String> syntax;
	
	/**
	 * Constructor for BrainParser
	 * 
	 * @param filename the filename of the ant brain to be read
	 */
	public BrainParser(String filename) {
		BrainReader reader = new BrainReader(filename);
		brainInstructions = reader.read();
		syntax = new ArrayList<String>();
		//specify syntax of instructions as regular expressions
		String dir = "(Here|Ahead|LeftAhead|RightAhead)";
		String leftright = "(Left|Right)";
		String state = "\\d{1,3}"; //ensures state <= 100
		String i = "[0-5]";		   //ensures marker 0-5
		String p = "\\d+";			
		String cond = "(Friend|Foe|FriendWithFood|FoeWithFood|Food|Rock|Marker " + i + "|FoeMarker|Home|FoeHome)";
		String senseIns = "Sense " + dir + " " + state + " " + state + " " + cond;
 		String markIns = "Mark " + i + " " + state;
		String unmarkIns = "Unmark " + i + " " + state;
		String pickupIns = "PickUp " + state + " " + state;
		String dropIns = "Drop " + state;
		String turnIns = "Turn " + leftright + " " + state;
		String moveIns = "Move " + state + " " + state;
		String flipIns = "Flip " + p + " " + state + " " + state;
		//add expressions to syntax list
		syntax.add(senseIns);
		syntax.add(markIns);
		syntax.add(unmarkIns);
		syntax.add(pickupIns);
		syntax.add(dropIns);
		syntax.add(turnIns);
		syntax.add(moveIns);
		syntax.add(flipIns);
	}
	
	/**
	 * Check that the brain is syntactically valid
	 * 
	 * @return true if the given brain is valid
	 */
	public boolean isValid() {
		//check for valid number of states
		if (brainInstructions.size() > 10000) {
			return false;
		}
		//check that instructions use correct syntax
		int i = 0;
		int j = 0;
		boolean valid = true;
		boolean match = false;
		//iterate through each instruction and check for a match in 
		//the list of valid instruction formats
		while (valid && i < brainInstructions.size()) {
			match = false;
			while (!match && j < syntax.size()) {
				match = brainInstructions.get(i).matches(syntax.get(j));
				j++;
			}
			valid = match;
			j = 0;
			i++;
		}
		return valid;
	}
	
	/**
	 * Parse the ant brain into a set of instructions
	 * 
	 * @return a list of ant brain instructions
	 */
	public ArrayList<Instruction> parseBrain() {
		if (isValid()) {
			StringTokenizer tokenise;
			String currentToken;
			Instruction nextInstruction;
			ArrayList<Instruction> antBrain = new ArrayList<Instruction>();
			//tokenise each instruction and create instruction object
			for (String instruction: brainInstructions) {
				//tokenise current instruction
				tokenise = new StringTokenizer(instruction);
				//get instruction type
				currentToken = tokenise.nextToken();
				
				if (currentToken.equalsIgnoreCase("sense")) {
					Instruction sense;
					String direct = tokenise.nextToken();
					Direction dir = null;
					//get direction enum
					for (Direction d: Direction.values()) {
						if (d.toString().equalsIgnoreCase(direct)) {
							dir = d;
						}
					}
					//get states
					int st1 = Integer.parseInt(tokenise.nextToken());
					int st2 = Integer.parseInt(tokenise.nextToken());
					//get condition enum
					String cond = tokenise.nextToken();
					Condition con = null;
					for (Condition cons: Condition.values()) {
						if (cons.toString().equalsIgnoreCase(cond)) {
							con = cons;
						}
					}
					//if marker then get marker number
					if (con.equals(Condition.MARKER)) {
						int marker = Integer.parseInt(tokenise.nextToken());
						sense = new Sense(dir, st1, st2, con, marker);
					} else {
						sense = new Sense(dir, st1, st2, con);
					}
					nextInstruction = sense;
					antBrain.add(nextInstruction);
					
				} else if (currentToken.equalsIgnoreCase("mark")) {
					int marker = Integer.parseInt(tokenise.nextToken());
					int st = Integer.parseInt(tokenise.nextToken());
					nextInstruction = new Mark(marker, st);
					antBrain.add(nextInstruction);
					
				} else if (currentToken.equalsIgnoreCase("unmark")) {
					int marker = Integer.parseInt(tokenise.nextToken());
					int st = Integer.parseInt(tokenise.nextToken());
					nextInstruction = new Unmark(marker, st);
					antBrain.add(nextInstruction);
					
				} else if (currentToken.equalsIgnoreCase("pickup")) {
					int st1 = Integer.parseInt(tokenise.nextToken());
					int st2 = Integer.parseInt(tokenise.nextToken());
					nextInstruction = new PickUp(st1, st2);
					antBrain.add(nextInstruction);
					
				} else if (currentToken.equalsIgnoreCase("drop")) {
					int st = Integer.parseInt(tokenise.nextToken());
					nextInstruction = new Drop(st);
					antBrain.add(nextInstruction);
					
				} else if (currentToken.equalsIgnoreCase("turn")) {
					String dir = tokenise.nextToken();
					LeftRight lr = null;
					if (dir.equalsIgnoreCase(LeftRight.LEFT.toString())) {
						lr = LeftRight.LEFT;
					} else if (dir.equalsIgnoreCase(LeftRight.RIGHT.toString())){
						lr = LeftRight.RIGHT;
					}
					int st = Integer.parseInt(tokenise.nextToken());
					nextInstruction = new Turn(lr, st);
					antBrain.add(nextInstruction);
					
				} else if (currentToken.equalsIgnoreCase("move")) {
					int st1 = Integer.parseInt(tokenise.nextToken());
					int st2 = Integer.parseInt(tokenise.nextToken());
					nextInstruction = new Move(st1, st2);
					antBrain.add(nextInstruction);
					
				} else if (currentToken.equalsIgnoreCase("flip")) {
					int p = Integer.parseInt(tokenise.nextToken());
					int st1 = Integer.parseInt(tokenise.nextToken());
					int st2 = Integer.parseInt(tokenise.nextToken());
					nextInstruction = new Flip(p, st1, st2);
					antBrain.add(nextInstruction);
				}
					
			}
			return antBrain;
			
		} else {
			//notify of error
			return null;
		}
	}
}