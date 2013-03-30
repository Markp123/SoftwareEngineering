package SEngine;

import java.util.Random;

public class World {
	private Cell[][] world;
	private Random random;
	private int rows;
	private int columns;
	private int recursive;
	private int skip = 0;
	private int count = 0;
	private int start;
	private int temp = 1;
	private int length = 6;

	public World(int row, int column)
	{
		random = new Random();
		world = new Cell[row][column];
		rows = world.length;
		columns = world.length;
		start = world.length/2;
		for (int i = 0; i < world.length; i++)
		{                
			for(int j = 0; j < world.length; j++)
			{
				Cell cell = new Cell();
				world[i][j] = cell;
			}
		}
		rocks();
		antHill();
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
			if (!world[tRow][tColumn].getIsRock() && !(tRow == 0 && tColumn == 0))
			{
				world[tRow][tColumn].setRock(true);
				made++;
			}       
		}
	}

	public void antHill()
	{
		if (recursive <12)
		{
			if (count%2 == 0 && skip != 3)
			{
				for(int j = 0; j < length; j++)
				{
					world[start][j+(world.length/2)-temp].setRAntHill(true);
				}
				count++;
				skip++;
				temp++;
				start++;
				length++;
			}
			else if (count %2 != 0 && skip != 3)
			{
				for(int j = 0; j < length; j++)
				{
					world[start][j+(world.length/2)-temp].setRAntHill(true);
				}
				count++;
				start++;
				length++;
			}
			else if (count % 2 != 0 && skip == 3)
			{
				for(int j = 0; j < length; j++)
				{
					world[start][j+(world.length/2)-temp].setRAntHill(true);
				}
				count++;
				start++;
				length--;
				temp--;
			}
			else
			{
				for(int j = 0; j < length; j++)
				{
					world[start][j+(world.length/2)-temp].setRAntHill(true);
				}
				count++;
				start++;
				length--;
			}
			recursive++;
			antHill();	
		}
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
