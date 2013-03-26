package SEngine;

public class WorldModel {
	private Cell[][] world;
	
	public WorldModel()
	{
		world = new Cell[20][20];
		
		for (int i = 0; i < world.length; i++)
        {                
            for(int j = 0; j < world.length; j++)
            {
                Cell cell = new Cell();
                world[i][j] = cell;
            }
        }
	}
	
	public static void main(String[] args)
	{
		WorldModel wm = new WorldModel();
		wm.printWorld();
	}
	
	public void printWorld()
	{
		boolean hex = false;
		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world.length; j++)
			{
				if (hex)
				{
					System.out.print(" ");
					System.out.print(world[i][j].toString());
					System.out.print("|");
				}
			}
			System.out.println("");
			hex = !hex;
		}
	}
}
