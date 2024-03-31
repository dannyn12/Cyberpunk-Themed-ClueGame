package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Player;

class GameSolutionTest {
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
	 * Test to check when the accusation made by the player is correct or when it is false
	 */
	@Test
	public void testAccusation() {
	}
	
	/*
	 * Test to check when the player tries to dispute a suggestion with the cards in their hand. 
	 * If the player cannot, null is returned. If a player can, the player returns the card. If 
	 * is more than one card that can dispute the suggestion, one is randomly chosen
	 */
	@Test
	public void testDisproveSuggestion() {
		
	}
	
	/*
	 * Test to check if the player can dispute the suggestion.
	 */
	@Test
	public void testHandleSuggestion() {
		
	}
}
