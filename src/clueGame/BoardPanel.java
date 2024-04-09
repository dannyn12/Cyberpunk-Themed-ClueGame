/* BoardPanel class draws the cells, rooms, and players on the board. It contains a drawCell, drawDoorAndLabel, and draw player method
 * to draw each component on the board.
 * Authors: Jordan Lam, Danny Nguyen
 */
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
    
    /*
     * Draws board cells and rooms
     */
    public void drawCell(Graphics g) {
        // Calculate cell size 
        int cellWidth = getWidth() / board.getNumColumns();
        int cellHeight = getHeight() / board.getNumRows();

        // Draw board cells
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumColumns(); col++) {
                BoardCell cell = board.getCell(row, col);
                cell.draw(g, cellWidth, cellHeight);   
            }
        }
        repaint();
    }
    
    /*
     * Draw doors and room labels
     */
    public void drawDoorAndLabel(Graphics g) {
    	// Calculate cell size 
        int cellWidth = getWidth() / board.getNumColumns();
        int cellHeight = getHeight() / board.getNumRows();
        
        // Draw Doors and Labels
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumColumns(); col++) {
                BoardCell cell = board.getCell(row, col);
                cell.drawLabelDoor(g, cellWidth, cellHeight);
            }
        }
        repaint();
    }
    
    /*
     * Draw game players
     */
    public void drawPlayers(Graphics g) {
        // Calculate cell size 
        int cellWidth = getWidth() / board.getNumColumns();
        int cellHeight = getHeight() / board.getNumRows();
        
        // Draw players
        for (Player player : board.getPlayers()) {
            int playerX = player.getCol() * cellWidth + cellWidth / 2;
            int playerY = player.getRow() * cellHeight + cellHeight / 2; 

            // Set color
            switch (player.getColor()) {
                case "Green":
                    g.setColor(Color.GREEN);
                    break;
                case "Purple":
                    Color purple = new Color(148, 0, 211);
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
            g.fillOval(playerX - cellWidth / 2, playerY - cellHeight / 2, cellWidth, cellHeight);
        }
        repaint();
    }
    
    /*
     * Paint component
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCell(g);
        drawPlayers(g);
        drawDoorAndLabel(g);
        
    }
}


