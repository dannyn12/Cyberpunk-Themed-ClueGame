package clueGame;

import java.util.ArrayList;

public abstract class Player {
	private String name;
	private String color;
	private int row, col;
	private ArrayList<Card> hand = new ArrayList<>();
	
	public Player(String name, String color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
	}
	
	/*
	 * 
	 */
	public void updateLocation(int row, int col) {
	}
	
	/*
	 * 
	 */
	public void updateHand(Card card) {
		this.hand.add(card);
	}
	
	/*
	 * 
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
	
	
}
