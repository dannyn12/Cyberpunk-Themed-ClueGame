/* ClueGame class creates the GUI of the game. It contains the known cards panel, gameControl panel, and board panel which all form
 * to make the game GUI. 
 * Authors: Jordan Lam, Danny Nguyen
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	BoardPanel boardPanel;
	CardsPanel cardsPanel;
	GameControlPanel gameControl;

	public ClueGame() {
		boardPanel = new BoardPanel();
		cardsPanel = new CardsPanel();
		gameControl = new GameControlPanel();
		
		gameControl.setPreferredSize(new Dimension(900, 130));
		cardsPanel.setPreferredSize(new Dimension(243, getHeight()));

		add(boardPanel, BorderLayout.CENTER);
		add(gameControl, BorderLayout.SOUTH);
		add(cardsPanel, BorderLayout.EAST);
		
		JOptionPane.showMessageDialog(null, "You are Nova. \n" + "Can you find the solution \n" + "before the Computer players.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	/*
	 * Initialize board
	 */
	public static void main(String[] args) {
		ClueGame frame = new ClueGame();
		frame.setTitle("Clue Game");
		frame.setSize(925, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
