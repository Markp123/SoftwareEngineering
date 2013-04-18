package SEngine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Software Engineering Project 2013
 * 
 * Test ant brain parser
 * 
 * @author DCRichards
 *
 */
public class BrainParserTest {
  
	String filename;
	
	/**
	 * set up test
	 * 
	 * @param filename the ant brain file to test
	 */
	public void setup(String filename) {
		this.filename = filename;
	}
	
	/**
	 * Test that a brain returns valid
	 * 
	 */
	@Test
	public void testIsValid() {
		setup("unittestfile.brain");
		BrainParser parser = new BrainParser(filename);
		assertEquals("check that brain returns valid", parser.isValid(), true);
	}
	
	/**
	 * Test that an invalid brain returns invalid
	 */
	@Test
	public void testIsInvalid() {
		setup("unittestfile-invalid.brain");
		BrainParser parser = new BrainParser(filename);
		assertEquals("check that brain returns valid", parser.isValid(), false);
	}
	
	/**
	 * Test that instructions in list match input
	 * 
	 */
	@Test
	public void testParse() {
		setup("unittestfile.brain");
		BrainParser parser = new BrainParser(filename);
		ArrayList<Instruction> brain = parser.parseBrain();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			for (int i = 0; i < brain.size(); i++) {
				if (!brain.get(i).toString().toLowerCase().contains(reader.readLine().toLowerCase())) {
					fail("line " + i + " does not match");
				}
			}
		} catch (Exception e) {
			//error
		}
		
	}
	
	/**
	 * Run tests
	 */
	@Before
	public void test() {
		testIsValid();
		testParse();
		testIsInvalid();
	}

}
