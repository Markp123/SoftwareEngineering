package SEngine;

import java.awt.event.*;
import java.awt.Dialog;

public class WorldControl extends MouseAdapter
{
	private WorldGUI view;
	private World world;

	public WorldControl(final World world, final WorldGUI view)
	{
		this.view = view;
		this.world = world;

		for(int i = 0; i < cells.length;i++) 
		{
			for(int k=0; k < cells[i].length;k++) 
			{
				final int row = i;
				final int col = k;
				cells[i][k].addMouseListener(new MouseListener(){

					public void mouseClicked(MouseEvent e) {

						if (e.getButton() == MouseEvent.BUTTON1) 
						{
							//left mouse click
						}
						else if (e.getButton() == MouseEvent.BUTTON3) 
						{
							//right mouse click
						
						}
					}

					public void mouseEntered(MouseEvent e) 
					{

					}

					public void mouseExited(MouseEvent e) 
					{

					}

					public void mousePressed(MouseEvent e) 
					{

					}

					public void mouseReleased(MouseEvent e) 
					{

					}
				});
			}
		}
	}
}
