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
	
	public TestBoard(int rows, int columns) {
		this.grid = new TestBoardCell[rows][columns];
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				this.grid[row][column] = new TestBoardCell(row, column);
			}
		}
		this.targets = new HashSet<>();
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
