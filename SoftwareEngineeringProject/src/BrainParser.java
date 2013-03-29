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
		if (brainInstructions.size() > 1000) {
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
			ArrayList<Instruction> antBrain = new ArrayList<Instruction>();
			//iterate through each instruction, tokenise and 
			//convert to instruction class
			for (String instruction: brainInstructions) {
				tokenise = new StringTokenizer(instruction);
				
			}
			return antBrain;
			
		} else {
			//notify of error
			return null;
		}
	}
	
	public static void main(String[] args) {
		BrainParser bp = new BrainParser("C:/Users/David/Desktop/testfile.brain");
		System.out.println(bp.isValid());
	}

}