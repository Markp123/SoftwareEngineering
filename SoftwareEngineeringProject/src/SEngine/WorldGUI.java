package SEngine;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * The GUI controls the game and displays the game world
 *
 * @author (18856)
 */
public class WorldGUI extends JFrame
{
	private ImageIcon icon = new ImageIcon("ant_image.png");
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
	
	/**
	 * Constructor for the GUI
	 * 
	 * This creates the main menu that greets the player
	 *
	 */
	public WorldGUI()
	{
		brains = new ArrayList<String>();
		main.getContentPane().setLayout(new BorderLayout());
		imagePanel.add(imageLabel);
		main.add(game, BorderLayout.CENTER);
		main.add(tournament, BorderLayout.SOUTH);
		main.add(imagePanel, BorderLayout.NORTH);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);
		main.pack();
		main.setVisible(true);
	}
	
	public WorldGUI(World world) {
		this.getContentPane().setLayout(new BorderLayout());
		cellArray = new CellRep[world.getRows()][world.getColumns()];
		board.setLayout(new GridLayout(world.getRows(), world.getColumns()));
		for (int i = 0; i < world.getRows(); i++){
			for (int j = 0; j < world.getColumns(); j++){
				CellRep c = new CellRep(world.getCell(i, j));
				board.add(c);
				cellArray[i][j] = c;
			}
		}
		board.setVisible(true);
		this.add(board, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800,800));
		this.pack();
		this.setVisible(true);
	}

	/**
	 * This sets up the original state of the world using the input world parameter
	 * 
	 * @param world - The original state of the world
	 */
	public void setupWorld(World world) {
		this.getContentPane().setLayout(new BorderLayout());
		cellArray = new CellRep[world.getRows()][world.getColumns()];
		board.setLayout(new GridLayout(world.getRows(), world.getColumns()));
		for (int i = 0; i < world.getRows(); i++){
			for (int j = 0; j < world.getColumns(); j++){
				CellRep c = new CellRep(world.getCell(i, j));
				board.add(c);
				cellArray[i][j] = c;
			}
		}
		board.setVisible(true);
		this.add(board, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800,800));
		this.pack();
		this.setVisible(true);
	}

	/**
	 * This shows the two player brain selection screen
	 */
	public void showGame(){
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
		twoPGame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		twoPGame.pack();
		twoPGame.setVisible(true);
	}

	/**
	 * This shows the screen where the player selects the amount of players
	 */
	public void chooseAmountOfPlayers(){
		main.dispose();
		numOfP.setLayout(new GridLayout(1,2));
		amountOfPlayersFrame.getContentPane().setLayout(new BorderLayout());
		numOfP.add(new JLabel("Input number of players: "));
		numOfP.add(NumOfPlayers);
		amountOfPlayersFrame.add(numOfP, BorderLayout.CENTER);
		amountOfPlayersFrame.add(choosen, BorderLayout.SOUTH);
		amountOfPlayersFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		amountOfPlayersFrame.pack();
		amountOfPlayersFrame.setVisible(true);
	}

	/**
	 * This shows the brain selection screen for the tournament
	 */
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
					JFileChooser brainChooser = new JFileChooser("");
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
		tournamentFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		tournamentFrame.pack();
		tournamentFrame.setVisible(true);
	}
	
	/**
	 * returns the two player game screen
	 * 
	 * @return gameScreen
	 */
	public JFrame getGameScreen()
	{
		return gameScreen;
	}
	
	/**
	 * adds a path to a brain
	 * 
	 * @param brain
	 */
	public void addBrain(String brain)
	{
		brains.add(brain);
	}

	/**
	 * Returns the brains to be used in the game
	 * @return brains
	 */
	public List<String> getBrains()
	{
		return brains;
	}

	/**
	 * Returns the textfields used in the tournament brain selection screen
	 */
	public List<JTextField> getTextFieldArray() {
		return textFieldArray;
	}

	/**
	 * Returns the amount of players
	 * @return amountOfPlayers
	 */
	public int getAmountOfPlayers() {
		return amountOfPlayers;
	}

	/**
	 * Returns the buttons used in the tournament brain selection screen
	 * 
	 * @return buttonArray
	 */
	public List<JButton> getButtonArray() {
		return buttonArray;
	}

	/**
	 * Sets the amount of players in the tournament
	 * @param number
	 */
	public void setNumOfPlayers(int number){
		this.amountOfPlayers = number;
	}

	/**
	 * Returns the start button
	 * @return start
	 */
	public JButton getStart() {
		return start;
	}

	/**
	 * Returns the two player game screen
	 * @return twoPGame
	 */
	public JFrame getTwoPGame(){
		return twoPGame;
	}

	/**
	 * returns the single game button
	 * @return game
	 */
	public JButton getGame(){
		return game;
	}
	
	/**
	 * Returns the tournament button
	 * @return tournament
	 */
	public JButton getTournament(){
		return tournament;
	}

	/**
	 * Returns the player 1 button in the two player game screen
	 * @return player1
	 */
	public JButton getPlayer1() {
		return player1;
	}

	/**
	 * Returns the player 2 button in the two player game screen
	 * @return player2
	 */
	public JButton getPlayer2() {
		return player2;
	}


	/**
	 * Updates the GUI with the new state of the world
	 * 
	 * @param world - the new state of the world
	 */
//		public void update(World world){
//			for (int i = 0; i < world.getRows(); i++)
//			{
//				for (int j = 0; j < world.getColumns(); j++)
//				{
//					cellArray[i][j].updateCell(cellArray[i][j]);
//				}
//			}
//		}
	public void update(List<Point> cellsToUpdateList){
		for(Point p : cellsToUpdateList){
			int i = p.x;
			int j = p.y;
			cellArray[i][j].updateCell(cellArray[i][j]);
		}		
	}
	
	/**
	 * Removes the world display
	 */
	public void endGame(){
		gameScreen.dispose();
	}

	/**
	 * Returns the textfield displaying player 1's choice
	 * @return choice1
	 */
	public JTextField getChoice1() {
		return choice1;
	}
	
	/**
	 * Returns the textfield displaying player 2's choice
	 * @return choice2
	 */
	public JTextField getChoice2() {
		return choice2;
	}

	/**
	 * Returns the button from the amount of players selection screen
	 * @return choosen
	 */
	public JButton getChoosen() {
		return choosen;
	}

	/**
	 * Returns the textfield holding the number of players
	 * @return NumOfPlayers
	 */
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