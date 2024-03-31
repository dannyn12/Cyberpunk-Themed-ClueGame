package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;

class ComputerAITest {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 28;
	public static final int NUM_COLUMNS = 27;

	// since singleton board class
	private static Board board;

	@BeforeAll
	public static void setUp() throws BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	/*
	 * Test to check if computer player can select the location they want to move to from the target list
	 */
	@Test
	public void testSelectTargets() {
	}
	
	/*
	 * Test to check if computer player can make a suggestion with a player, room, and weapon.
	 */
	@Test
	public void testcreateSuggestion() {
	}

}
