package SEngine;
import java.util.Random;

public class World {
	private Cell[][] world;
	private Random random;
	private int rows, columns, randRow, randCol, made;
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
		checkSpaceClear(0,0,0,0,13);
		redAntHill(0,0,0,13);
		resetVariables();
		checkRand();
		checkSpaceClear(0,0,0,0,13);
		blackAntHill(0,0,0,13);
		while(made<11)
		{
			resetFood();
			checkRand();
			findClearFoodSpace(0, 0, 5, randRow);
			food(0,5,randRow);
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
	}

	public void resetFood()
	{
		randRow = random.nextInt(world.length);
		randCol = random.nextInt(world.length);
		randRowCorrect = false;
		randColCorrect = false;
		foodSpaceClear = false;
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
			if (!(world[tRow][tColumn].getIsRock() && !(tRow == 0 && tColumn == 0) && !(world[tRow][tColumn].getIsAntHill())))
			{
				if (!(world[tRow][tColumn].getIsRAntHill()) && !(world[tRow][tColumn].getIsBAntHill()))
				{
					world[tRow][tColumn].setRock(true);
					made++;
				}
			}   
		}
	}

	public void checkSpaceClear(int n, int temp, int count, int recursive, int length)
	{
		while (!spaceClear && recursive < 7)
		{
			if (randRowCorrect && randColCorrect)
			{
				for(int i = 0; i < length; i++)
				{
					if (getCell(randRow + count,(i+randCol)+temp).getIsAntHill() == false);
					{
						n++;
					}
				}
				for(int j = 0; j < length; j++)
				{
					if (getCell(randRow - count,(j+randCol)+temp).getIsAntHill() == false);
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
			if (n == 140)
			{
				spaceClear = true;
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
						world[randRow+count][(i+randCol)+temp].setAntHill();
					}
				}
				for(int j = 0; j < length; j++)
				{
					{
						world[randRow-count][(j+randCol)+temp].setRAntHill(true);
						world[randRow-count][(j+randCol)+temp].setAntHill();
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
						world[randRow+count][(i+randCol)+temp].setAntHill();
					}
				}
				for(int j = 0; j < length; j++)
				{
					{
						world[randRow-count][(j+randCol)+temp].setBAntHill(true);
						world[randRow-count][(j+randCol)+temp].setAntHill();
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

	public void findClearFoodSpace(int n, int recursive, int length, int randRow)
	{
		while (!foodSpaceClear)
		{
			if ((recursive < 5) && randRowCorrect && randColCorrect )
			{
				for (int j = 0; j < length; j++)
				{
					if ((getCell(randRow,j+randCol).getIsAntHill() == false))
					{
						n++;
					}
				}
				randRow++;
				recursive++;
				findClearFoodSpace(n, recursive, length, randRow);
			}
			if (n == 25)
			{
				foodSpaceClear = true;
			}
			else
			{
				n = 0;
			}
			checkRand();
		}
	}

	public void food(int recursive, int length, int randRow)
	{
		while(recursive<5)
		{
			if(foodSpaceClear)
			{
				for (int j = 0; j < length; j++)
				{
					world[randRow][j+randCol].setFood(true);
					world[randRow][j+randCol].setFoodAmount(5);
				}
				randRow++;
				recursive++;
				food(recursive, length, randRow);
			}
		}
	}
}
