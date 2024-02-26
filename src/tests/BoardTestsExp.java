package tests;

import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard(4,4);
	}
	
	/*
	 * Test adjacency for edges and center
	 */
	@Test
	public void testAdjacency() {
		// test top left of board
		TestBoardCell topLeft = board.getCell(0, 0);
		Set<TestBoardCell> testList = topLeft.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
		
		// test bottom right corner
		TestBoardCell bottomRight = board.getCell(3, 3);
		Set<TestBoardCell> testList2 = bottomRight.getAdjList();
		Assert.assertTrue(testList2.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList2.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, testList2.size());
		
		// test right edge
		TestBoardCell rightEdge = board.getCell(1, 3);
		Set<TestBoardCell> testList3 = rightEdge.getAdjList();
		Assert.assertTrue(testList3.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList3.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList3.contains(board.getCell(1, 2)));
		Assert.assertEquals(3, testList3.size());
		
		// test left edge
		TestBoardCell leftEdge = board.getCell(2, 0);
		Set<TestBoardCell> testList4 = leftEdge.getAdjList();
		Assert.assertTrue(testList4.contains(board.getCell(3, 0)));
		Assert.assertTrue(testList4.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList4.contains(board.getCell(2, 1)));
		Assert.assertEquals(3, testList4.size());
		
		// test middle
		TestBoardCell middle = board.getCell(2, 2);
		Set<TestBoardCell> testList5 = middle.getAdjList();
		Assert.assertTrue(testList5.contains(board.getCell(3, 2)));
		Assert.assertTrue(testList5.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList5.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList5.contains(board.getCell(2, 3)));
		Assert.assertEquals(4, testList5.size());		
	}
	
	/*
	 * Test targets with rolls and start location
	 */
	@Test
	public void testTargetsNormal() {
		// test when roll is 1
		TestBoardCell rollOf1 = board.getCell(0, 0);
		board.calcTargets(rollOf1, 1);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		
		// test when roll is 2
		TestBoardCell rollOf2 = board.getCell(0, 0);
		board.calcTargets(rollOf2, 2);
		Set<TestBoardCell> targets2 = board.getTargets();
		Assert.assertEquals(3, targets2.size());
		Assert.assertTrue(targets2.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets2.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets2.contains(board.getCell(1, 1)));
		
		// test when roll is 3
		TestBoardCell rollOf3 = board.getCell(0, 0);
		board.calcTargets(rollOf3, 3);
		Set<TestBoardCell> targets3 = board.getTargets();
		Assert.assertEquals(6, targets3.size());
		Assert.assertTrue(targets3.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets3.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets3.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets3.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets3.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets3.contains(board.getCell(1, 0)));	
		
		// test when roll is 4
		TestBoardCell rollOf4 = board.getCell(0, 0);
		board.calcTargets(rollOf4, 4);
		Set<TestBoardCell> targets4 = board.getTargets();
		Assert.assertEquals(7, targets4.size());
		Assert.assertTrue(targets4.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets4.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets4.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets4.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets4.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets4.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets4.contains(board.getCell(1, 3)));
		
		// test when roll is 5
		TestBoardCell rollOf5 = board.getCell(0, 0);
		board.calcTargets(rollOf5, 5);
		Set<TestBoardCell> targets5 = board.getTargets();
		Assert.assertEquals(8, targets5.size());
		Assert.assertTrue(targets5.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets5.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets5.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets5.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets5.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets5.contains(board.getCell(0,1)));
		Assert.assertTrue(targets5.contains(board.getCell(1,0)));
		Assert.assertTrue(targets5.contains(board.getCell(1,0)));
		
		// test when roll is 6
		TestBoardCell rollOf6 = board.getCell(0, 0);
		board.calcTargets(rollOf6, 6);
		Set<TestBoardCell> targets6 = board.getTargets();
		Assert.assertEquals(9, targets6.size());
		Assert.assertTrue(targets6.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets6.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets6.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets6.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets6.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets6.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets6.contains(board.getCell(3,0)));
		Assert.assertTrue(targets6.contains(board.getCell(0,2)));
		Assert.assertTrue(targets6.contains(board.getCell(0,2)));
	}
	
	/*
	 * Test for targets when a room in involved
	 */
	@Test
	public void testTargetsRoom() {
		// test case where 1 room can be entered 
		board.getCell(1, 0).setIsRoom(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1,0))); 
		Assert.assertTrue(targets.contains(board.getCell(1,1))); 
		Assert.assertTrue(targets.contains(board.getCell(0,2)));
		board.getCell(1, 0).setIsRoom(false);
		// test case where only move would be to enter one of two rooms
		board.getCell(1, 0).setIsRoom(true);
		board.getCell(0, 1).setIsRoom(true);
		TestBoardCell cell2 = board.getCell(0, 0);
		board.calcTargets(cell2, 5);
		Set<TestBoardCell> targets2 = board.getTargets();
		Assert.assertEquals(2, targets2.size());
		Assert.assertTrue(targets2.contains(board.getCell(1,0))); 
		Assert.assertTrue(targets2.contains(board.getCell(0,1))); 
		
	}
	
	/*
	 * Test for targets when an interfering occupied space is involved
	 */
	@Test
	public void testTargetsOccupied() {
		// test case where 1 player blocks a cell the user could've went to
		board.getCell(2, 0).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0,2))); 
		Assert.assertTrue(targets.contains(board.getCell(1,1))); 
		board.getCell(2, 0).setOccupied(false);
		// test case where the player is trapped between others so they aren't able to move
		board.getCell(1, 0).setOccupied(true);
		board.getCell(0, 1).setOccupied(true);
		TestBoardCell cell2 = board.getCell(0, 0);
		board.calcTargets(cell2, 3);
		Set<TestBoardCell> targets2 = board.getTargets();
		Assert.assertEquals(0, targets2.size());
	}
	
	/*
	 * Test that includes room and occupied cells
	 */
	@Test
	public void testTargetsMixed() {
		// test case where player blocks path but there is a room                             
		board.getCell(0, 2).setOccupied(true);                   
		board.getCell(1, 2).setIsRoom(true);                     
		TestBoardCell cell = board.getCell(0, 3);                
		board.calcTargets(cell, 3);                              
		Set<TestBoardCell> targets = board.getTargets();         
		Assert.assertEquals(3, targets.size());                  
		Assert.assertTrue(targets.contains(board.getCell(1,2))); 
		Assert.assertTrue(targets.contains(board.getCell(2,2))); 
		Assert.assertTrue(targets.contains(board.getCell(3,3))); 
		board.getCell(0, 2).setOccupied(false);                   
		board.getCell(1, 2).setIsRoom(false);    
		
		// test case where player blocks path and the only route is a room
		board.getCell(0,1).setIsRoom(true);
		board.getCell(1,0).setOccupied(true);
		TestBoardCell cell2 = board.getCell(0, 0);
		board.calcTargets(cell2, 3);
		Set<TestBoardCell> targets2 = board.getTargets();
		Assert.assertEquals(3, targets2);
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
	}   
	
}
	
	
	
	
	
	
	