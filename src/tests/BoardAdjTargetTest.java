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
		// test Slum district with secret passage way to quantum laboratory
		Set<BoardCell> testList = board.getAdjList(24, 3); 
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(20, 3))); // door
		assertTrue(testList.contains(board.getCell(23, 6))); // door
		assertTrue(testList.contains(board.getCell(22, 22))); // secret passage to Quantum Laboratory
		
		// test Neon Allay
		testList = board.getAdjList(3, 3);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(2, 6))); // door 
		assertTrue(testList.contains(board.getCell(3, 6))); // door
		
		// test Data core
		testList = board.getAdjList(23, 12);
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
		assertEquals(5, testList.size());
		assertTrue(testList.contains(board.getCell(19, 3)));
		assertTrue(testList.contains(board.getCell(20, 2)));
		assertTrue(testList.contains(board.getCell(20, 4)));
		assertTrue(testList.contains(board.getCell(24, 3)));
		

		testList = board.getAdjList(2, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 7)));
		assertTrue(testList.contains(board.getCell(1, 6)));
		assertTrue(testList.contains(board.getCell(3, 6)));
		assertTrue(testList.contains(board.getCell(3, 3)));
		
		testList = board.getAdjList(23, 18);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(22, 18)));
		assertTrue(testList.contains(board.getCell(24, 18)));
		assertTrue(testList.contains(board.getCell(23, 12)));
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
		board.calcTargets(board.getCell(3, 13), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(12, 23)));
		assertTrue(targets.contains(board.getCell(15, 19)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(3, 13), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(6, 11)));
		assertTrue(targets.contains(board.getCell(6, 13)));	
		assertTrue(targets.contains(board.getCell(7, 12)));
		assertTrue(targets.contains(board.getCell(5, 10)));
		assertTrue(targets.contains(board.getCell(5, 14)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(3, 13), 4);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(7, 11)));
		assertTrue(targets.contains(board.getCell(7, 13)));	
		assertTrue(targets.contains(board.getCell(5, 9)));
		assertTrue(targets.contains(board.getCell(5, 15)));	
	}
	
	@Test
	public void testTargetsInUndergroundBunker() {
		// test a roll of 1
		board.calcTargets(board.getCell(14, 21), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(12, 23)));
		assertTrue(targets.contains(board.getCell(15, 19)));	
		assertTrue(targets.contains(board.getCell(17, 21)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(14, 21), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(12, 21)));
		assertTrue(targets.contains(board.getCell(19, 21)));	
		assertTrue(targets.contains(board.getCell(17, 19)));
		assertTrue(targets.contains(board.getCell(13, 19)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(14, 21), 4);
		targets= board.getTargets();
		assertEquals(19, targets.size()); 
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(18, 21)));	
		assertTrue(targets.contains(board.getCell(19, 20)));
		assertTrue(targets.contains(board.getCell(16, 19)));	
	}
	
	// TODO
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(8, 17), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(8, 18)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(8, 17), 3);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(7, 19)));
		assertTrue(targets.contains(board.getCell(9, 15)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(8, 17), 4);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(10, 15)));	
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(5, 16)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(11, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(11, 1)));
		assertTrue(targets.contains(board.getCell(11, 3)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 2), 3);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 2), 4);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(11, 6)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 7), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(13, 6)));
		assertTrue(targets.contains(board.getCell(12, 7)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(13, 7), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(15, 6)));
		assertTrue(targets.contains(board.getCell(14, 7)));
		assertTrue(targets.contains(board.getCell(11, 8)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(13, 7), 4);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(15, 9)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(15, 7).setOccupied(true);
		board.calcTargets(board.getCell(13, 7), 4);
		board.getCell(15, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(15, 9)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
		assertFalse( targets.contains( board.getCell(15, 7))) ;
		assertFalse( targets.contains( board.getCell(17, 7))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(12, 20).setOccupied(true);
		board.getCell(8, 18).setOccupied(true);
		board.calcTargets(board.getCell(8, 17), 1);
		board.getCell(12, 20).setOccupied(false);
		board.getCell(8, 18).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(8, 16)));	
		assertTrue(targets.contains(board.getCell(12, 20)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(12, 15).setOccupied(true);
		board.calcTargets(board.getCell(12, 20), 3);
		board.getCell(12, 15).setOccupied(false);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(8, 19)));	
		assertTrue(targets.contains(board.getCell(8, 15)));

	}
}
