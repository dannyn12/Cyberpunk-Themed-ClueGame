/*
 * Player class holds all the information about the players in Clue and is an abstract class.
 * Authors: Danny Nguyen and Jordan Lam
 */
package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	private String name;
	private String color;
	private int row, col;
	private ArrayList<Card> hand = new ArrayList<>();
	private Set<Card> seenCards = new HashSet<>();
	private boolean playerFinished;
	
	public Player(String name, String color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
		this.playerFinished = false;
	}
	
	public void move(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	/*
	 * 	Updates location of player
	 */
	public void updateLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void isPlayerFinished(boolean finished) {
		this.playerFinished = finished;
	}
	
	public boolean getPlayerFinished() {
		return this.playerFinished;
	}
	
	/*
	 *  Updates the hand of the player
	 */
	public void updateHand(Card card) {
		this.hand.add(card);
	}
	
	/*
	 *  Updates that are seen by player
	 */
	public void updateSeen(Card seenCard) {
		this.seenCards.add(seenCard);
	}
	
	/*
	 * A player tries to dispute a suggestion with the cards in their hand. 
	 * If the player cannot, null is returned. If a player can, the player returns the card. 
	 * If is more than one card that can dispute the suggestion, one is randomly chosen.
	 */
	public Card disproveSuggestion(Card room, Card person, Card weapon) {
		ArrayList<Card> cardAvailable = new ArrayList<>();

		// check for all card that matches the suggestion
		for (Card card: hand) {
			if (card.getCardName() == room.getCardName() || card.getCardName() == person.getCardName() || card.getCardName() == weapon.getCardName()) {
				cardAvailable.add(card);
			}
		}
		
		// if the suggestion has more than once correct suggestion pick a random card to show
		if (cardAvailable.size() > 1) {
			Random random = new Random();
			int randomIndex = random.nextInt(cardAvailable.size());
			return cardAvailable.get(randomIndex);
		}
		// if only one card show that card
		else if (cardAvailable.size() == 1){
			return cardAvailable.get(0);
		}
		// if no matching return null
		else {
			return null;
		}
		
	}
	
	/*
	 * Clears all seen cards
	 */
	public void clearCards() {
		this.seenCards.clear();
		this.hand.clear();
	}
	
	/*
	 * 	Gets location of player
	 */
	public int[] getLocation() {
		int[] location = {this.row, this.col};
		return location;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public Set<Card> getSeenCards() {
		return seenCards;
	}

	public void setSeenCards(Set<Card> seenCards) {
		this.seenCards = seenCards;
	}
	
	
}
