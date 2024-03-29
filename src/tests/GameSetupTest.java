package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Solution;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;
import java.util.Set;

class GameSetupTest {
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
	 * Test to check 6 players exist and the correct players
	 * checks that player are at the place on the board using is occupied
	 */
	@Test
	public void testPeopleLoaded() {
		ArrayList<Player> players = board.getPlayers();
		assertEquals(players.size(), 6);
		
		// check if each player is loaded on correct cell
		for (Player person: players) {
			int row = person.getRow();
			int col = person.getCol();
			BoardCell playerCells = board.getCell(row, col);
			assertTrue(playerCells.isOccupied());
		}
		
	}
	/*
	 * Test to check that there is 1 human player and 5 computer player
	 */
	@Test
	public void testProperPlayers() {
		ArrayList<Player> players = board.getPlayers();
		int numHuman = 0;
		int numComputer = 0;
		
		for (Player player: players) {
			if (player instanceof HumanPlayer) {
				numHuman += 1;
			}
			else {
				numComputer += 1;
			}
		}
		
		assertEquals(numHuman, 1);
		assertEquals(numComputer, 5);
	}
	
	/*
	 * Test to check that deck includes all cards 
	 */
	@Test
	public void testDeck() {

		assertEquals(board.getDeck().size(), 21);
		assertEquals(board.getPeopleCards().size(), 6);
		assertEquals(board.getWeaponCards().size(), 6);
		assertEquals(board.getRoomCards().size(), 9);
	}
	/*
	 * Test to check that there is a solution
	 */
	@Test
	public void testSolution() {
		Solution solution = board.getSolution();
		Card person = solution.getPerson();
		Card room = solution.getRoom();
		Card weapon = solution.getWeapon();
		
		assertEquals(person.getType(), CardType.PERSON);
		assertEquals(room.getType(), CardType.ROOM);
		assertEquals(weapon.getType(), CardType.WEAPON);
		
	}
	/*
	 * Test to check that all other cards are dealt to players 
	 */
	@Test
	public void testCardsDealt() {
		Solution solution = board.getSolution();
		ArrayList<Player> players = board.getPlayers();
		Set<Card> seen = new HashSet<>();
		seen.add(solution.getPerson());
		seen.add(solution.getRoom());
		seen.add(solution.getWeapon());
		
		/* 
		 * looks through every players hands and makes sure that
		 * for 1 they have at least 2 cards and that none are from solution
		 * or another players hands
		 */
		for(Player player: players) {
			ArrayList<Card> hand = player.getHand();
			assertTrue(hand.size() > 2);
			for(Card card: hand) {
				assertFalse(seen.contains(card));
				seen.add(card);
			}
		}
	}
}
