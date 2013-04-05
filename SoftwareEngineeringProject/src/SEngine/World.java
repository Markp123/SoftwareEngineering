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
		redAntHill(0,0,0,randRow,0,6);
		resetVariables();
		checkRand();
		checkSpaceClear(0,0,0,0,randRow,0,6);
		blackAntHill(0,0,0,randRow,0,6);
		while(made<11)
		{
			resetVariables();
			checkRand();
			findClearFoodSpace(0, 0, 5, randRow);
			food(0,5,randRow);
			made++;
		}
		rocks();
	}

	public void checkRand()
	{
		while (!randRowCorrect || !randColCorrect)
		{
			if (!randRowCorrect)
			{
				if (randRow != 0 && randRow < world.length -12)
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
				if (randCol > 5 && randCol < world.length-12)
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

	public void checkSpaceClear(int n, int half, int temp, int count, int randRow, int recursive, int length)
	{
		while (!spaceClear)
		{
			if ((recursive < 11) && randRowCorrect && randColCorrect)
			{
				if (half != 3)
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
						half++;
					}
					if (randRow % 2 == 0)
					{
						temp++;
					}
					count++;
					randRow++;
					length++;
				}
				else if (half == 3)
				{
					for(int j = 0; j < length; j++)
					{
						if (getCell(randRow,(j+randCol)-temp).getIsRAntHill() == false);
						{
							n++;
						}
					}
					if (randRow % 2 != 0)
					{
						temp--;
					}
					count++;
					randRow++;
					length--;
				}
				recursive++;	
				checkSpaceClear(n, half, temp, count, randRow, recursive, length);
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

	public void redAntHill(int half, int temp, int count, int randRow, int recursive, int length)
	{
		while (recursive < 11)
		{
			if (half != 3)
			{
				for(int i = 0; i < length; i++)
				{
					world[randRow][(i+randCol)-temp].setRAntHill(true);
				}
				if (count%2 == 0)
				{
					half++;
				}
				if (randRow % 2 == 0)
				{
					temp++;
				}
				count++;
				randRow++;
				length++;
			}
			else if (half == 3)
			{
				for(int j = 0; j < length; j++)
				{
					world[randRow][(j+randCol)-temp].setRAntHill(true);
				}
				if (randRow % 2 != 0)
				{
					temp--;
				}
				count++;
				randRow++;
				length--;
			}
			recursive++;	
			redAntHill(half, temp, count, randRow, recursive, length);
		}

	}

	public void blackAntHill(int half, int temp, int count, int randRow, int recursive, int length)
	{
		while (recursive < 11)
		{
			if (spaceClear)
			{
				if (half != 3)
				{
					for(int i = 0; i < length; i++)
					{
						world[randRow][(i+randCol)-temp].setBAntHill(true);
					}
					if (count%2 == 0)
					{
						half++;
					}
					if (randRow % 2 == 0)
					{
						temp++;
					}
					count++;
					randRow++;
					length++;
				}
				else if (half == 3)
				{
					for(int j = 0; j < length; j++)
					{
						world[randRow][(j+randCol)-temp].setBAntHill(true);
					}
					if (randRow % 2 != 0)
					{
						temp--;
					}
					count++;
					randRow++;
					length--;
				}
				recursive++;	
				blackAntHill(half, temp, count, randRow, recursive, length);
			}
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
					if ((getCell(randRow,j+randCol).getIsRAntHill()) == false && (getCell(randRow,j+randCol).getIsBAntHill()) == false)
					{
						n++;
					}
				}
				randRow++;
				recursive++;
				findClearFoodSpace(n, recursive, length, randRow);
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
		while(recursive<6)
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
