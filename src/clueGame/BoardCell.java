/*
 * Authors: Jordan Lam, Danny Nguyen
 * BoardCell class this gives information about each invidual cell like if it is a room or is occupied by a player
 * this room also has adjacenyList that shows its adjacent neighbors
 */
package clueGame;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int col, row;
	private Set<BoardCell> adjList;
	private boolean isOccupied;
	private char initial;
	private DoorDirection DoorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private boolean isDoorway;
	private boolean isRoom;
	
	public BoardCell(int col, int row) {
		super();
		this.col = col;
		this.row = row;
		this.adjList = new HashSet<>();
		this.isRoom = false;
		this.isOccupied = false;
	}
	
	//  adds a cell to this cells adjacency list
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	
	
	public Set<BoardCell> getAdjList() {
		return adjList;
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
	
	public boolean isDoorway() {
		return isDoorway;
	}

	public DoorDirection getDoorDirection() {
		return DoorDirection;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public char getInitial() {
		return initial;
	}
	
	

}

