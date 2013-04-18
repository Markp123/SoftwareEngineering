package SEngine;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * The GUI display of the ant world
 *
 * @author (18856)
 */
public class WorldGUI
{
	private ImageIcon icon = new ImageIcon("C:/Users/mpp27/ant_image.png");
	private JFrame gameScreen = new JFrame("Game");
	private JFrame main = new JFrame("Main Menu");
	private JLabel imageLabel = new JLabel(icon);
	private JPanel imagePanel = new JPanel();
	private JFrame twoPGame = new JFrame("Two Player Game");
	private JPanel twoPGamePanel = new JPanel();
	private JFrame tournamentFrame = new JFrame("Tournament");
	private JPanel tournamentPanel = new JPanel();
	private JFrame amountOfPlayersFrame = new JFrame("Amount Of Players");
	private JPanel numOfP = new JPanel();
	private JButton game = new JButton("Single Game");
	private JButton tournament = new JButton("Tournament");
	private JTextField choice1 = new JTextField();
	private JTextField choice2 = new JTextField();
	private JTextField NumOfPlayers = new JTextField();
	private JButton player1 = new JButton("Player 1");
	private JButton player2 = new JButton("Player 2");
	private JButton choosen = new JButton("Confirm");
	private JButton start = new JButton("START");
	private List<JButton> buttonArray = new ArrayList<JButton>();
	private List<JTextField> textFieldArray = new ArrayList<JTextField>();
	private JPanel board = new JPanel();
	private CellRep[][] cellArray;
	private int amountOfPlayers;
	private List<String> brains;
	private int count = 1;

	/**
	 * Constructor for the GUI
	 * 
	 * Adds JLabels to a JPanel displaying the state of the world. Sets the background colour of the JLabels to
	 * represent the state of the cell within the world
	 *
	 * @param world - the state of the world
	 */
	public WorldGUI()
	{
		brains = new ArrayList<String>();
		main.getContentPane().setLayout(new BorderLayout());
		imagePanel.add(imageLabel);
		main.add(game, BorderLayout.CENTER);
		main.add(tournament, BorderLayout.SOUTH);
		main.add(imagePanel, BorderLayout.NORTH);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.pack();
		main.setVisible(true);
	}

	public void setupWorld(World world)
	{
		cellArray = new CellRep[world.getRows()][world.getColumns()];
		gameScreen.getContentPane().setLayout(new BorderLayout());
		board.setLayout(new GridLayout(world.getRows(), world.getColumns()));
		
		for (int i = 0; i < world.getRows(); i++){
			for (int j = 0; j < world.getColumns(); j++){
				CellRep c = new CellRep(world.getCell(i, j));
				cellArray[i][j] = c;
				board.add(c);
			}
		}
		System.out.println("Game " + count);
		count++;
		gameScreen.getContentPane().add(board, BorderLayout.CENTER);
		gameScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameScreen.setPreferredSize(new Dimension(800,800));
		gameScreen.pack();
		gameScreen.setVisible(true);
	}
		
	public void showGame()
	{
		main.dispose();
		twoPGame.getContentPane().setLayout(new BorderLayout());
		twoPGamePanel.setLayout(new GridLayout(2,2));	
		twoPGamePanel.add(player1);
		twoPGamePanel.add(choice1);
		choice1.setEditable(false);
		twoPGamePanel.add(player2);
		twoPGamePanel.add(choice2);
		choice2.setEditable(false);
		twoPGame.add(twoPGamePanel, BorderLayout.CENTER);
		twoPGame.add(start, BorderLayout.SOUTH);
		twoPGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		twoPGame.pack();
		twoPGame.setVisible(true);
	}

	public void chooseAmountOfPlayers()
	{
		main.dispose();
		numOfP.setLayout(new GridLayout(1,2));
		amountOfPlayersFrame.getContentPane().setLayout(new BorderLayout());
		numOfP.add(new JLabel("Input number of players: "));
		numOfP.add(NumOfPlayers);
		amountOfPlayersFrame.add(numOfP, BorderLayout.CENTER);
		amountOfPlayersFrame.add(choosen, BorderLayout.SOUTH);
		amountOfPlayersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		amountOfPlayersFrame.pack();
		amountOfPlayersFrame.setVisible(true);
	}

	public void showTournament()
	{
		amountOfPlayersFrame.dispose();
		for (int i = 0; i < amountOfPlayers; i++)
		{
			buttonArray.add(new JButton("Player"+(i+1)));
			buttonArray.get(i).setName("" + i);
			buttonArray.get(i).addMouseListener(new MouseListener(){
				public void mouseClicked(MouseEvent e) {
					JButton button = (JButton)e.getSource();
					String buttonName = button.getName();
					JFileChooser brainChooser = new JFileChooser("C:/Users/mpp27/");
					int returnValue = brainChooser.showOpenDialog(null);
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = brainChooser.getSelectedFile();
						//Add selected file to tournament
						String path = selectedFile.getPath();
						path = path.replace("\\", "/");
						brains.add(path);
						System.out.println(path);
						textFieldArray.get(Integer.parseInt(buttonName)).setText(selectedFile.getName());
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

		for (int i = 0; i < amountOfPlayers; i++)
		{
			textFieldArray.add(new JTextField()); 
		}
		tournamentFrame.getContentPane().setLayout(new BorderLayout());
		tournamentPanel.setLayout(new GridLayout(amountOfPlayers, 2));
		for (int i = 0; i < amountOfPlayers; i++)
		{
			tournamentPanel.add(buttonArray.get(i));
			tournamentPanel.add(textFieldArray.get(i));
			textFieldArray.get(i).setEditable(false);
		}
		tournamentFrame.add(tournamentPanel, BorderLayout.CENTER);
		tournamentFrame.add(start, BorderLayout.SOUTH);
		tournamentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tournamentFrame.pack();
		tournamentFrame.setVisible(true);
	}
	
	public JFrame getGameScreen()
	{
		return gameScreen;
	}
	
	public void addBrain(String brain, int index)
	{
		brains.add(index, brain);
	}

	public List<String> getBrains()
	{
		return brains;
	}

	public List<JTextField> getTextFieldArray() {
		return textFieldArray;
	}

	public int getAmountOfPlayers() {
		return amountOfPlayers;
	}

	public List<JButton> getButtonArray() {
		return buttonArray;
	}

	public void setNumOfPlayers(int number){
		this.amountOfPlayers = number;
	}

	public JButton getStart() {
		return start;
	}

	public JFrame getTwoPGame()
	{
		return twoPGame;
	}

	public JButton getGame(){
		return game;
	}
	public JButton getTournament(){
		return tournament;
	}

	public JButton getPlayer1() {
		return player1;
	}

	public JButton getPlayer2() {
		return player2;
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
	
	public void endGame(){
		gameScreen.dispose();
	}

	public JTextField getChoice1() {
		return choice1;
	}
	
	public JTextField getChoice2() {
		return choice2;
	}

	public JButton getChoosen() {
		return choosen;
	}

	public JTextField getNumOfPlayers() {
		return NumOfPlayers;
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		WorldGUI view = new WorldGUI();
		WorldControl control = new WorldControl(view);
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
}