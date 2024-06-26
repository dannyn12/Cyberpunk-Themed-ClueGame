/*
 * Authors: Jordan Lam & Danny Nguyen
 * this is the GameControlPanel which draws a GUI
 * showing the player buttons to make guesses and assumtions
 * and shows results 
 */
package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel{
	private Board board;
	private JPanel mainPanel;
	// top panel variables
	private JPanel subPanelTop;
	private JPanel subPanelTop1;
	private JTextField who;
	private JTextField roll;
	private JPanel subPanelTop2;
	private JButton subButtonTop1;
	private JButton subButtonTop2;
	// bottom panel variables
	private JPanel subPanelBottom;
	private JPanel subPanelBottom1;
	private JPanel subPanelBottom2;
	private JTextField theGuess;
	private JTextField theResult;
	
	private static ClueGame clueGame; // this is used for testing in main 
	
	/*
	 * Constructor for the panel
	 */
	public GameControlPanel() {
		board = gameSetUp();
        createTopPanel();
        createBottomPanel();

	}
	
	/*
	 * Creates to bottom half of the control panel
	 */
	private void createBottomPanel() {
		// Create the second sub-panel with a GridLayout of 0 row and 2 columns
        this.subPanelBottom = new JPanel(new GridLayout(0, 2));
        
        // Left bottom sub panel
        this.subPanelBottom1 = new JPanel(new GridLayout(1,0));
        this.subPanelBottom1.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
        this.theGuess = new JTextField(); 
        theGuess.setEditable(false);
        subPanelBottom1.add(theGuess);
        
        // Right bottom sub panel
        this.subPanelBottom2 = new JPanel(new GridLayout(1,0));
        this.subPanelBottom2.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
        this.theResult = new JTextField(); 
        theResult.setEditable(false);
        subPanelBottom2.add(theResult);       
        
        // add bottom sub panels to bottom panel
        subPanelBottom.add(subPanelBottom1);
        subPanelBottom.add(subPanelBottom2);
        // add bottom panel to main panel
        mainPanel.add(subPanelBottom);

        // add the main panel to this GameControlPanel
        add(mainPanel);
        
	}

	/*
	 * Creates top half of control panel
	 */
	private void createTopPanel() {
		// Create a layout with 2 rows and 0 columns for the main panel
		this.mainPanel = new JPanel(new GridLayout(2, 0));
		mainPanel.setPreferredSize(new Dimension(895,120));

		// Create the first sub-panel with a GridLayout of 1 row and 4 columns
		this.subPanelTop = new JPanel(new GridLayout(0, 4));

		// Add components to the first sub-panel
		this.subPanelTop1 = new JPanel(new GridLayout(2,0));
		subPanelTop1.add(new JLabel("<html><b>Whose turn?</b></html>"));
		this.who = new JTextField(14); // 14
		who.setEditable(false);
		subPanelTop1.add(who, BorderLayout.NORTH);

		this.subPanelTop2 = new JPanel();
		this.roll = new JTextField(5); // 5
		roll.setEditable(false);
		subPanelTop2.add(new JLabel("<html><b>Roll:</b></html>"));
		subPanelTop2.add(roll);

		this.subButtonTop1 = new JButton("<html><b>Make Accusation</b></html>");
		this.subButtonTop2 = new JButton("<html><b>NEXT!</b></html>");
		subButtonTop1.setPreferredSize(new Dimension(70,70));
		subButtonTop1.setOpaque(true);
		subButtonTop2.setOpaque(true);


		// add top sub panels to top panel
		subPanelTop.add(subPanelTop1);
		subPanelTop.add(subPanelTop2);
		subPanelTop.add(subButtonTop1);
		subPanelTop.add(subButtonTop2);

		// add top panel to main panel
		mainPanel.add(subPanelTop);

	}
	
	/*
	 * Initialize game to create board
	 */
	private Board gameSetUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		return board;
	}
	
	/*
	 * Method sets GUI to display who the player that has the current turn with color
	 */
	public void setTurn(List<Player> player, int currentPlayerIndex, int rollNumber) {
	    String color = player.get(currentPlayerIndex).getColor();
	    Color bgColor;
	    
	    // change color of current player
	    switch (color) {
	        case "Blue":
	            bgColor = Color.CYAN;
	            break;
	        case "Green":
	            bgColor = Color.GREEN;
	            break;
	        case "Red":
	            bgColor = Color.RED;
	            break;
	        case "Orange":
	            bgColor = new Color(255, 165, 0);
	            break;
	        case "Yellow":
	            bgColor = Color.YELLOW;
	            break;
	        case "Pink":
	            bgColor = Color.PINK;
	            break;
	        case "Purple":
	        	Color purple = new Color(148, 150, 211);
	            bgColor = purple;
	            break;
	        case "White":
	            bgColor = Color.WHITE;
	            break;
	        default:
	            bgColor = Color.BLACK; 
	            break;
	    }
	    
	    // change color and name of GUI
	    who.setBackground(bgColor);
	    who.setText(player.get(currentPlayerIndex).getName());
	    roll.setText(String.valueOf(rollNumber));
	   
	}

	public JButton getSubButtonTop1() {
		return subButtonTop1;
	}
		
	public JButton getSubButtonTop2() {
		return subButtonTop2;
	}

	public void setGuess(String guess) {
		theGuess.setText(guess);	
	}
	
	public void setGuessResult(String result) {
		theResult.setText(result);
	}
		
	/*
	 * Main to test the panel
	 * 
	 * @param args
	 */
	   public static void main(String[] args) {
			GameControlPanel panel = new GameControlPanel(); // Pass the board instance to the panel constructor
	        JFrame frame = new JFrame(); // create the frame
	        frame.setContentPane(panel); // put the panel in the frame
	        frame.setSize(900, 180); // size the frame
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
	        frame.setVisible(true); // make it visible
	    }

}
