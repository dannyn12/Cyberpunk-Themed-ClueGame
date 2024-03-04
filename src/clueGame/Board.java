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
	 *  method calculate the adjacencies  of each cell in the board
	 */
	private void calculateAdjacencies(int rows, int columns) {
		for (int row = 0; row < rows; row++) { // going to each cell 
			for (int column = 0; column < columns; column++) {
				BoardCell cell = grid[row][column];
				if ((row - 1) >= 0) { // above neighbor
					cell.addAdj(grid[row-1][column]);
				}
				if ((column - 1) >= 0) { // left neighbor
					cell.addAdj(grid[row][column-1]);
				}
				if ((row + 1) < rows) { // below neighbor
					cell.addAdj(grid[row+1][column]);
				}
				if ((column + 1) < columns) { // right neighbor
					cell.addAdj(grid[row][column+1]);
				}

			}
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
	 * calculates legal targets for a move from startCell of length pathlength
	 */
	public void calcTargets(BoardCell startCell, int pathlength) {
		// put starting cell visited
		visited.add(startCell);
		// loop through the start cells adjacency list
		for (BoardCell adjCell: startCell.getAdjList()) {
			// if it is visited continue
			if (visited.contains(adjCell) || adjCell.isOccupied() == true) {
				continue;
			}
			// add cell to visited if not
			visited.add(adjCell);

			// if pathlength is 1 or there is a room it is a target
			if (pathlength == 1 || adjCell.isRoom() == true) {
				// if cell is occupied it is not
				if (adjCell.isOccupied() == false) {
					targets.add(adjCell);
				}
			}
			// go next adjCell cell
			else {
				calcTargets(adjCell, pathlength - 1);
			}

			// remove adjCell from visited set
			visited.remove(adjCell);

		}
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
	 * Creates the board
	 */
	private void createBoard(List<String> cellList, int cellListLocation) throws BadConfigFormatException {
		for (int row = 0; row < this.numRows; row++) { // adding cells to grid
			for (int column = 0; column < this.numColumns; column++) {
				this.grid[row][column] = new BoardCell(row, column);
				String cell = cellList.get(cellListLocation);

				// if cell is more than one character
				char charAtIndex1 = cell.charAt(0);
				if (cell.length() > 1) {
					// set initial
					this.grid[row][column].setInitial(charAtIndex1);
					// check if it is a label
					String initial = String.valueOf(charAtIndex1);
					if (cell.equals(initial + "#")) {
						this.roomMap.get(charAtIndex1).setLabelCell(this.grid[row][column]);
						this.grid[row][column].setRoomLabel(true);
						this.grid[row][column].setIsRoom(true);
					}
					// check if it is a center
					else if (cell.equals(initial + "*")) {
						this.roomMap.get(charAtIndex1).setCenterCell(this.grid[row][column]);
						this.grid[row][column].setRoomCenter(true);
						this.grid[row][column].setIsRoom(true);
					}	
					// check if it is a door also set the door direction
					else if (cell.equals(initial + "^")) {
						this.grid[row][column].setDoorway(true);
						this.grid[row][column].setDoorDirection(DoorDirection.UP);
					}
					else if (cell.equals(initial + ">")) {
						this.grid[row][column].setDoorway(true);
						this.grid[row][column].setDoorDirection(DoorDirection.RIGHT);
					}
					else if (cell.equals(initial + "v")) {
						this.grid[row][column].setDoorway(true);
						this.grid[row][column].setDoorDirection(DoorDirection.DOWN);
					}
					else if (cell.equals(initial + "<")) {
						this.grid[row][column].setDoorway(true);
						this.grid[row][column].setDoorDirection(DoorDirection.LEFT);
					}
					// check if it is a secret passage way
					else {
						this.grid[row][column].setSecretPassage(cell.charAt(1));
						this.grid[row][column].setIsRoom(true);
					}
				}
				// if cell is one character
				else {
					// if cell is a unused space
					if (cell.equals("X")) {
						this.grid[row][column].setInitial(charAtIndex1);
						this.grid[row][column].setOccupied(true);
					}
					// if cell is a walkway
					else if (cell.equals("W")) {
						this.grid[row][column].setInitial(charAtIndex1);
					}
					else {
						if (roomMap.containsKey(charAtIndex1)) {
							this.grid[row][column].setInitial(charAtIndex1);
							this.grid[row][column].setIsRoom(true);
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
		int columns = 0;
		try {
			FileReader file = new FileReader("src/data/" + layout);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String[] row = scanner.nextLine().split(",");
				rows += 1;
				columns = row.length;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.numRows = rows;
		this.numColumns = columns;
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