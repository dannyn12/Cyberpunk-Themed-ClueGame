/* Solution class is the final solution that is generated at the beginning of the game.
 * Authors: Danny Nguyen and Jordan Lam
 * 
 */
package clueGame;

public class Solution {
	Card room;
	Card person;
	Card weapon;
	
	public Solution(Card room, Card person, Card weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}
	
	public Card getRoom() {
		return room;
	}
	public Card getPerson() {
		return person;
	}
	public Card getWeapon() {
		return weapon;
	}
	
}
