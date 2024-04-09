/* BoardCell class this gives information about each invidual cell like if it is a room or is occupied by a player
 * this room also has adjacenyList that shows its adjacent neighbors
 * Authors: Jordan Lam, Danny Nguyen
 */
package clueGame;
import java.awt.Color;
import java.awt.Graphics;
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
	
	/*
	 * constructor for BoardCell class 
	 * Boards are init with nothing active
	 */
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
	
	/*
	 * this will add a cell to this cells adjList
	 */
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	
	/*
	 * Draws players & rooms & doors on board
	 */
	public void draw(Graphics g, int cellSize) {
	    int x = col * cellSize;
	    int y = row * cellSize;
	    if (initial == 'W') {
		    g.setColor(Color.CYAN); 
		    g.fillRect(x, y, cellSize, cellSize);
		    g.setColor(Color.BLACK);
		    g.drawRect(x, y, cellSize, cellSize); 
	    } else if (initial == 'X'){
		    g.setColor(Color.MAGENTA); 
		    g.fillRect(x, y, cellSize, cellSize); 
	    } else if (isRoom) {
	    	g.setColor(Color.YELLOW);
	    	g.fillRect(x, y, cellSize, cellSize); 
	    }
	}
	
	public void drawLabelDoor(Graphics g, int cellSize) {
	    int x = col * cellSize;
	    int y = row * cellSize;
	    if (isDoorway) {
	    	if(doorDirection == DoorDirection.DOWN) {
		    	g.setColor(Color.BLACK);
		    	g.fillRect(x, y + cellSize, cellSize, 5);
	    	} else if (doorDirection == DoorDirection.LEFT) {
	    		g.setColor(Color.BLACK);
		    	g.fillRect(x-5, y,  5, cellSize);
	    	} else if (doorDirection == DoorDirection.RIGHT) {
	    		g.setColor(Color.BLACK);
		    	g.fillRect(x+cellSize, y,  5, cellSize);
	    	} else {
	    		g.setColor(Color.BLACK);
		    	g.fillRect(x, y-5,  cellSize, 5);
	    	}
	    } else if (roomLabel) {
	    	Board board = Board.getInstance();
	    	Room room = board.getRoom(initial);
	    	String name = room.getName();
	    	g.drawString(name, x, y);
	    }
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
	
	public void getLocation() {
		System.out.println(this.row + " "+ this.col);
	}


}