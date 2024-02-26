package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	
	public TestBoard(int rows, int columns) {
		this.grid = new TestBoardCell[rows][columns];
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				this.grid[row][column] = new TestBoardCell(row, column);
			}
		}
		this.targets = new HashSet<>();
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
		
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
}
