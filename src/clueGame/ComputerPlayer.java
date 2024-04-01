package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {
	
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}
	
	/*
	 * TODO
	 */
	public Solution createSuggestion() {
		return null;
	}
	
	/*
	 * TODO
	 */
	public BoardCell selectTarget(Set<BoardCell> targets) {
		return null;
	}

}
