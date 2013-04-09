package SEngine;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The GUI display of the mine field
 * 
 * @author (18856) 
 */
public class WorldGUI extends JFrame
{
    private Cell[][] cells;
    private World world;
    private JPanel masterPanel = new JPanel(new BorderLayout());
    private JPanel board = new JPanel();
    private JPanel display = new JPanel();    

    public WorldGUI(World world)
    {
        this.world = world;
        cells = new Cell[world.getRows()][world.getColumns()];
        board.setLayout(new GridLayout(cells.length, cells.length));        
        for (int i = 0; i < cells.length; i++)
        {
            for (int j = 0; j < cells[i].length; j++)
            {
                Cell c = world.getCell(i, j);
                cells[i][j] = c;
                board.add(c);
            }
        }
        
        masterPanel.add(board, BorderLayout.CENTER);
        this.getContentPane().add(masterPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();                        
        this.setVisible(true);
    }
    
    public Cell[][] getTiles() 
    {
        return cells;
    }
    
    public static void main(String[] args)
    {
        World world = new World(150, 150);
        WorldGUI view = new WorldGUI(world);
        WorldControl control = new WorldControl(world, view);
    }
}
