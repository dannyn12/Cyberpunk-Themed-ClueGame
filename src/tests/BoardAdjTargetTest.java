/* BoardAdjTargetTest contains the test for the adjacency and targets for a cell in rooms and walkways.
 * Authors: Danny Nguyen and Jordan Lam
 * 3/8/24
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// test Slum district with secret passage way to cybernetics clinic
		Set<BoardCell> testList = board.getAdjList(23, 2); 
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(20, 3))); // door
		assertTrue(testList.contains(board.getCell(23, 6))); // door
		assertTrue(testList.contains(board.getCell(3, 22))); // secret passage to cybernetics clinic
		
		// test Neon Allay
		testList = board.getAdjList(2, 3);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(2, 6))); // door 
		assertTrue(testList.contains(board.getCell(3, 6))); // door
		
		// test Data core
		testList = board.getAdjList(25, 12);
		assertEquals(5, testList.size());
		assertTrue(testList.contains(board.getCell(23, 7))); // door
		assertTrue(testList.contains(board.getCell(20, 11))); // door
		assertTrue(testList.contains(board.getCell(20, 14))); // door
		assertTrue(testList.contains(board.getCell(23, 18))); // door
		assertTrue(testList.contains(board.getCell(24, 18))); // door
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are DARK GREY on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(20, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(19, 3)));
		assertTrue(testList.contains(board.getCell(20, 2)));
		assertTrue(testList.contains(board.getCell(20, 4)));
		assertTrue(testList.contains(board.getCell(23, 2)));
		

		testList = board.getAdjList(2, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 7)));
		assertTrue(testList.contains(board.getCell(1, 6)));
		assertTrue(testList.contains(board.getCell(3, 6)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		
		testList = board.getAdjList(23, 18);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(22, 18)));
		assertTrue(testList.contains(board.getCell(24, 18)));
		assertTrue(testList.contains(board.getCell(25, 12)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are DARK ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(27, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(26, 6)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(20, 17);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(20, 16)));
		assertTrue(testList.contains(board.getCell(20, 18)));
		assertTrue(testList.contains(board.getCell(19, 17)));

		// Test adjacent to walkways
		testList = board.getAdjList(19, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(18, 7)));
		assertTrue(testList.contains(board.getCell(20, 7)));
		assertTrue(testList.contains(board.getCell(19, 6)));
		assertTrue(testList.contains(board.getCell(19, 8)));

		// Test next to closet
		testList = board.getAdjList(12,17);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(12, 18)));
		assertTrue(testList.contains(board.getCell(11, 17)));
		assertTrue(testList.contains(board.getCell(13, 17)));
		
	
	}
	
	// Tests out of room center, 1, 3 and 4
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsCorperateTower() {
		// test a roll of 1
		board.calcTargets(board.getCell(2, 13), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(5, 12)));
		assertTrue(targets.contains(board.getCell(5, 13)));
		assertTrue(targets.contains(board.getCell(22, 22)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(2, 13), 3);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(6, 11)));
		assertTrue(targets.contains(board.getCell(6, 13)));	
		assertTrue(targets.contains(board.getCell(7, 12)));
		assertTrue(targets.contains(board.getCell(5, 10)));
		assertTrue(targets.contains(board.getCell(5, 14)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(2, 13), 4);
		targets= board.getTargets();
		assertEquals(19, targets.size());
		assertTrue(targets.contains(board.getCell(7, 11)));
		assertTrue(targets.contains(board.getCell(7, 13)));	
		assertTrue(targets.contains(board.getCell(5, 9)));
		assertTrue(targets.contains(board.getCell(5, 15)));	
	}
	
	@Test
	public void testTargetsInUndergroundBunker() {
		// test a roll of 1
		board.calcTargets(board.getCell(15, 22), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(12, 23)));
		assertTrue(targets.contains(board.getCell(15, 19)));	
		assertTrue(targets.contains(board.getCell(17, 21)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(15, 22), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(12, 21)));
		assertTrue(targets.contains(board.getCell(19, 21)));	
		assertTrue(targets.contains(board.getCell(17, 19)));
		assertTrue(targets.contains(board.getCell(13, 19)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(15, 22), 4);
		targets= board.getTargets();
		assertEquals(19, targets.size()); 
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(18, 21)));	
		assertTrue(targets.contains(board.getCell(19, 20)));
		assertTrue(targets.contains(board.getCell(16, 19)));	
	}
	
	// Tests out of room center J, 
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(9, 19), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(8, 19)));
		assertTrue(targets.contains(board.getCell(10, 19)));	
		assertTrue(targets.contains(board.getCell(9, 18)));	
		assertTrue(targets.contains(board.getCell(8, 22)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(9, 19), 3);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(8, 22)));
		assertTrue(targets.contains(board.getCell(6, 19)));
		assertTrue(targets.contains(board.getCell(12, 19)));	
		assertTrue(targets.contains(board.getCell(8, 19)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(9, 19), 4);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(8, 22)));
		assertTrue(targets.contains(board.getCell(5, 19)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(12, 22), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(12, 21)));
		assertTrue(targets.contains(board.getCell(12, 23)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(12, 22), 3);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(8, 22)));
		assertTrue(targets.contains(board.getCell(15, 22)));
		assertTrue(targets.contains(board.getCell(12, 19)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(12, 22), 4);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(8, 22)));
		assertTrue(targets.contains(board.getCell(15, 22)));
		assertTrue(targets.contains(board.getCell(12, 18)));
		assertTrue(targets.contains(board.getCell(11, 19)));
		assertTrue(targets.contains(board.getCell(13, 19)));
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(7, 6), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(7, 7)));
		assertTrue(targets.contains(board.getCell(6, 6)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(7, 6), 3);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(4, 6)));
		assertTrue(targets.contains(board.getCell(7, 9)));
		assertTrue(targets.contains(board.getCell(7, 5)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(7, 6), 4);
		targets= board.getTargets();
		assertEquals(20, targets.size());
		assertTrue(targets.contains(board.getCell(3, 6)));
		assertTrue(targets.contains(board.getCell(7, 2)));
		assertTrue(targets.contains(board.getCell(9, 4)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 3 blocked 2 down
		board.getCell(8, 3).setOccupied(true);
		board.calcTargets(board.getCell(7, 3), 3);
		board.getCell(8, 3).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(9, 2)));
		assertTrue(targets.contains(board.getCell(9, 4)));	
		assertFalse( targets.contains( board.getCell(8, 3)));
		assertFalse( targets.contains( board.getCell(15, 3)));
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(3, 3).setOccupied(true);
		board.getCell(2, 6).setOccupied(true);
		board.calcTargets(board.getCell(3, 6), 1);
		board.getCell(3, 3).setOccupied(false);
		board.getCell(2, 6).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(2, 3)));	
		assertTrue(targets.contains(board.getCell(4, 6)));	
		assertTrue(targets.contains(board.getCell(3, 7)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(4, 19).setOccupied(true);
		board.getCell(6, 21).setOccupied(true);
		board.calcTargets(board.getCell(3, 22), 2);
		board.getCell(4, 19).setOccupied(false);
		board.getCell(6, 21).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(3, 18)));
		assertTrue(targets.contains(board.getCell(2, 19)));	
		assertTrue(targets.contains(board.getCell(23, 2)));

	}
}
