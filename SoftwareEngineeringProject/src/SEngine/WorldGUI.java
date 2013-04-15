package SEngine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * The GUI display of the ant world
 *
 * @author (18856)
 */
public class WorldGUI extends JFrame
{
	private JPanel board = new JPanel();
	private CellRep[][] cellArray;

	/**
	 * Constructor for the GUI
	 * 
	 * Adds JLabels to a JPanel displaying the state of the world. Sets the background colour of the JLabels to
	 * represent the state of the cell within the world
	 *
	 * @param world - the state of the world
	 */
	public WorldGUI(World world)
	{
		cellArray = new CellRep[world.getRows()][world.getColumns()];
		this.getContentPane().setLayout(new BorderLayout());
		board.setLayout(new GridLayout(world.getRows(), world.getColumns()));
		for (int i = 0; i < world.getRows(); i++)
		{
			
			for (int j = 0; j < world.getColumns(); j++)
			{
				CellRep c = new CellRep(world.getCell(i, j));
				cellArray[i][j] = c;
				board.add(c);
			}
		}
		this.getContentPane().add(board, BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(800,800));
		this.setVisible(true);
	}

	/**
	 * Updates the GUI with the new state of the world
	 * 
	 * @param world - the new state of the world
	 */
//	public void update(World world){
//		for (int i = 0; i < world.getRows(); i++)
//		{
//			for (int j = 0; j < world.getColumns(); j++)
//			{
//				cellArray[i][j].updateCell(cellArray[i][j]);
//			}
//		}
//	}
	public void update(List<Point> cellsToUpdateList){
		for(Point p : cellsToUpdateList){
			int i = p.x;
			int j = p.y;
			cellArray[i][j].updateCell(cellArray[i][j]);
		}
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		World world = new World(150, 150);
		WorldGUI view = new WorldGUI(world);
		//WorldControl control = new WorldControl(world, view);
	}

	class CellRep extends JLabel
	{
		private Cell cell;

		public CellRep(Cell cell)
		{
			this.cell = cell;
			this.setBackground(cell.toColour());
			this.setOpaque(true);
		}
		
		public void updateCell(CellRep newCell)
		{
			newCell.setBackground(this.cell.toColour());
		}
	}
	
	public void endGame(){
		this.dispose();
	}
}