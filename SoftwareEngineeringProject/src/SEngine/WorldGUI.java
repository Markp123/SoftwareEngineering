package SEngine;

import java.awt.*;
import javax.swing.*;

/**
* The GUI display of the ant world
*
* @author (18856)
*/
public class WorldGUI extends JFrame
{
    private JPanel board = new JPanel();
    
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
        this.getContentPane().setLayout(new BorderLayout());
        board.setLayout(new GridLayout(world.getRows(), world.getColumns()));
        for (int i = 0; i < world.getRows(); i++)
        {
            for (int j = 0; j < world.getColumns(); j++)
            {
            	Cell c = world.getCell(i, j);
                JLabel jl = new JLabel();
                if(c.isAnt()){
        			if(c.getAnt().getColour()==Colour.RED) {
        				jl.setBackground(new Color(238));
        			} else {
        				jl.setBackground(Color.black);
        			}
        		} else if(c.getIsRock()) {
        			jl.setBackground(new Color(91,91,91));
        		} else if (c.getIsFood()) {
        			jl.setBackground(new Color(255,215,0));
        		} else if (c.getIsRAntHill()) {
        			jl.setBackground(new Color(255,106,106));
        		} else if (c.getIsBAntHill()) {
        			jl.setBackground(new Color(193,193,193));
        		} else if (c.getIsEmpty()) {
        			jl.setBackground(new Color(113,198,113));
        		}
                jl.setOpaque(true);
            	
                board.add(jl);
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
    public void update(World world){
    	JPanel newBoard = new JPanel();
    	newBoard.setLayout(new GridLayout(world.getRows(), world.getColumns()));
    	this.remove(board);
    	for (int i = 0; i < world.getRows(); i++)
        {
            for (int j = 0; j < world.getColumns(); j++)
            {
            	Cell c = world.getCell(i, j);
                JLabel jl = new JLabel();
                if(c.isAnt()){
        			if(c.getAnt().getColour()==Colour.RED) {
        				jl.setBackground(new Color(238));
        			} else {
        				jl.setBackground(Color.black);
        			}
        		} else if(c.getIsRock()) {
        			jl.setBackground(new Color(91,91,91));
        		} else if (c.getIsFood()) {
        			jl.setBackground(new Color(255,215,0));
        		} else if (c.getIsRAntHill()) {
        			jl.setBackground(new Color(255,106,106));
        		} else if (c.getIsBAntHill()) {
        			jl.setBackground(new Color(193,193,193));
        		} else if (c.getIsEmpty()) {
        			jl.setBackground(new Color(113,198,113));
        		}
                jl.setOpaque(true);
                newBoard.add(jl);
            }
        }
    	this.getContentPane().add(newBoard, BorderLayout.CENTER);
    	newBoard.validate();
    	this.repaint();
    	this.setVisible(true);
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
}