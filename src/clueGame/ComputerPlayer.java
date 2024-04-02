package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}
	
	/*
	 * this function will create the suggestion for the computer
	 * it will choose the only weapon missing if it has all others and the same for person
	 * it will choose the room it is currently in
	 * it will choose randomly if it has multiple person/weapons seen
	 */
	public Solution createSuggestion() {
		Board board = Board.getInstance();
		ArrayList<Card> roomCards = board.getRoomCards();
		ArrayList<Card> weaponCards = board.getWeaponCards();
		ArrayList<Card> peopleCards = board.getPeopleCards();
		ArrayList<Card> seen = new ArrayList<>();
		ArrayList<Card> weaponsNotSeen = new ArrayList<>();
		ArrayList<Card> peopleNotSeen = new ArrayList<>();
		Card roomCard = null;
		Card weaponCard = null;
		Card peopleCard = null;
		Random rand = new Random();
		boolean found = false;
		
		for(Card card: this.getHand()) {
			seen.add(card);
		}
		
		for(Card card: this.getSeenCards()) {
			seen.add(card);
		}
		
		// logic for finding cards that i need
		for(int i = 0 ; i < weaponCards.size(); i++) {
			Card card = weaponCards.get(i);
			found = false;
			for(Card c: seen) {
				if(card.equals(c)) {
					found = true;
				}
			}
			if(!found) {
				weaponsNotSeen.add(card);
			}
		}
		
		for(int i = 0 ; i < peopleCards.size(); i++) {
			Card card = peopleCards.get(i);
			found = false;
			for(Card c: seen) {
				if(card.equals(c)) {
					found = true;
				}
			}
			if(!found) {
				peopleNotSeen.add(card);
			}
		}

		
		// getting room card from players current location
		int[] loc = this.getLocation();
		BoardCell cell = board.getCell(loc[0], loc[1]);
		if(cell.isRoom()) {
			String name = board.getRoom(cell).getName();
			for(Card card: roomCards) {
				if(card.getCardName().equals(name)) {
					roomCard = card;
					break;
				}
			}
			if(weaponsNotSeen.size() == 1) { // computer logic for getting weapons
				weaponCard = weaponsNotSeen.get(0);
			} else {
				int randomIndex = rand.nextInt(weaponsNotSeen.size());
				weaponCard = weaponsNotSeen.get(randomIndex);
			}
			if(peopleNotSeen.size() == 1) { // computer logic for getting weapons
				peopleCard = peopleNotSeen.get(0);
			} else {
				int randomIndex2 = rand.nextInt(peopleNotSeen.size());
				peopleCard = peopleNotSeen.get(randomIndex2);
			}
			return new Solution(roomCard, peopleCard, weaponCard);
		}
		return null;
	}


	
	/*
	 * this function will do the logic for selecting the targets for the computer
	 * it will choose randomly if no targets are rooms
	 * it will choose the room if it has not been seen
	 * if room in list that has been seen it will choose a random target including the room
	 */
	public BoardCell selectTarget(Set<BoardCell> targets) {
		Board board = Board.getInstance();
		Random rand = new Random();
		ArrayList<Card> roomCards = board.getRoomCards();
		ArrayList<Card> seen = new ArrayList<>();
		ArrayList<Card> roomsNotSeen = new ArrayList<>();
		
		boolean found = false;
		
		for(Card card: this.getHand()) {
			if(card.getType() == CardType.ROOM) {
				seen.add(card);
			}
		}
		
		for(Card card: this.getSeenCards()) {
			if(card.getType() == CardType.ROOM) {
				seen.add(card);
			}
		}
		
		// logic for finding cards that i have not seen
		for(int i = 0 ; i < roomCards.size(); i++) {
			Card card = roomCards.get(i);
			found = false;
			for(Card c: seen) {
				if(card.equals(c)) {
					found = true;
				}
			}
			if(!found) {
				roomsNotSeen.add(card);
			}
		}
		
		// logic for finding if a target is a room i have not seen
		for(BoardCell target: targets) {
			if(target.isRoomCenter()) {
				String name = board.getRoom(target).getName();
				found = false;
				for(Card card: roomsNotSeen) {
					if(card.getCardName().equals(name)) {
						return target;
					}
				}		
			}
		}
		int randomIndex = rand.nextInt(targets.size());
		int curr = 0;
		for(BoardCell target: targets) {
			if(curr == randomIndex) {
				return target;
			}
			curr++;
		}
		
		return null;
	}

}
