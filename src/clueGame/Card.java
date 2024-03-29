package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String cardName, String type) {
		this.cardName = cardName;
		if (type == "Person") {
			this.type = CardType.PERSON;
		} else if (type == "Room"){
			this.type = CardType.PERSON;
		} else if(type == "Weapon") {
			this.type = CardType.WEAPON;
		}
	}
	
	public boolean equals(Card target) {
		return false;
	}

	public String getCardName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}
	
	
}
