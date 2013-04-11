package SEngine;

import java.io.*;
import java.util.Random;

public class World {
	private Cell[][] world;
	private Random random;
	private int rows, columns, randRow, randCol, made;
	private boolean randRowCorrect, randColCorrect, spaceClear, foodSpaceClear;
	private int generatedWorldNo;
	private BufferedWriter worldWriter;

	public World(int row, int column)
	{
		generatedWorldNo = 0;
		random = new Random();
		world = new Cell[row][column];
		rows = world.length;
		columns = world.length;
		randRow = random.nextInt(world.length);
		randCol = random.nextInt(world.length);
		randRowCorrect = false;
		randColCorrect = false;
		spaceClear = false;
		for (int i = 0; i < world.length; i++)
		{
			for(int j = 0; j < world.length; j++)
			{
				Cell cell = new Cell();
				world[i][j] = cell;
			}
		}
		/**
		 * bens code for setting all empty cells to empty, add into world
		 */
		for (int i = 0; i < world.length; i++){
			for(int j = 0; j < world.length; j++){
				Cell cell = world[i][j];
				cell.setEmpty(!(cell.getIsRock() || cell.getIsRAntHill() || cell.getIsBAntHill() || cell.getFoodAmount() > 0));
			}
		}
		resetVariables();
		checkRand();
		checkSpaceClear(0,0,0,0,15);
		redAntHill(0,0,0,13);
		spaceClear = false;
		resetVariables();
		checkRand();
		checkSpaceClear(0,0,0,0,15);
		blackAntHill(0,0,0,13);
		spaceClear = false;
		while(made<11)
		{
			findClearFoodSpace(0, 0, 0, 7);
			food(0, 0, 5);
			foodSpaceClear = false;
			made++;
		}
		rocks();
	}

	/**
	 * Construct a new world from a file
	 *
	 * @param filename the location of the file
	 */
	public World(String filename) {
		WorldReader wr = new WorldReader(filename);
		String[][] worldArray = wr.read();
		rows = wr.getRows();
		columns = wr.getColumns();
		world = new Cell[rows][columns];
		Cell curCell;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				//create new cell
				curCell = new Cell();
				if (worldArray[i][j].equals("#")) {
					curCell.setRock(true);
					curCell.setImage("#");
				} else if (worldArray[i][j].equals(".")) {
					curCell.setEmpty(true);
					curCell.setImage(".");
				} else if (isNumeric(worldArray[i][j])) {
					curCell.setFood(true);
					curCell.setFoodAmount(Integer.parseInt(worldArray[i][j]));
					curCell.setImage(worldArray[i][j]);
				} else if (worldArray[i][j].equals("+")) {
					curCell.setRAntHill(true);
					curCell.setImage("+");
				} else if (worldArray[i][j].equals("-")) {
					curCell.setBAntHill(true);
					curCell.setImage("-");
				}
				//store cell in world
				world[i][j] = curCell;
			}
		}
	}

	/**
	 * Check that a string is a valid integer
	 *
	 * @param s the string to check
	 * @return true if an integer
	 */
	private boolean isNumeric(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException exc) {
			return false;
		}
	}

	/**
	 * write the current world to a .world file
	 *
	 * @return true if successful
	 */
	public boolean writeWorld() {
		try {
			worldWriter = new BufferedWriter(new FileWriter("generatedWorld" + generatedWorldNo + ".world"));
			worldWriter.write(Integer.toString(rows));
			worldWriter.newLine();
			worldWriter.write(Integer.toString(columns));
			worldWriter.newLine();
			for (int i = 0; i < rows; i++) {
				String currentLine = "";
				if (i%2 != 0) {
					currentLine += " ";
				}
				for (int j = 0; j < columns; j++) {
					if (j != columns - 1) {
						currentLine += world[i][j].toString() + " ";
					} else {
						currentLine += world[i][j].toString();
					}
				}
				worldWriter.write(currentLine);
				worldWriter.newLine();
			}
			worldWriter.close();
			return true;
		} catch (IOException ioe) {
			return false;
		}
	}

	public void rockCheck()
	{
		if (randRow > 3 && randRow < world.length -3)
		{
			randRowCorrect = true;
		}
		else
		{
			randRowCorrect = false;
		}
		if (randCol > 3 && randCol < world.length-3)
		{
			randColCorrect = true;
		}
		else
		{
			randColCorrect = false;
		}
		while (!randRowCorrect || !randColCorrect)
		{
			if (!randRowCorrect)
			{
				if (randRow > 3 && randRow < world.length -3)
				{
					randRowCorrect = true;
				}
				else
				{
					randRowCorrect = false;
					randRow = random.nextInt(world.length);
				}
			}
			else if (!randColCorrect)
			{
				if (randCol > 3 && randCol < world.length-3)
				{
					randColCorrect = true;
				}
				else
				{
					randColCorrect = false;
					randCol = random.nextInt(world.length);
				}
			}
		}
	}

	public void checkRand()
	{
		if (randRow > 8 && randRow < world.length -14)
		{
			randRowCorrect = true;
		}
		else
		{
			randRowCorrect = false;
		}
		if (randCol > 7 && randCol < world.length-14)
		{
			randColCorrect = true;
		}
		else
		{
			randColCorrect = false;
		}
		while (!randRowCorrect || !randColCorrect)
		{
			if (!randRowCorrect)
			{
				if (randRow > 8 && randRow < world.length -14)
				{
					randRowCorrect = true;
				}
				else
				{
					randRowCorrect = false;
					randRow = random.nextInt(world.length);
				}
			}
			else if (!randColCorrect)
			{
				if (randCol > 7 && randCol < world.length-14)
				{
					randColCorrect = true;
				}
				else
				{
					randColCorrect = false;
					randCol = random.nextInt(world.length);
				}
			}
		}
	}

	public void resetVariables()
	{
		randRow = random.nextInt(world.length);
		randCol = random.nextInt(world.length);
		randRowCorrect = false;
		randColCorrect = false;
		spaceClear = false;
		checkRand();
	}

	public void resetFood()
	{
		randRow = random.nextInt(world.length);
		randCol = random.nextInt(world.length);
		randRowCorrect = false;
		randColCorrect = false;
		foodSpaceClear = false;
		checkRand();
	}

	public Cell getCell(int row, int column)
	{
		return world[row][column];
	}

	public int getRows()
	{
		return rows;
	}

	public int getColumns()
	{
		return columns;
	}

	public void rocks()
	{
		for (int i = 0; i < world.length; i++)
		{
			world[0][i].setRock(true);
			world[i][0].setRock(true);
			world[i][world.length-1].setRock(true);
			world[world.length-1][i].setRock(true);
		}
		int made = 0;
		int n =0;
		boolean rockSpaceClear = false;
		while (made < 14)
		{
			randRow = random.nextInt(world.length);
			randCol = random.nextInt(world.length);
			rockCheck();
			for (int i = randRow - 1; i <= randRow + 1; i++)
			{
				for (int j = randCol - 1; j <= randCol + 1; j++)
				{
					if ((world[i][j].getIsRock() == false && (i == 0 && j == 0) == false))
					{
						if (!(world[i][j].getIsAntHill()) && !(world[i][j].getIsFood()))
						{
							{
								n++;
							}
						}
					}
					if (n == 9)
					{
						rockSpaceClear = true;
					}

				}
			}
			if (rockSpaceClear)
			{
				world[randRow][randCol].setRock(true);
				world[randRow][randCol].setEmpty(false);
				made++;
				rockSpaceClear = false;
			}
			n = 0;
		}
	}

	public void checkSpaceClear(int n, int temp, int count, int recursive, int length)
	{
		while (!spaceClear)
		{
			if (recursive <8)
			{
				for(int i = 0; i < length; i++)
				{
					if (getCell(randRow + count,((i+randCol)+temp)-1).getIsEmpty())
					{
						n++;
					}
				}
				for(int j = 0; j < length; j++)
				{
					if (getCell(randRow - count,((j+randCol)+temp)-1).getIsEmpty())
					{
						n++;
					}
				}
				if ((randRow + count) % 2 != 0)
				{
					temp++;
				}
				count++;
				length--;
				recursive++;
				checkSpaceClear(n, temp, count, recursive, length);
			}
			if (n == 184)
			{
				spaceClear = true;
			}
			else if (recursive == 8 && spaceClear == false)
			{
				resetVariables();
				checkRand();
				checkSpaceClear(0,0,0,0,15);
			}
		}
	}


	public void redAntHill(int temp, int count, int recursive, int length)
	{
		while (recursive < 7)
		{
			if (spaceClear)
			{
				for(int i = 0; i < length; i++)
				{
					{
						world[randRow+count][(i+randCol)+temp].setRAntHill(true);
						world[randRow+count][(i+randCol)+temp].setIsAntHill(true);
						world[randRow+count][(i+randCol)+temp].setEmpty(false);
					}
				}
				for(int j = 0; j < length; j++)
				{
					{
						world[randRow-count][(j+randCol)+temp].setRAntHill(true);
						world[randRow-count][(j+randCol)+temp].setIsAntHill(true);
						world[randRow-count][(j+randCol)+temp].setEmpty(false);
					}
				}
				if ((randRow + count) % 2 != 0)
				{
					temp++;
				}
				count++;
				length--;
			}
			recursive++;
			redAntHill(temp, count, recursive, length);
		}
	}

	public void blackAntHill(int temp, int count, int recursive, int length)
	{
		while (recursive < 7)
		{
			if (spaceClear)
			{
				for(int i = 0; i < length; i++)
				{
					{
						world[randRow+count][(i+randCol)+temp].setBAntHill(true);
						world[randRow+count][(i+randCol)+temp].setIsAntHill(true);
						world[randRow+count][(i+randCol)+temp].setEmpty(false);

					}
				}
				for(int j = 0; j < length; j++)
				{
					{
						world[randRow-count][(j+randCol)+temp].setBAntHill(true);
						world[randRow-count][(j+randCol)+temp].setIsAntHill(true);
						world[randRow-count][(j+randCol)+temp].setEmpty(false);
					}
				}
				if ((randRow + count) % 2 != 0)
				{
					temp++;
				}
				count++;
				length--;
			}
			recursive++;
			blackAntHill(temp, count, recursive, length);
		}
	}

	public void findClearFoodSpace(int n, int count, int recursive, int length)
	{
		while (!foodSpaceClear)
		{
			if (recursive < 4)
			{
				for(int i = 0; i < length; i++)
				{
					if (getCell(randRow+count,(i+randCol)-1).getIsEmpty())
					{
						n++;
					}
				}
				for(int j = 0; j < length; j++)
				{
					if (getCell(randRow-count,(j+randCol)-1).getIsEmpty())
					{
						n++;
					}
				}
				count++;
				recursive++;
				findClearFoodSpace(n, count, recursive, length);
			}
			if (n == 56)
			{
				foodSpaceClear = true;
			}
			else if (recursive == 4 && foodSpaceClear == false)
			{
				resetFood();
				checkRand();
				findClearFoodSpace(0,0,0,7);

			}
		}
	}

	public static void main(String[] args) {
		//World wd = new World("C:/Users/David/Desktop/sample0.world");
		World wd = new World(150,150);
		wd.writeWorld();
	}

	public void food(int count, int recursive, int length)
	{
		while(recursive < 3)
		{
			if(foodSpaceClear == true)
			{
				for(int i = 0; i < length; i++)
				{
					{
						world[randRow+count][i+randCol].setFood(true);
						world[randRow+count][i+randCol].setFoodAmount(5);
						world[randRow+count][i+randCol].setEmpty(false);
					}
				}
				for(int j = 0; j < length; j++)
				{
					{
						world[randRow-count][j+randCol].setFood(true);
						world[randRow-count][j+randCol].setFoodAmount(5);
						world[randRow-count][j+randCol].setEmpty(false);
					}
				}
				recursive++;
				count++;
				food(count, recursive, length);
			}
		}
	}


}