package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
    private Board board;

    public BoardPanel() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setSize(new Dimension(650, 702));

        // Calculate cell size 
        int cellSize = getWidth() / board.getNumColumns();

        // Draw board cells
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumColumns(); col++) {
                BoardCell cell = board.getCell(row, col);
                cell.draw(g, cellSize);
              
            }
        }

        // Draw players
        for (Player player : board.getPlayers()) {
        	int playerX = player.getCol() * cellSize + cellSize / 2;
        	int playerY = player.getRow() * cellSize + cellSize / 2;

        	// Set color
        	switch (player.getColor()) {
        	case "Green":
        		g.setColor(Color.GREEN);
        		break;
        	case "Purple":
        		Color purple = new Color(148,0,211);
        		g.setColor(purple);
        		break;
        	case "Pink":
        		g.setColor(Color.PINK);
        		break;
        	case "Orange":
        		g.setColor(Color.ORANGE);
        		break;
        	case "Blue":
        		g.setColor(Color.BLUE);
        		break;
        	case "Red":
        		g.setColor(Color.RED);
        		break;
        	default:
        		g.setColor(Color.BLACK);
        		break;
        	}

        	g.fillOval(playerX - cellSize / 2, playerY - cellSize / 2, cellSize, cellSize);
        }
    }
}

