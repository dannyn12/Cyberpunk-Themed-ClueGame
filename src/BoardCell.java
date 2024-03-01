/*
 * Authors: Jordan Lam, Danny Nguyen
 * BoardCell class this gives information about each invidual cell like if it is a room or is occupied by a player
 * this room also has adjacenyList that shows its adjacent neighbors
 */

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int column, row;
	private Set<BoardCell> adjacencyList;
	private boolean isRoom;
	private boolean isOccupied;
	
	public BoardCell(int column, int row) {
		super();
		this.column = column;
		this.row = row;
		this.adjacencyList = new HashSet<>();
		this.isRoom = false;
		this.isOccupied = false;
	}
	
	//  adds a cell to this cells adjacency list
	public void addAdjacency(BoardCell cell) {
		adjacencyList.add(cell);
	}
	
	
	public Set<BoardCell> getAdjList() {
		return adjacencyList;
	}
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean isRoom() {
		return isRoom;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}

}

