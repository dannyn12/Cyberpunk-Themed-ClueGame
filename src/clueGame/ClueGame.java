package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

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

	}
	
	public static void main(String[] args) {
		ClueGame frame = new ClueGame();
		frame.setSize(900, 900);
		frame.setVisible(true);
	}
}
