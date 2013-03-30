package SEngine;

public class WorldModel {
	private World world;
	
	public WorldModel(World world)
	{
		this.world = world;
	}
	
	public static void main(String[] args)
	{
		World world = new World(100, 100);
		WorldModel model = new WorldModel(world);
		model.printWorld();
	}


	public void printWorld()
	{
		boolean needSpace = false;
		for (int i = 0; i < world.getRows(); i++)
		{
			for (int j = 0; j < world.getColumns(); j++)
			{
				if (needSpace)
				{
					System.out.print(" ");
					System.out.print(world.getCell(i,j).toString());
					System.out.print(" ");
				}
				else
				{
					System.out.print(world.getCell(i,j).toString());
					System.out.print("  ");
				}
			}
			System.out.println("");
			needSpace = !needSpace;
		}
	}
}