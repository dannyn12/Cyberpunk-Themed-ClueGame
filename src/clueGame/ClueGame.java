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
		
		gameControl.setPreferredSize(new Dimension(boardPanel.getWidth(), 150));
		cardsPanel.setPreferredSize(new Dimension(200, getHeight()));

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
