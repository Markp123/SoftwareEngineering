package SEngine;

/**
 * Software Engineering Project 2013
 * 
 * This is the world model class. Its primary job is to print out a representation of the world.
 *  
 * @author Team valiANT
 *
 */
public class WorldModel {

	private World world;
	
	/**
	 * Constructor for WorldModel 
	 * 
	 * @param world the world to be displayed
	 */
	public WorldModel(World world) {
		this.world = world;
	}
	
	/**
	 * print a representation of the world
	 */
	public void printWorld() {
		boolean needSpace = false;
		for (int i = 0; i < world.getRows(); i++) {
			for (int j = 0; j < world.getColumns(); j++)
			{
				if (needSpace)
				{
					System.out.print(" ");
					System.out.print(world.getCell(i,j).toString());
				}
				else
				{
					System.out.print(world.getCell(i,j).toString());
					System.out.print(" ");
				}
			}
			System.out.println("");
			needSpace = !needSpace;
		}
	}
	
	public static void main(String[] args) {
		World world = new World(150, 150);
		//world.writeWorld();
		//World world = new World("C:/Users/David/Desktop/sample0.world");
		WorldModel model = new WorldModel(world);
		model.printWorld();
	}

}
