package clueGame;

import java.util.ArrayList;

public abstract class Player {
	private String name;
	private String color;
	private int row, col;
	private ArrayList<Card> hand;
	
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
	
	
}
