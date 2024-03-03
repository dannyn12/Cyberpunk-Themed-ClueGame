/* Board class that holds the information on the game board 
 * this class will calculate adjacenies and give critical information to determining movement around the board
 * Authors: Danny Nguyen and Jordan Lam
 * 2/26/24
 */
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
	
	
	// method calculate the adjacencies  of each cell in the board
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
	
	// constructor is private to ensure only one can be created
	private Board() {
		super() ;
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize()
	{
		this.grid = new BoardCell[this.numRows][this.numColumns];
		for (int row = 0; row < this.numRows; row++) { // adding cells to grid
			for (int column = 0; column < this.numColumns; column++) {
				this.grid[row][column] = new BoardCell(row, column);
			}
		}
		this.targets = new HashSet<>();
		this.visited = new HashSet<>();
		this.calculateAdjacencies(this.numRows, this.numColumns);
		this.roomMap = new HashMap<>();
		
		// load layout and setup
		this.loadSetupConfig();
		this.loadLayoutConfig();
	}	

	// calculates legal targets for a move from startCell of length pathlength
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
	
	// TODO
	public void loadSetupConfig() {
	
	}
	
	// TODO
	public void loadLayoutConfig() {

		
	}
	
	/*
	 * Sets layout to layoutConfigFile and setup setupConfigFile. Method also gets the size of
	 * the board.
	 */
	public void setConfigFiles(String layout, String setup) {
		this.layoutConfigFile = layout;
		this.setupConfigFile = setup;
		
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
	
	// TODO OR FIX
	public Room getRoom(char initial) {
		Room room = new Room();
		roomMap.put(initial, room);
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