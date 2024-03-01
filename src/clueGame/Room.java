/*
 * Authors: Jordan Lam & Danny Nguyen
 * This is the Room class *insert information about room class*
 */
package clueGame;
public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room() {
		super();
	}
	
	public Room(String name, BoardCell centerCell, BoardCell labelCell) {
		this.name = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	
	public BoardCell getLabelCell() {
		return labelCell;
	}
	
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	
	
}
