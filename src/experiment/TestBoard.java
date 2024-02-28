/* TestBoard contains the board and calculates the adjacency for each cell.
 * Danny Nguyen and Jordan Lam
 * 2/26/24
 */

package experiment;

import java.util.HashSet;
import java.util.Set;

// 
public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	/*
	 * Calculates adjacency of a each cell in the grid.
	 */
	private void calculateAdjacencies(int rows, int columns) {
		for (int row = 0; row < rows; row++) { // going to each cell 
			for (int column = 0; column < columns; column++) {
				TestBoardCell cell = grid[row][column];
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
	
	public TestBoard(int rows, int columns) {
		this.grid = new TestBoardCell[rows][columns];
		for (int row = 0; row < rows; row++) { // adding cells to grid
			for (int column = 0; column < columns; column++) {
				this.grid[row][column] = new TestBoardCell(row, column);
			}
		}
		this.targets = new HashSet<>();
		this.visited = new HashSet<>();
		this.calculateAdjacencies(rows, columns);
	}
	
	/*
	 * Calculates legal targets for a move from startCell of length pathlength
	 */
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		// put starting cell visited
		visited.add(startCell);
		// loop through the start cells adjacency list
		for (TestBoardCell adjCell: startCell.getAdjList()) {
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
			
			// romove adjCell from visited set
			visited.remove(adjCell);
			
		}
	}
	
	/*
	 * Returns the cell from the board at row, col.
	 */
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
		
	}
	
	/*
	 * Returns the cell from the board at row, col.
	 */
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
}
