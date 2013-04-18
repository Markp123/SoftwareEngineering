package SEngine;

import static org.junit.Assert.*;
import org.junit.*;
import SEngine.BrainReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Software Engineering Project 2013
 * 
 * Test ant brain reader
 * 
 * @author DCRichards
 * @version 1.0
 */
public class BrainReaderTest {
  
	
	/**
	 * Constructor for BrainReaderTest
	 */
	public BrainReaderTest() {}
	
	/**
	 * Check that file reader reads correctly and returns
	 */
	@Test
	public void testRead() {
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter("unittestfile.brain"));
			br.write("sense ahead 1 3 food");
			br.newLine();
			br.write("mark 1 8");
			br.newLine();
			br.write("unmark 4 10");
			br.newLine();
			br.write("turn left 2");
			br.newLine();
			br.write("pickup 8 0");
			br.newLine();
			br.write("drop 8");
			br.newLine();
			br.write("move 10 11");
			br.newLine();
			br.write("flip 1 10 10");
			br.newLine();
			br.close();
		} catch (IOException iex) {
			//error
		}
		BrainReader reader = new BrainReader("unittestfile.brain");
		assertEquals("check instructions read",8,reader.read().size());
	}
	
	/**
	 * Run tests
	 */
	@Test
	public void test() {
		testRead();
	}

}
