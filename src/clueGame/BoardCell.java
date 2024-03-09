/*
 * Authors: Jordan Lam, Danny Nguyen
 * BoardCell class this gives information about each invidual cell like if it is a room or is occupied by a player
 * this room also has adjacenyList that shows its adjacent neighbors
 * 3/2/24
 */
package clueGame;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int col, row;
	private Set<BoardCell> adjList;
	private boolean isOccupied;
	private char initial;
	private boolean isSecretPassage;
	private char secretPassage;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean isDoorway;
	private boolean isRoom;
	
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.adjList = new HashSet<>();
		this.isSecretPassage = false;
		this.doorDirection = DoorDirection.NONE;
		this.isRoom = false;
		this.isOccupied = false;
	}
	
	//  adds a cell to this cells adjacency list
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	
	/*
	 * Getters and setters
	 */
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
		return doorDirection;
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
	
	public boolean isSecretPassage() {
		return isSecretPassage;
	}
	
	public void setIsSecretPassage(boolean isSecretPassage) {
		this.isSecretPassage = isSecretPassage;
	}

	public boolean isRoomLabel() {
		return roomLabel;
	}
	
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public void setDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}

}