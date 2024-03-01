/* Board class that holds the information on the game board 
 * this class will calculate adjacenies and give critical information to determining movement around the board
 * Authors: Danny Nguyen and Jordan Lam
 * 2/26/24
 */

import java.util.HashSet;
import java.util.Set;

public class Board {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	private void calculateAdjacencies(int rows, int columns) {
		for (int row = 0; row < rows; row++) { // going to each cell 
			for (int column = 0; column < columns; column++) {
				BoardCell cell = grid[row][column];
				if ((row - 1) >= 0) { // above neighbor
					cell.addAdjacency(grid[row-1][column]);
				}
				if ((column - 1) >= 0) { // left neighbor
					cell.addAdjacency(grid[row][column-1]);
				}
				if ((row + 1) < rows) { // below neighbor
					cell.addAdjacency(grid[row+1][column]);
				}
				if ((column + 1) < columns) { // right neighbor
					cell.addAdjacency(grid[row][column+1]);
				}
				
			}
		}
	}
	
	public Board(int rows, int columns) {
		this.grid = new BoardCell[rows][columns];
		for (int row = 0; row < rows; row++) { // adding cells to grid
			for (int column = 0; column < columns; column++) {
				this.grid[row][column] = new BoardCell(row, column);
			}
		}
		this.targets = new HashSet<>();
		this.visited = new HashSet<>();
		this.calculateAdjacencies(rows, columns);
	}
	
	// calculates legal targets for a move from startCell of length pathlength
	public void calcTargets(BoardCell startCell, int pathlength) {
		// put starting cell visited
		visited.add(startCell);
		// loop through the start cells adjacency list
		for (BoardCell adjCell: startCell.getAdjList()) {
			// if it is visited continue
			if (visited.contains(adjCell) || adjCell.isOccupied() == true) {
				continue;
			}
			// add cell to visited if not
			visited.add(adjCell);
			
			// if pathlength is 1 or there is a room it is a target
			if (pathlength == 1 || adjCell.isRoom() == true) {
				// if cell is occupied it is not
				if (adjCell.isOccupied() == false) {
					targets.add(adjCell);
				}
				
			}
			// go next adjCell cell
			else {
				calcTargets(adjCell, pathlength - 1);
			}
			
			// remove adjCell from visited set
			visited.remove(adjCell);
			
		}
	}
	
	// returns the cell from the board at row, col.
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
		
	}
	
	// gets the targets last created by calcTargets()
	public Set<BoardCell> getTargets() {
		return targets;
	}
}