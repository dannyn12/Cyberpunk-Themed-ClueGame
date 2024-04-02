/* ComputerAITest contains test that test if the computer player works correctly in the Clue game.
 * Authors: Danny Nguyen and Jordan Lam
 * 4/1/24
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;

class ComputerAITest {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 28;
	public static final int NUM_COLUMNS = 27;
	private static Card neonAllyCard, clinicCard, towerCard, nexusCard, coreCard, districtCard, labCard, bunkerCard, junkCard, cipherCard, novaCard, neonCard, vortexCard, spectreCard, rebalCard, disrupterCard, injectorCard, grenadeCard, virusCard, spikeCard, augmentationCard;

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
		// remove all players
		board.getCell(0,9).setOccupied(false);
		board.getCell(0,17).setOccupied(false);
		board.getCell(19, 0).setOccupied(false);
		board.getCell(19, 26).setOccupied(false);
		board.getCell(7, 26).setOccupied(false);
	}
	
	/*
	 * Test to check if computer player can select the location they want to move to from the target list
	 */
	@Test
	public void testSelectTargets() {
		// no rooms in target list select randomly
		ComputerPlayer testPlayer1 = new ComputerPlayer("test" , "black", 6, 8);
		Set<BoardCell> seen = new HashSet<>();
		Set<BoardCell> targets = new HashSet<>();	
		board.calcTargets(board.getCell(6, 8), 1);
		targets = board.getTargets();
		
		for(int i = 0; i < 100; i++) {
			seen.add(testPlayer1.selectTarget(targets));
		}
		assertTrue(seen.size() > 2);
		// if room in list that has not been seen, select it
		ComputerPlayer testPlayer2 = new ComputerPlayer("test" , "black", 12, 22);
		testPlayer2.updateHand(junkCard);
		seen.clear();
		board.calcTargets(board.getCell(12, 22), 2);
		targets = board.getTargets();
		seen.add(testPlayer2.selectTarget(targets));
		for(int i = 0; i < 100; i++) {
			seen.add(testPlayer2.selectTarget(targets));
		}
		assertTrue(seen.size() == 1);
		assertTrue(seen.contains(board.getCell(14, 21)));
		// if room in list that has been seen, each target (including room) selected randomly
		ComputerPlayer testPlayer3 = new ComputerPlayer("test" , "black", 14, 7);
		testPlayer3.updateHand(nexusCard);
		seen.clear();
		board.calcTargets(board.getCell(14, 7), 1);
		targets = board.getTargets();
		for(int i = 0; i < 100; i++) {
			seen.add(testPlayer1.selectTarget(targets));
		}
		assertTrue(seen.size() > 3);
	}
	
	/*
	 * Test to check if computer player can make a suggestion with a player, room, and weapon.
	 */
	@Test
	public void testcreateSuggestion() {
		ComputerPlayer testPlayer = new ComputerPlayer("test" , "black", 4, 22);
		// room matches current location
		testPlayer.updateHand(bunkerCard);
		Solution solution0 = testPlayer.createSuggestion();
		assertTrue(solution0.getRoom().equals(clinicCard));
		testPlayer.clearCards();
		// if only one weapon not seen, it is selected
		testPlayer.updateHand(disrupterCard);
		testPlayer.updateHand(injectorCard);
		testPlayer.updateHand(augmentationCard);
		testPlayer.updateHand(grenadeCard);
		testPlayer.updateHand(spikeCard);
		Solution solution1 = testPlayer.createSuggestion();
		assertTrue(solution1.getWeapon().equals(virusCard));
		testPlayer.clearCards();
		// if only one person not seen, it is selected
		testPlayer.updateHand(cipherCard);
		testPlayer.updateHand(novaCard);
		testPlayer.updateHand(neonCard);
		testPlayer.updateHand(vortexCard);
		testPlayer.updateHand(spectreCard);
		Solution solution2 = testPlayer.createSuggestion();
		assertTrue(solution2.getPerson().equals(rebalCard));
		testPlayer.clearCards();
		// if multiple weapons not seen, one of them is randomly selected
		testPlayer.updateHand(disrupterCard);
		testPlayer.updateHand(injectorCard);
		testPlayer.updateHand(augmentationCard);
		Set<Card> seen = new HashSet<>();
		seen.add(disrupterCard);
		seen.add(injectorCard);
		seen.add(augmentationCard);
		Solution solution3 = testPlayer.createSuggestion();
		assertFalse(seen.contains(solution3.getWeapon()));
		testPlayer.clearCards();
		// if multiple persons not seen, one of them is randomly selected
		testPlayer.updateHand(cipherCard);
		testPlayer.updateHand(novaCard);
		testPlayer.updateHand(neonCard);
		seen.clear();
		seen.add(cipherCard);
		seen.add(novaCard);
		seen.add(neonCard);
		Solution solution4 = testPlayer.createSuggestion();
		assertFalse(seen.contains(solution4.getPerson()));
	}


}
