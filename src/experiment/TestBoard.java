/* TestBoard contains the board. It contains methods so that the tests work but do not pass.
 * Danny Nguyen and Jordan Lam
 * 2/26/24
 */

package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
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
				if ((row + 1) <= rows) { // below neighbor
					cell.addAdjacency(grid[row+1][column]);
				}
				if ((column + 1) <= columns) { // right neighbor
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
		this.calculateAdjacencies(rows, columns);
	}
	
	// calculates legal targets for a move from startCell of length pathlength
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	// returns the cell from the board at row, col.
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
		
	}
	
	// gets the targets last created by calcTargets()
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
}
