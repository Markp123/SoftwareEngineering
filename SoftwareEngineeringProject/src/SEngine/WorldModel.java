package SEngine;

import java.util.Random;

public class WorldModel {
	private Cell[][] world;
	private Random random;
	private int rows;
	private int columns;

	public WorldModel(int row, int column)
	{
		random = new Random();
		world = new Cell[row][column];
		rows = world.length;
		columns = world.length;
		for (int i = 0; i < world.length; i++)
		{                
			for(int j = 0; j < world.length; j++)
			{
				Cell cell = new Cell();
				world[i][j] = cell;
			}
		}
		food();
	}

	public static void main(String[] args)
	{
		WorldModel wm = new WorldModel(100, 100);
		wm.printWorld();
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
		int row = world.length/2;
		int temp = 1;
		int length = 6; 
		for(int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2)-temp].setRAntHill(true);
		}
		row++;
		temp++;
		length++;
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setRAntHill(true);
		}
		row++;
		length++;
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setRAntHill(true);
		}
		row++;
		temp++;
		length++;
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setRAntHill(true);
		}
		row++;
		length++;
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setRAntHill(true);
		}
		row++;
		temp++;
		length++;
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setRAntHill(true);
		}
		row++;
		temp--;
		length--;
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setRAntHill(true);
		}
		row++;
		length--;
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setRAntHill(true);
		}
		row++;
		temp--;
		length--;
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setRAntHill(true);
		}
		row++;
		length--;
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setRAntHill(true);
		}
		row++;
		temp--;
		length--;
		for (int j = 0; j < length; j++)
		{
			world[row][j+(world.length/2-temp)].setRAntHill(true);
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


	public void printWorld()
	{
		boolean needSpace = false;
		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world.length; j++)
			{
				if (needSpace)
				{
					System.out.print(" ");
					System.out.print(world[i][j].toString());
					System.out.print(" ");
				}
				else
				{
					System.out.print(world[i][j].toString());
					System.out.print("  ");
				}
			}
			System.out.println("");
			needSpace = !needSpace;
		}
	}
}