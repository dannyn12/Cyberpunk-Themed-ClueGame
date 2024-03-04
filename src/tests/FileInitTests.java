/* FileInitTests contains test that test door direction, room labals, board dimensions, number of doorways, and rooms are correct.
 * Authors: Danny Nguyen and Jordan Lam
 * 3/1/24
 */

package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 28;
	public static final int NUM_COLUMNS = 27;

	// since singleton board class
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	// Test if room labels are correct.
	@Test
	public void testRoomLabels() {
		// testing all rooms are here and correct
		assertEquals("Neon Alley", board.getRoom('A').getName() );
		assertEquals("Cybernetics Clinic", board.getRoom('C').getName() );
		assertEquals("Corporate Tower", board.getRoom('T').getName() );
		assertEquals("Cyberspace Nexus", board.getRoom('N').getName() );
		assertEquals("Data Core", board.getRoom('D').getName() );
		assertEquals("Slum District", board.getRoom('S').getName() );
		assertEquals("Quantum Laboratory", board.getRoom('Q').getName() );
		assertEquals("Underground Bunker", board.getRoom('U').getName() );
		assertEquals("Techno Junkyard", board.getRoom('J').getName() );
	}
	
	// Test if board dimensions are correct
	@Test
	public void testBoardDimensions() {
		// testing dimensions
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		// Neon Alley walkways
		BoardCell cell = board.getCell(2, 6);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(3, 6);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		// Corporate Tower walkways
		cell = board.getCell(5, 12);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(5, 13);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		
		// Cyberspace Nexus walkway
		cell = board.getCell(3, 19);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(4, 19);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(6, 21);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		
		// Techno Junkyard walkways
		cell = board.getCell(9, 19);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(12, 21);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());

		//Underground Bunker walkways
		cell = board.getCell(15, 19);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(12, 23);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		cell = board.getCell(17, 21);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());	
		
		//Quantum Laboratory walkways
		cell = board.getCell(20, 20);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		cell = board.getCell(20, 23);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		cell = board.getCell(21, 18);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		
		//Data core walkways
		cell = board.getCell(20, 14);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		cell = board.getCell(20, 11);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		cell = board.getCell(23, 18);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(24, 18);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(23, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		
		//Cyberspace Nexus walkways
		cell = board.getCell(18, 3);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(18, 4);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(9, 3);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());	
		cell = board.getCell(14, 6);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());	
		
		// Cybernetics Clinic walkways
		cell = board.getCell(3, 19);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(4, 19);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(6, 21);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		
		// Test that walkways are not doors
		cell = board.getCell(14, 8);
		assertFalse(cell.isDoorway());
	}
	

	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(26, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRooms() {
		// just test all standard room location
		BoardCell cell = board.getCell(3, 1);
		Room room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Neon Alley" ) ;
		assertFalse( cell.isLabel() );
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isDoorway());
		
		BoardCell cell2 = board.getCell(3, 11);
		Room room2 = board.getRoom( cell2 ) ;
		assertTrue( room2 != null );
		assertEquals( room2.getName(), "Corporate Tower" ) ;
		assertFalse( cell2.isLabel() );
		assertFalse( cell2.isRoomCenter() );
		assertFalse( cell2.isDoorway());
		
		BoardCell cell3 = board.getCell(3, 21);
		Room room3 = board.getRoom( cell3 ) ;
		assertTrue( room3 != null );
		assertEquals( room3.getName(), "Cybernetics Clinic" ) ;
		assertFalse( cell3.isLabel() );
		assertFalse( cell3.isRoomCenter() );
		assertFalse( cell3.isDoorway());

		BoardCell cell4 = board.getCell(10, 3);
		Room room4 = board.getRoom( cell4 ) ;
		assertTrue( room4 != null );
		assertEquals( room4.getName(), "Cyberspace Nexus" ) ;
		assertFalse( cell4.isLabel() );
		assertFalse( cell4.isRoomCenter() );
		assertFalse( cell4.isDoorway());

		BoardCell cell5 = board.getCell(10, 21);
		Room room5 = board.getRoom( cell5 ) ;
		assertTrue( room5 != null );
		assertEquals( room5.getName(), "Techno Junkyard" ) ;
		assertFalse( cell5.isLabel() );
		assertFalse( cell5.isRoomCenter() );
		assertFalse( cell5.isDoorway());

		BoardCell cell6 = board.getCell(15, 21);
		Room room6 = board.getRoom( cell6 ) ;
		assertTrue( room6 != null );
		assertEquals( room6.getName(), "Underground Bunker" ) ;
		assertFalse( cell6.isLabel() );
		assertFalse( cell6.isRoomCenter() );
		assertFalse( cell6.isDoorway());

		BoardCell cell7 = board.getCell(23, 0);
		Room room7 = board.getRoom( cell7 ) ;
		assertTrue( room7 != null );
		assertEquals( room7.getName(), "Slum District" ) ;
		assertFalse( cell7.isLabel() );
		assertFalse( cell7.isRoomCenter() );
		assertFalse( cell7.isDoorway());

		BoardCell cell8 = board.getCell(23, 10);
		Room room8 = board.getRoom( cell8 ) ;
		assertTrue( room8 != null );
		assertEquals( room8.getName(), "Data Core" ) ;
		assertFalse( cell8.isLabel() );
		assertFalse( cell8.isRoomCenter() );
		assertFalse( cell8.isDoorway());

		BoardCell cell9 = board.getCell(23, 21);
		Room room9 = board.getRoom( cell9 ) ;
		assertTrue( room9 != null );
		assertEquals( room9.getName(), "Quantum Laboratory" ) ;
		assertFalse( cell9.isLabel() );
		assertFalse( cell9.isRoomCenter() );
		assertFalse( cell9.isDoorway());
		// this is a label cell to test
		cell = board.getCell(2, 13);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Corporate Tower" ) ;
		assertTrue( cell.isLabel() );
		assertTrue( room.getLabelCell() == cell );
		// this is a room center cell to test
		cell = board.getCell(9,22);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Techno Junkyard" ) ;
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );
		// this is a secret passage test
		cell = board.getCell(1, 15);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Corporate Tower" ) ;
		assertTrue( cell.getSecretPassage() == 'Q' );
	}

}
