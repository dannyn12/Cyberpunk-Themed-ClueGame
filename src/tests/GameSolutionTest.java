package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 28;
	public static final int NUM_COLUMNS = 27;

	// since singleton board class
	private static Board board;
	private static Card neonAllyCard, clinicCard, towerCard, nexusCard, coreCard, districtCard, labCard, bunkerCard, junkCard, cipherCard, novaCard, neonCard, vortexCard, spectreCard, rebalCard, disrupterCard, injectorCard, grenadeCard, virusCard, spikeCard, augmentationCard;

	@BeforeAll
	public static void setUp() throws BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
		// Create some card constants for test
		//
		// 9 rooms
		neonAllyCard = new Card("Neon Alley", CardType.ROOM);
		clinicCard = new Card("Cybernetics Clinic", CardType.ROOM);
		towerCard = new Card("Corporate Tower", CardType.ROOM);
		nexusCard = new Card("Cyberspace Nexus", CardType.ROOM);
		coreCard = new Card("Data Core", CardType.ROOM);
		districtCard = new Card("Slum District", CardType.ROOM);
		labCard = new Card("Quantum Laboratory", CardType.ROOM);
		bunkerCard = new Card("Underground Bunker", CardType.ROOM);
		junkCard = new Card("Techno Junkyard", CardType.ROOM);
		// 6 players
		cipherCard = new Card("Cipher", CardType.PERSON);
		novaCard = new Card("Nova", CardType.PERSON);
		neonCard = new Card("Neon", CardType.PERSON);
		vortexCard = new Card("Vortex", CardType.PERSON);
		spectreCard = new Card("Spectre", CardType.PERSON);
		rebalCard = new Card("Rebel", CardType.PERSON);
		// 6 weapons
		disrupterCard = new Card("Neural Disruptor", CardType.WEAPON);
		injectorCard = new Card("Nanobot Injector", CardType.WEAPON);
		grenadeCard = new Card("EMP Grenade", CardType.WEAPON);
		virusCard = new Card("Neural Interface Virus", CardType.WEAPON);
		spikeCard = new Card("Data Spike", CardType.WEAPON);
		augmentationCard = new Card("Cybernetic Augmentation", CardType.WEAPON);
		
		
	}

	/*
	 * Test to check when the accusation made by the player is correct or when it is false
	 */
	@Test
	public void testAccusation() {
		// set solution
		Solution solution = new Solution(neonAllyCard, cipherCard, disrupterCard);
		board.setSolution(solution);
		// test solution is correct
		assertTrue(board.checkAccusation(neonAllyCard, cipherCard, disrupterCard));
		//test solution with wrong person
		assertFalse(board.checkAccusation(neonAllyCard, novaCard, disrupterCard));
		// test solution with wrong weapon
		assertFalse(board.checkAccusation(neonAllyCard, cipherCard, virusCard));
		// test solution with wrong room
		assertFalse(board.checkAccusation(coreCard, cipherCard, disrupterCard));
		
	}
	
	/*
	 * Test to check when the player tries to dispute a suggestion with the cards in their hand. 
	 * If the player cannot, null is returned. If a player can, the player returns the card. If 
	 * is more than one card that can dispute the suggestion, one is randomly chosen
	 */
	@Test
	public void testDisproveSuggestion() {
		// create a player with known cards
		Player player = new HumanPlayer("testPlayer", "white", 0, 0);
		player.updateHand(augmentationCard);
		player.updateHand(rebalCard);
		player.updateHand(junkCard);
		
		// test if player has only one matching card it should be returned
		assertEquals(player.disproveSuggestion(augmentationCard, cipherCard, coreCard), augmentationCard);
		
		// test if players has >1 matching card, returned card should be chosen randomly
		ArrayList<Card> match = new ArrayList<>();
		while (match.size() != 2) {
			Card cardReturn = player.disproveSuggestion(augmentationCard, rebalCard, coreCard);
			if (!match.contains(cardReturn)) {
				match.add(cardReturn);
			}
		}
		assertTrue(match.contains(augmentationCard));
		assertTrue(match.contains(rebalCard));
		
		// If player has no matching cards, null is returned
		assertNull(player.disproveSuggestion(virusCard, cipherCard, coreCard));
	
	}
	
	/*
	 * Test to check if the player can dispute the suggestion.
	 */
	@Test
	public void testHandleSuggestion() {
		// create a small number of players with known cards
		ArrayList<Player> playerList = new ArrayList<>();
		Player player = new HumanPlayer("testPlayer", "white", 0, 0);
		player.updateHand(augmentationCard);
		player.updateHand(rebalCard);
		player.updateHand(junkCard);
		playerList.add(player);
		
		Player player2 = new HumanPlayer("testPlayer2", "black", 1, 1);
		player2.updateHand(disrupterCard);
		player2.updateHand(cipherCard);
		player2.updateHand(neonAllyCard);
		playerList.add(player2);
		
		Player player3 = new HumanPlayer("testPlayer3", "grey", 2, 2);
		player3.updateHand(virusCard);
		player3.updateHand(novaCard);
		player3.updateHand(clinicCard);
		playerList.add(player3);
		
		// set player list to test players
		board.setPlayers(playerList);
		
		// test suggestion no one can disprove returns null
		assertNull(board.handleSuggestion(coreCard, vortexCard, injectorCard, player));
		// test suggestion only suggesting player can disprove returns null
		assertNull(board.handleSuggestion(junkCard, vortexCard, injectorCard, player));
		// test suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
		assertEquals(board.handleSuggestion(neonAllyCard, vortexCard, injectorCard, player), neonAllyCard);
		// test suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
		assertEquals(board.handleSuggestion(neonAllyCard, novaCard, injectorCard, player), neonAllyCard);
		
	
	}
}
