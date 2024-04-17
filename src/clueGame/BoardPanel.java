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
    private int cellWidth;
    private int cellHeight;
    private boolean roomTarget = false;
    private char roomName;
    
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
        // Draw board cells
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumColumns(); col++) {
                BoardCell cell = board.getCell(row, col);
                cell.draw(g, cellWidth, cellHeight);
                if (cell.getisTarget() && cell.isRoom()) {
                	roomTarget = true;
                	roomName = cell.getInitial();
                }
            }
        }             
        repaint();
    }
    
    /*
     * Draw doors and room labels
     */
    public void drawDoorAndLabel(Graphics g) { 
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
                	Color purple = new Color(148, 150, 211);
                    g.setColor(purple);
                    break;
                case "Pink":
                    g.setColor(Color.PINK);
                    break;
                case "Orange":
                	Color neonOrange = new Color(255, 95, 31);
                    g.setColor(neonOrange);
                    break;
                case "Blue":
                    g.setColor(Color.CYAN);
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
     * Calculates height of cell
     */
	private int calcCellHeight() {
		return getHeight() / board.getNumRows();
	}
	
	/*
	 * Calculates width of cell
	 */
	private int calcCellSize() {
		return getWidth() / board.getNumColumns();
	}
    
    /*
     * Paint component
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCell(g);
        drawPlayers(g);
        drawDoorAndLabel(g);
		cellWidth = calcCellSize();
		cellHeight = calcCellHeight();
        
    }
}


