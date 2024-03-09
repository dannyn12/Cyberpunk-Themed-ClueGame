/* Board class that holds the information on the game board 
 * this class will calculate adjacenies and give critical information to determining movement around the board
 * Authors: Danny Nguyen and Jordan Lam
 * 2/26/24
 */
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private static Board theInstance = new Board();


	/*
	 *  Method calculate the adjacency of each cell in the board
	 */
	private void calculateAdjacencies(int rows, int cols) {
		for (int row = 0; row < rows; row++) { // going to each cell 
			for (int col = 0; col < cols; col++) {
				BoardCell cell = grid[row][col];
				if (cell.getInitial() == 'W' && !cell.isDoorway()) { // for walkways
					if ((row - 1) >= 0) { // above neighbor
						if(grid[row-1][col].getInitial() == 'W') { // connecting adjacent walkways 
							cell.addAdj(grid[row-1][col]);
						} else if (grid[row-1][col].isDoorway()) { // connecting walkways to doors
							cell.addAdj(grid[row-1][col]);
						}
					}
					if ((col - 1) >= 0) { // left neighbor
						if(grid[row][col-1].getInitial() == 'W') { 
							cell.addAdj(grid[row][col-1]);
						} else if (grid[row][col-1].isDoorway()) {
							cell.addAdj(grid[row][col-1]);
						}
					}
					if ((row + 1) < rows) { // below neighbor
						if(grid[row+1][col].getInitial() == 'W') { 
							cell.addAdj(grid[row+1][col]);
						} else if (grid[row-1][col].isDoorway()) {
							cell.addAdj(grid[row+1][col]);
						}
					}
					if ((col + 1) < cols) { // right neighbor
						if(grid[row][col+1].getInitial() == 'W') { 
							cell.addAdj(grid[row][col+1]);
						} else if (grid[row][col-1].isDoorway()) {
							cell.addAdj(grid[row][col+1]);
						}
					}
				} 
				else if (cell.isDoorway()) { // for doorways 
					if ((row - 1) >= 0 && cell.getDoorDirection() != DoorDirection.UP) { // above neighbor
						if(grid[row-1][col].getInitial() == 'W') { // connecting adjacent walkways 
							cell.addAdj(grid[row-1][col]);
						} else if (grid[row-1][col].isDoorway()) { // connecting door to adjacent doors
							cell.addAdj(grid[row-1][col]);
						}
					}
					if ((col - 1) >= 0 && cell.getDoorDirection() != DoorDirection.LEFT) { // left neighbor
						if(grid[row][col-1].getInitial() == 'W') { 
							cell.addAdj(grid[row][col-1]);
						} else if (grid[row][col-1].isDoorway()) {
							cell.addAdj(grid[row][col-1]);
						}
					}
					if ((row + 1) < rows && cell.getDoorDirection() != DoorDirection.DOWN) { // below neighbor
						if(grid[row+1][col].getInitial() == 'W') { 
							cell.addAdj(grid[row+1][col]);
						} else if (grid[row-1][col].isDoorway()) {
							cell.addAdj(grid[row+1][col]);
						}
					}
					if ((col + 1) < cols && cell.getDoorDirection() != DoorDirection.RIGHT) { // right neighbor
						if(grid[row][col+1].getInitial() == 'W') { 
							cell.addAdj(grid[row][col+1]);
						} else if (grid[row][col-1].isDoorway()) {
							cell.addAdj(grid[row][col+1]);
						}
					}
					
					this.calculateAdjDoorRoom(cell, rows, cols, row, col);
				}
				else if (cell.isRoomCenter()) { // for rooms 
					char initial = cell.getInitial();
					Room room = this.roomMap.get(initial);
					List<BoardCell> doorList = room.getDoorList();
					for (BoardCell door: doorList) {
						cell.addAdj(door);
					}
					if(room.hasSecretPassage()) {
						cell.addAdj(room.getSecretPassage().get(0));
					}
						
				}
				
			}
		}
	}
	
	/*
	 * helper function for calculateadj function this function will add the room center to the doorway
	 */
	private void calculateAdjDoorRoom(BoardCell cell, int rows, int cols, int row, int col) {
		if ((row - 1) >= 0 && cell.getDoorDirection() == DoorDirection.UP) { // above neighbor
			char initial = grid[row-1][col].getInitial();
			Room room = this.roomMap.get(initial);
			cell.addAdj(room.getCenterCell());
		} else if ((col - 1) >= 0 && cell.getDoorDirection() == DoorDirection.LEFT) { // left neighbor
			char initial = grid[row][col-1].getInitial();
			Room room = this.roomMap.get(initial);
			cell.addAdj(room.getCenterCell());
		} else if ((row + 1) < rows && cell.getDoorDirection() == DoorDirection.DOWN) { // below neighbor
			char initial = grid[row+1][col].getInitial();
			Room room = this.roomMap.get(initial);
			cell.addAdj(room.getCenterCell());
		} else  { // right neighbor
			char initial = grid[row][col+1].getInitial();
			Room room = this.roomMap.get(initial);
			cell.addAdj(room.getCenterCell());
		}
	}

	/*
	 * constructor is private to ensure only one can be created
	 */
	private Board() {
		super() ;
	}

	/*
	 * this method returns the only Board
	 */
	public static Board getInstance() {
		return theInstance;
	}

	/*
	 * initialize the board 
	 */
	public void initialize() 
	{
		try {
			this.loadSetupConfig();
			this.loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Recursive helper function to calcTargets that find the targets
	 */
	private void findTarget(BoardCell cell, int length) {
		// loop through the start cells adjacency list
		for (BoardCell adjCell: cell.getAdjList()) {
			// if it is visited continue
			if (visited.contains(adjCell) || (adjCell.isOccupied() && !adjCell.isRoomCenter())) {
				continue;
			}
			// add cell to visited if not
			visited.add(adjCell);

			// if pathlength is 1 or there is a room it is a target
			if (length == 1 || adjCell.isRoomCenter()) {
				// if cell is occupied it is not
				targets.add(adjCell);
			}
			// go next adjCell cell
			else {
				findTarget(adjCell, length - 1);
			}

			// remove adjCell from visited set
			visited.remove(adjCell);

		}
	}

	/*
	 * Calculates legal targets for a move from startCell of length pathlength
	 */
	public void calcTargets(BoardCell startCell, int pathlength) {
		targets = new HashSet<>();
		visited = new HashSet<>();
		// put starting cell visited
		visited.add(startCell);
		findTarget(startCell, pathlength);
		
	}
	
	/*
	 * Method reads Setup File and sets game up
	 */
	public void loadSetupConfig() throws BadConfigFormatException {
		try {
			SetupGame();
		} catch (FileNotFoundException e ) {
			throw new BadConfigFormatException("Setup Config file not found.");
		}
	}
	
	/*
	 * Reads setupConfigFile and set up game
	 */
	private void SetupGame() throws FileNotFoundException {
		FileReader file = new FileReader("src/data/" + this.setupConfigFile);
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String[] row = scanner.nextLine().split(", ");
			if (row.length >= 3 && (row[0].equals("Room") || row[0].equals("Space"))) {
				char inital = row[2].charAt(0);
				Room room = new Room();
				room.setName(row[1]);
				this.roomMap.put(inital, room);
			}
		}
	}

	/*
	 * Initializes the layout of the board
	 */
	public void loadLayoutConfig() throws BadConfigFormatException {
		// ArrayList of string
		List<String> cellList = new ArrayList<>();

		try {
			// create board
			setupLayout(cellList); // read layout file and add each cell initial in layout file to cellList
			int cellListLocation = 0; // list index
			this.grid = new BoardCell[this.numRows][this.numColumns];
			
			try {
				createBoard(cellList, cellListLocation);
			} catch (IndexOutOfBoundsException e){
				throw new BadConfigFormatException("LayoutConfig invalid");
			}

		} catch (FileNotFoundException e){
			throw new BadConfigFormatException("Setup Config file not found.");
		}
	}
	
	/*
	 * Sets up the layout of the board
	 */
	private void setupLayout(List<String> cellList) throws FileNotFoundException {
		FileReader file = new FileReader("src/data/" + this.layoutConfigFile);
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String[] row = scanner.nextLine().split(",");
			for (String cell: row) {
				cellList.add(cell);
			}
		}
		scanner.close();
	}
	
	/*
	 * finds the doors and secret passage of each room
	 */
	private void findDoorsAndPassage() {
		// parse through layout
		for (int row = 0; row < this.numRows; row++) {
			for (int col = 0; col < this.numColumns; col++) {
				BoardCell cell = this.grid[row][col];
				// add doorway to room door list
				if (cell.isDoorway()) {
					if ( cell.getDoorDirection() == DoorDirection.UP) {
						char initial = this.grid[row-1][col].getInitial();
						this.roomMap.get(initial).addDoor(cell);;
					}
					else if ( cell.getDoorDirection() == DoorDirection.LEFT) {
						char initial = this.grid[row][col-1].getInitial();
						this.roomMap.get(initial).addDoor(cell);;
					}
					else if ( cell.getDoorDirection() == DoorDirection.RIGHT) {
						char initial = this.grid[row][col+1].getInitial();
						this.roomMap.get(initial).addDoor(cell);;
					}
					else if ( cell.getDoorDirection() == DoorDirection.DOWN) {
						char initial = this.grid[row+1][col+1].getInitial();
						this.roomMap.get(initial).addDoor(cell);;
					}
					
				}
				// get secret passage and add that to room class
				else if (cell.isSecretPassage()) {
					char initial = this.grid[row][col].getInitial();
					Room currentRoom = this.roomMap.get(initial);
					// get center of room secret passage leads too
					char roomTo = this.grid[row][col].getSecretPassage();
					Room connectedRoom = this.roomMap.get(roomTo);	
					currentRoom.addSecretPassage(connectedRoom.getCenterCell());
					currentRoom.hasSecretPassage(true);
				}
			}
		}
	}
	
	
	/*
	 * Creates the board
	 */
	private void createBoard(List<String> cellList, int cellListLocation) throws BadConfigFormatException {
		for (int row = 0; row < this.numRows; row++) { // adding cells to grid
			for (int col = 0; col < this.numColumns; col++) {
				this.grid[row][col] = new BoardCell(row, col);
				String cell = cellList.get(cellListLocation);

				// if cell is more than one character
				char charAtIndex1 = cell.charAt(0);
				if (cell.length() > 1) {
					// set initial
					this.grid[row][col].setInitial(charAtIndex1);
					// check if it is a label
					String initial = String.valueOf(charAtIndex1);
					if (cell.equals(initial + "#")) {
						this.roomMap.get(charAtIndex1).setLabelCell(this.grid[row][col]);
						this.grid[row][col].setRoomLabel(true);
						this.grid[row][col].setIsRoom(true);
					}
					// check if it is a center
					else if (cell.equals(initial + "*")) {
						this.roomMap.get(charAtIndex1).setCenterCell(this.grid[row][col]);
						this.grid[row][col].setRoomCenter(true);
						this.grid[row][col].setIsRoom(true);
					}	
					// check if it is a door also set the door direction
					else if (cell.equals(initial + "^")) {
						this.grid[row][col].setDoorway(true);
						this.grid[row][col].setDoorDirection(DoorDirection.UP);
					}
					else if (cell.equals(initial + ">")) {
						this.grid[row][col].setDoorway(true);
						this.grid[row][col].setDoorDirection(DoorDirection.RIGHT);
					}
					else if (cell.equals(initial + "v")) {
						this.grid[row][col].setDoorway(true);
						this.grid[row][col].setDoorDirection(DoorDirection.DOWN);
					}
					else if (cell.equals(initial + "<")) {
						this.grid[row][col].setDoorway(true);
						this.grid[row][col].setDoorDirection(DoorDirection.LEFT);
					}
					// check if it is a secret passage way
					else {
						this.grid[row][col].setSecretPassage(cell.charAt(1));
						this.grid[row][col].setIsSecretPassage(true);
						this.grid[row][col].setIsRoom(true);
					}
				}
				// if cell is one character
				else {
					// if cell is a unused space
					if (cell.equals("X")) {
						this.grid[row][col].setInitial(charAtIndex1);
						this.grid[row][col].setOccupied(true);
					}
					// if cell is a walkway
					else if (cell.equals("W")) {
						this.grid[row][col].setInitial(charAtIndex1);
					}
					else {
						if (roomMap.containsKey(charAtIndex1)) {
							this.grid[row][col].setInitial(charAtIndex1);
							this.grid[row][col].setIsRoom(true);
						} 
						else {
							throw new BadConfigFormatException(cell + " is not in the legend");
						}
					}
				}

				// increment counter
				cellListLocation += 1;
			}
		}
		
		this.targets = new HashSet<>();
		this.visited = new HashSet<>();
		this.findDoorsAndPassage();
		this.calculateAdjacencies(this.numRows, this.numColumns);
	}
	

	/*
	 * Sets layout to layoutConfigFile and setup setupConfigFile. Method also gets the size of
	 * the board.
	 */
	public void setConfigFiles(String layout, String setup) {
		this.layoutConfigFile = layout;
		this.setupConfigFile = setup;
		this.roomMap = new HashMap<>();
		getBoardSize(layout);

	}
	
	/*
	 * Gets the size of the board
	 */
	private void getBoardSize(String layout) {
		// get size of board
		int rows = 0;
		int cols = 0;
		try {
			FileReader file = new FileReader("src/data/" + layout);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String[] row = scanner.nextLine().split(",");
				rows += 1;
				cols = row.length;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.numRows = rows;
		this.numColumns = cols;
	}
	
	public Set<BoardCell> getAdjList(int row, int col){
		BoardCell cell = this.grid[row][col];
		return cell.getAdjList();
	}

	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public static Board getTheInstance() {
		return theInstance;
	}

	public Room getRoom(char initial) {
		return roomMap.get(initial);
	}

	public Room getRoom(BoardCell cell) {
		char initial = cell.getInitial();
		return roomMap.get(initial);
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	
}