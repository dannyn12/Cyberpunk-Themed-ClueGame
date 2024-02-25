package tests;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	
	/*
	 * Test adjacencies for edges and center
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
	
	
	
	
	
}
