/*
 * Authors: Jordan Lam & Danny Nguyen
 * this is the cardsPanel class
 * this draws a GUI showing the human players
 * seen and in hand cards
 */
package clueGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardsPanel extends JPanel {
	private JPanel peoplePanel;
	private JPanel roomPanel;
	private JPanel weaponsPanel;
	private static JPanel INHAND_PEOPLE;
	private static JPanel SEEN_PEOPLE;
	private static JPanel INHAND_ROOM;
	private static JPanel SEEN_ROOM;
	private static JPanel INHAND_WEAPON;
	private static JPanel SEEN_WEAPON;
	private static final String SEEN = "Seen:";
	private static final String IN_HAND = "In Hand:";
	
	/*
	 * Constructor that makes the card panel
	 */
	public CardsPanel() {
		setLayout(new GridLayout(3,0));
		setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		JPanel panel = peoplePanel();
		add(panel);
		panel = roomsPanel();
		add(panel);
		panel = weaponsPanel();
		add(panel);
	}
	
	/*
	 * Create GUI for in hand and seen people cards
	 */
	private JPanel peoplePanel() {
		// Make border
		this.peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(0,1));
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
	 	
	 	// In hand cards
	 	JLabel inHandLabel = new JLabel(IN_HAND);
	 	peoplePanel.add(inHandLabel);
	 	INHAND_PEOPLE = new JPanel();
	 	INHAND_PEOPLE.setLayout(new GridLayout(0,1));
	 	peoplePanel.add(INHAND_PEOPLE);
	 	
	 	// Seen cards:
	 	JLabel seenLabel = new JLabel(SEEN);
	 	peoplePanel.add(seenLabel);
	 	SEEN_PEOPLE = new JPanel();
	 	SEEN_PEOPLE.setLayout(new GridLayout(0,1));
	 	peoplePanel.add(SEEN_PEOPLE);
	 	
		return peoplePanel;
		
	}
	
	/*
	 * Create GUI for in hand and seen rooms cards
	 */
	private JPanel roomsPanel() {
		// Make boroder
		roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(0,1));
	 	roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
	 	
	 	// In hand cards
	 	JLabel inHandLabel = new JLabel(IN_HAND);
	 	roomPanel.add(inHandLabel);
	 	INHAND_ROOM = new JPanel();
	 	INHAND_ROOM.setLayout(new GridLayout(0,1));
	 	roomPanel.add(INHAND_ROOM);
	 	
	 	// Seen cards
	 	JLabel seenLabel = new JLabel(SEEN);
	 	roomPanel.add(seenLabel);
	 	SEEN_ROOM = new JPanel();
	 	SEEN_ROOM.setLayout(new GridLayout(0,1));
	 	roomPanel.add(SEEN_ROOM);
	 	
		return roomPanel;
	}
	
	/*
	 * Create GUI for in hand and seen rooms cards
	 */
	private JPanel weaponsPanel() {
		// Make border
		weaponsPanel = new JPanel();
		weaponsPanel.setLayout(new GridLayout(0,1));
	 	weaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
	 	
	 	// In hand cards
	 	JLabel inHandLabel = new JLabel(IN_HAND);
	 	weaponsPanel.add(inHandLabel);
	 	INHAND_WEAPON = new JPanel();
	 	INHAND_WEAPON.setLayout(new GridLayout(0,1));
	 	weaponsPanel.add(INHAND_WEAPON);
	 	
	 	// Seen cards
	 	JLabel seenLabel = new JLabel(SEEN);
	 	weaponsPanel.add(seenLabel);
	 	SEEN_WEAPON = new JPanel();
	 	SEEN_WEAPON.setLayout(new GridLayout(0,1));
	 	weaponsPanel.add(SEEN_WEAPON);
	 	
		return weaponsPanel;
	}
	
	/*
	 * Updates panel to include new card in hand or seen
	 */
	public void update(JPanel panel, Card card, String color) {
	    // Create a panel for the card
	    JPanel cardPanel = new JPanel();
	    cardPanel.setLayout(new GridLayout(1, 0));
	    String name = card.getCardName();
	    JTextField cardTextField = new JTextField();
	    cardTextField.setEditable(false);
	    cardTextField.setText(name);
	    cardPanel.add(cardTextField);
	    
	    // If card is in hand it has no color
	    if (panel == INHAND_PEOPLE || panel == INHAND_ROOM || panel == INHAND_WEAPON) {
	    	changeColor(cardTextField, "None");
	    }
	    else {
	    	changeColor(cardTextField, color);
	    }
	    // Add the card panel to panel
	    panel.add(cardPanel);
	    panel.revalidate();
	}
	
	/*
	 * Changes textField color based off string 
	 */
	private void changeColor(JTextField panel, String color) {
		if (color.equals("Blue")) {
			panel.setBackground(Color.BLUE);
		} else if (color.equals("Green")) {
			panel.setBackground(Color.green);
		} else if (color.equals("Red")) {
			panel.setBackground(Color.red);
		} else if (color.equals("Orange")) {
			panel.setBackground(Color.orange);
		} else if (color.equals("Yellow")) {
			panel.setBackground(Color.yellow);
		} else if (color.equals("Pink")) {
			panel.setBackground(Color.pink);
		} else if (color.equals("Magenta") || color.equals("Purple")) {
			panel.setBackground(Color.magenta);
		} else if (color.equals("White")) {
			panel.setBackground(Color.white);
		}

	}
	
	public static JPanel getInHandPeople() {
		return INHAND_PEOPLE;
	}


	public static JPanel getSeenPeople() {
		return SEEN_PEOPLE;
	}

	public static JPanel getInHandRoom() {
		return INHAND_ROOM;
	}

	public static JPanel getSeenRoom() {
		return SEEN_ROOM;
	}

	public static JPanel getInHandWeapon() {
		return INHAND_WEAPON;
	}

	public static JPanel getSeenWeapon() {
		return SEEN_WEAPON;
	}
	
	/*
	 * Main to test the panel
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		CardsPanel panel = new CardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame
		frame.setContentPane(panel);  // put the panel in the frame
		frame.setSize(200, 900);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.update(INHAND_PEOPLE, new Card("Cipher", CardType.PERSON), "Green");
		panel.update(SEEN_PEOPLE, new Card("Nova", CardType.PERSON), "Purple");
		panel.update(SEEN_PEOPLE, new Card("Neon", CardType.PERSON), "Pink");
		panel.update(SEEN_PEOPLE, new Card("Vortex", CardType.PERSON), "Orange");
		
		panel.update(INHAND_ROOM, new Card("None", CardType.ROOM), "None");
		panel.update(SEEN_ROOM, new Card("Neon Ally", CardType.ROOM), "Pink");
		panel.update(SEEN_ROOM, new Card("Corporate Tower", CardType.ROOM), "Blue");
		panel.update(SEEN_ROOM, new Card("Data Core", CardType.ROOM), "Green");
		panel.update(SEEN_ROOM, new Card("Slum District", CardType.ROOM), "Red");
		
		panel.update(INHAND_WEAPON, new Card("Data Spike", CardType.WEAPON), "Green");
		panel.update(INHAND_WEAPON, new Card("EMP Grenade", CardType.WEAPON), "Blue");
		panel.update(SEEN_WEAPON, new Card("Cybernetic Augmentation", CardType.WEAPON), "Blue");
		panel.update(SEEN_WEAPON, new Card("Nanobot Injector", CardType.WEAPON), "Orange");		
		
	}

}

