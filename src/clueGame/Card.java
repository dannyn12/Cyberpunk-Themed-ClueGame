package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Card target = (Card) obj;
	    return cardName.equals(target.getCardName()) && type == target.getType();
	}

	public String getCardName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}
		
}
