import java.util.List;
import java.util.StringTokenizer;

/**
 * Software Engineering Project 2013
 * 
 * A class to parse the ant brain instructions 
 * 
 * @author DCRichards
 *
 */
public class BrainParser {
	
	/*
	 * Instructions:
	 * 
	 * Sense sensedir st1 st2 cond
	 * Mark i st
	 * Unmark i st
	 * PickUp st1 st2
	 * Drop st
	 * Turn lr st
	 * Move st1 st2
	 * Flip p st1 st2
	 */
	
	List<String> brainInstructions;
	
	/**
	 * Constructor for BrainParser
	 * 
	 * @param filename the filename of the ant brain to be read
	 */
	public BrainParser(String filename) {
		BrainReader reader = new BrainReader(filename);
		brainInstructions = reader.read();
	}
	
	/**
	 * Tokenise a single instruction
	 * 
	 * @param instruction the number of the instruction of tokenise
	 * @return
	 */
	public StringTokenizer tokenize(int instruction) {
		return new StringTokenizer(brainInstructions.get(instruction));
	}
	
	public static void main(String[] args) {
		BrainParser bp = new BrainParser("C:/Users/David/Desktop/testfile.brain");
		bp.tokenize(0);
	}

}
