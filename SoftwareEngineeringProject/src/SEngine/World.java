package SEngine;
import java.util.Random;

public class World {
	private Cell[][] world;
	private Random random;
	private int rows, columns, randRow, randCol, made, foodRecursive;
	private boolean randRowCorrect, randColCorrect, spaceClear, foodSpaceClear;


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
		checkSpaceClear(0,0,0,0,15);
		redAntHill(0,0,0,13);
		resetVariables();
		checkRand();
		checkSpaceClear(0,0,0,0,15);
		blackAntHill(0,0,0,13);
		while(made<11)
		{
			resetFood();
			checkRand();
			findClearFoodSpace(0, 7, 0, 0);
			food(5, 0, 0);
			made++;
		}
		rocks();
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
			checkRand();
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
						n = 0;
					}
				}
			}   
			if (rockSpaceClear)
			{
				world[randRow][randCol].setRock(true);
				made++;
				rockSpaceClear = false;
			}
		}
	}

	public void checkSpaceClear(int n, int temp, int count, int recursive, int length)
	{
		while (!spaceClear)
		{
			if (recursive <8 && randRowCorrect && randColCorrect)
			{
				for(int i = 0; i < length; i++)
				{
					if (getCell(randRow + count,((i+randCol)+temp)-1).getIsAntHill() == false);
					{
						n++;
					}
				}
				for(int j = 0; j < length; j++)
				{
					if (getCell(randRow - count,((j+randCol)+temp)-1).getIsAntHill() == false);
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
			else
			{
				checkRand();
			}
			if (n == 184)
			{
				spaceClear = true;
			}
			else if (recursive == 8 && spaceClear == false)
			{
				n = 0;
				recursive = 0;
				count = 0;
				temp = 0;
				length = 15;
				resetVariables();
				checkRand();
				checkSpaceClear(n, temp, count, recursive, length);
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
					}
				}
				for(int j = 0; j < length; j++)
				{
					{
						world[randRow-count][(j+randCol)+temp].setRAntHill(true);
						world[randRow-count][(j+randCol)+temp].setIsAntHill(true);
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
					}
				}
				for(int j = 0; j < length; j++)
				{
					{
						world[randRow-count][(j+randCol)+temp].setBAntHill(true);
						world[randRow-count][(j+randCol)+temp].setIsAntHill(true);
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

	public void findClearFoodSpace(int n, int length, int count, int recursive)
	{
		while (!foodSpaceClear)
		{
			if ((recursive < 4) && randRowCorrect && randColCorrect )
			{
				for(int i = 0; i < length; i++)
				{
					if (!(getCell(randRow + count,(i+randCol)-1).getIsAntHill()) && !(getCell(randRow + count,i+randCol).getIsFood()))
					{
						n++;
					}
				}
				for(int j = 0; j < length; j++)
				{
					if (!(getCell(randRow - count,(j+randCol)-1).getIsAntHill()) && !(getCell(randRow + count,j+randCol).getIsFood()))
					{
						n++;
					}
				}
				recursive++;
				findClearFoodSpace(n, length, count, recursive);
			}
			if (n == 56)
			{
				foodSpaceClear = true;
			}
			else if (recursive == 4 && foodSpaceClear == false)
			{
				n = 0;
				recursive = 0;
				count = 0;
				resetFood();
				checkRand();
				findClearFoodSpace(n, length, count, recursive);
			}
		}
	}

	public void food(int length, int count, int recursive)
	{
		while(recursive < 3)
		{
			if(foodSpaceClear  == true)
			{
				for(int i = 0; i < length; i++)
				{
					{
						world[randRow+count][i+randCol].setFood(true);
						world[randRow+count][i+randCol].setFoodAmount(5);
					}
				}
				for(int j = 0; j < length; j++)
				{
					{
						world[randRow-count][j+randCol].setFood(true);
						world[randRow-count][j+randCol].setFoodAmount(5);
					}
				}
				recursive++;
				count++;
				food(length, count, recursive);
			}
		}
	}
}
