package experiment;

import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	
	public TestBoard() {
		
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
