package SEngine;

import java.awt.event.*;
import java.awt.*;
import java.io.File;

import javax.swing.*;

public class WorldControl extends MouseAdapter
{
	private WorldGUI view;
	private World world;

	public WorldControl(final WorldGUI view)
	{
		this.view = view;

		view.getGame().addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				view.setNumOfPlayers(2);
				view.showGame();
			}
			public void mouseEntered(MouseEvent e){
			}
			public void mouseExited(MouseEvent e){
			}
			public void mousePressed(MouseEvent e){
			}
			public void mouseReleased(MouseEvent e){
			}
		});

		view.getTournament().addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				view.chooseAmountOfPlayers();
			}
			public void mouseEntered(MouseEvent e){
			}
			public void mouseExited(MouseEvent e){
			}
			public void mousePressed(MouseEvent e){
			}
			public void mouseReleased(MouseEvent e){

			}
		});

		view.getPlayer1().addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				JFileChooser p1Chooser = new JFileChooser("C:/Users/Mark/Desktop/");
				int returnValue = p1Chooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = p1Chooser.getSelectedFile();
					//Add selected file to tournament
					String path = selectedFile.getPath();
					path = path.replace("\\", "/");
					view.addBrain(path,0);
					System.out.println(path);
					view.getChoice1().setText(selectedFile.getName());
				}
			}
			public void mouseEntered(MouseEvent e){
			}
			public void mouseExited(MouseEvent e){
			}
			public void mousePressed(MouseEvent e){
			}
			public void mouseReleased(MouseEvent e){
			}
		});

		view.getPlayer2().addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				JFileChooser p2Chooser = new JFileChooser("C:/Users/Mark/Desktop/");
				int returnValue = p2Chooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = p2Chooser.getSelectedFile();
					//Add selected file to tournament
					String path = selectedFile.getPath();
					path = path.replace("\\", "/");
					view.addBrain(path, 1);
					System.out.println(path);
					view.getChoice2().setText(selectedFile.getName());
				}
			}
			public void mouseEntered(MouseEvent e){
			}
			public void mouseExited(MouseEvent e){
			}
			public void mousePressed(MouseEvent e){
			}
			public void mouseReleased(MouseEvent e){

			}
		});

		view.getChoosen().addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				view.setNumOfPlayers(Integer.parseInt(view.getNumOfPlayers().getText()));
				view.showTournament();
			}
			public void mouseEntered(MouseEvent e){
			}
			public void mouseExited(MouseEvent e){
			}
			public void mousePressed(MouseEvent e){
			}
			public void mouseReleased(MouseEvent e){
			}
		});

		for (int i = 0; i < view.getAmountOfPlayers(); i ++)
		{
			view.getButtonArray().get(i).addMouseListener(new MouseListener(){
				public void mouseClicked(MouseEvent e) {
					JButton button = (JButton)e.getSource();
					String buttonName = button.getName();
					JFileChooser brainChooser = new JFileChooser();
					int returnValue = brainChooser.showOpenDialog(null);
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = brainChooser.getSelectedFile();
						//Add selected file to tournament
						view.getTextFieldArray().get(Integer.parseInt(buttonName)).setText(selectedFile.getName());
					}
				}
				public void mouseEntered(MouseEvent e){
				}
				public void mouseExited(MouseEvent e){
				}
				public void mousePressed(MouseEvent e){
				}
				public void mouseReleased(MouseEvent e){
				}
			});
		}


		view.getStart().addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				new Tournament(view.getAmountOfPlayers(), view.getBrains(), view);
			}
			public void mouseEntered(MouseEvent e){
			}
			public void mouseExited(MouseEvent e){
			}
			public void mousePressed(MouseEvent e){
			}
			public void mouseReleased(MouseEvent e){
			}
		});
	}
}






