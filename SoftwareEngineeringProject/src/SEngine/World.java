package SEngine;

import java.util.Random;

public class World {
	private Cell[][] world;
	private Random random;
	private int rows, columns, randRow, randCol,skip, count, temp, recursive;
	private int length = 6;
	private boolean randRowCorrect;
	private boolean randColCorrect;
	private boolean spaceClear;

	public World(int row, int column)
	{
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
		checkRand();
		redAntHill();
		resetVariables();
		checkSpaceClear(0);
		resetVariables2();
		blackAntHill();
		rocks();
	}

	public void checkRand()
	{
		if (randRow != 0 && randRow < world.length -12)
		{
			randRowCorrect = true;
		}
		else
		{

		}
		if (randCol > 5 && randCol < world.length-12)
		{
			randColCorrect = true;
		}
		else
		{

		}
	}

	public void resetVariables()
	{
		randRow = random.nextInt(world.length);
		randCol = random.nextInt(world.length);
		randRowCorrect = false;
		randColCorrect = false;
		skip = 0;
		count = 0;
		temp = 0;
		recursive = 0;
		length = 6;
	}

	public void resetVariables2()
	{
		skip = 0;
		count = 0;
		temp = 0;
		recursive = 0;
		length = 6;
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
		while (made < 15)
		{
			int tRow = random.nextInt(rows);
			int tColumn = random.nextInt(columns);
			if (!(world[tRow][tColumn].getIsRock() && !(tRow == 0 && tColumn == 0)))
			{
				if (!(world[tRow][tColumn].getIsRAntHill()) && !(world[tRow][tColumn].getIsBAntHill()))
				{
					world[tRow][tColumn].setRock(true);
					made++;
				}
			}   
		}
	}
	
	public void checkSpaceClear(int n)
	{
		while (!spaceClear)
		{
			if ((recursive < 11) && randRowCorrect && randColCorrect)
			{
				if (skip != 3)
				{
					for(int i = 0; i < length; i++)
					{
						if (getCell(randRow,(i+randCol)-temp).getIsRAntHill() == false);
						{
							n++;
						}
					}
					if (count%2 == 0)
					{
						skip++;
						temp++;
					}
					count++;
					randRow++;
					length++;
				}
				else if (skip == 3)
				{
					for(int j = 0; j < length; j++)
					{
						if (getCell(randRow,(j+randCol)-temp).getIsRAntHill() == false);
						{
							n++;
						}
					}
					if (count % 2 != 0)
					{
						temp--;
					}
					count++;
					randRow++;
					length--;
				}
				recursive++;	
				checkSpaceClear(n);
			}
			else if(!randRowCorrect || !randColCorrect)
			{
				if (!randRowCorrect)
				{
					randRow = random.nextInt(world.length);
				}
				if (!randColCorrect)
				{
					randCol = random.nextInt(world.length);
				}
				checkRand();
			}
			if (n == 91)
			{
				spaceClear = true;
			}
			else
			{
				n = 0;
			}
			checkRand();
		}
	}

	public void redAntHill()
	{
		if ((recursive < 11) && randRowCorrect && randColCorrect)
		{
			if (skip != 3)
			{
				for(int i = 0; i < length; i++)
				{
					world[randRow][(i+randCol)-temp].setRAntHill(true);
				}
				if (count%2 == 0)
				{
					skip++;
					temp++;
				}
				count++;
				randRow++;
				length++;
			}
			else if (skip == 3)
			{
				for(int j = 0; j < length; j++)
				{
					world[randRow][(j+randCol)-temp].setRAntHill(true);
				}
				if (count % 2 != 0)
				{
					temp--;
				}
				count++;
				randRow++;
				length--;
			}
			recursive++;	
			redAntHill();
		}
		else if(!randRowCorrect || !randColCorrect)
		{
			if (!randRowCorrect)
			{
				randRow = random.nextInt(world.length);
			}
			if (!randColCorrect)
			{
				randCol = random.nextInt(world.length);
			}
			checkRand();
			redAntHill();
		}
		checkRand();
	}

	public void blackAntHill()
	{
		if ((recursive < 11) && randRowCorrect && randColCorrect)
		{
			if (spaceClear)
			{
				if (skip != 3)
				{
					for(int i = 0; i < length; i++)
					{
						world[randRow][(i+randCol)-temp].setBAntHill(true);
					}
					if (count%2 == 0)
					{
						skip++;
						temp++;
					}
					count++;
					randRow++;
					length++;
				}
				else if (skip == 3)
				{
					for(int j = 0; j < length; j++)
					{
						world[randRow][(j+randCol)-temp].setBAntHill(true);
					}
					if (count % 2 != 0)
					{
						temp--;
					}
					count++;
					randRow++;
					length--;
				}
				recursive++;	
				blackAntHill();
			}
		}
		else if(!randRowCorrect || !randColCorrect)
		{
			if (!randRowCorrect)
			{
				randRow = random.nextInt(world.length);
			}
			if (!randColCorrect)
			{
				randCol = random.nextInt(world.length);
			}
			checkRand();
			blackAntHill();
		}
		checkRand();
	}

	public void food()
	{
		int row = world.length/2;
		int temp = 1;
		int length = 4; 
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setFood(true);
			world[row][j+(world.length/2-temp)].setFoodAmount(5);
		}
		row++;
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setFood(true);
			world[row][j+(world.length/2-temp)].setFoodAmount(5);
		}
		row++;
		temp--;
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setFood(true);
			world[row][j+(world.length/2-temp)].setFoodAmount(5);
		}
	}
}
