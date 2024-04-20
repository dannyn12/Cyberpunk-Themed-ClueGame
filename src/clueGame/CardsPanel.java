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
	private static JPanel inhandPeople = new JPanel();
	private static JPanel seenPeople = new JPanel();
	private static JPanel inhandRoom = new JPanel();
	private static JPanel seenRoom = new JPanel();
	private static JPanel inhandWeapon = new JPanel();
	private static JPanel seenWeapon = new JPanel();
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
		JPanel peoplePanel;
		// Make border
		peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(0,1));
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));

		// In hand cards
		JLabel inHandLabel = new JLabel(IN_HAND);
		peoplePanel.add(inHandLabel);
		inhandPeople.setLayout(new GridLayout(0,1));
		peoplePanel.add(inhandPeople);

		// Seen cards:
		JLabel seenLabel = new JLabel(SEEN);
		peoplePanel.add(seenLabel);
		seenPeople.setLayout(new GridLayout(0,1));
		peoplePanel.add(seenPeople);

		return peoplePanel;
	}
	
	/*
	 * Create GUI for in hand and seen rooms cards
	 */
	private JPanel roomsPanel() {
		JPanel roomPanel;
		// Make boroder
		roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(0,1));
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));

		// In hand cards
		JLabel inHandLabel = new JLabel(IN_HAND);
		roomPanel.add(inHandLabel);
		inhandRoom.setLayout(new GridLayout(0,1));
		roomPanel.add(inhandRoom);

		// Seen cards
		JLabel seenLabel = new JLabel(SEEN);
		roomPanel.add(seenLabel);
		seenRoom.setLayout(new GridLayout(0,1));
		roomPanel.add(seenRoom);

		return roomPanel;
	}
	
	/*
	 * Create GUI for in hand and seen rooms cards
	 */
	private JPanel weaponsPanel() {
		JPanel weaponsPanel;
		// Make border
		weaponsPanel = new JPanel();
		weaponsPanel.setLayout(new GridLayout(0,1));
		weaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));

		// In hand cards
		JLabel inHandLabel = new JLabel(IN_HAND);
		weaponsPanel.add(inHandLabel);
		inhandWeapon.setLayout(new GridLayout(0,1));
		weaponsPanel.add(inhandWeapon);

		// Seen cards
		JLabel seenLabel = new JLabel(SEEN);
		weaponsPanel.add(seenLabel);
		seenWeapon.setLayout(new GridLayout(0,1));
		weaponsPanel.add(seenWeapon);

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
	    changeColor(cardTextField, color);
	    
	    // Add the card panel to panel
	    panel.add(cardPanel);
	    panel.revalidate();
	}
	
	/*
	 * Changes textField color based off string 
	 */
	private void changeColor(JTextField panel, String color) {
	    switch (color.toLowerCase()) {
	        case "blue":
	            panel.setBackground(Color.cyan);
	            break;
	        case "green":
	            panel.setBackground(Color.green);
	            break;
	        case "red":
	            panel.setBackground(Color.red);
	            break;
	        case "orange":
	            panel.setBackground(Color.orange);
	            break;
	        case "yellow":
	            panel.setBackground(Color.yellow);
	            break;
	        case "pink":
	            panel.setBackground(Color.pink);
	            break;
	        case "purple":
	            Color purple = new Color(148, 150, 211);
	            panel.setBackground(purple);
	            break;
	        case "white":
	            panel.setBackground(Color.white);
	            break;
	        default:
	            // Handle the case when color doesn't match any of the specified colors
	            break;
	    }
	}

	
	public static JPanel getInHandPeople() {
		return inhandPeople;
	}


	public static JPanel getSeenPeople() {
		return seenPeople;
	}

	public static JPanel getInHandRoom() {
		return inhandRoom;
	}

	public static JPanel getSeenRoom() {
		return seenRoom;
	}

	public static JPanel getInHandWeapon() {
		return inhandWeapon;
	}

	public static JPanel getSeenWeapon() {
		return seenWeapon;
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
		panel.update(inhandPeople, new Card("Cipher", CardType.PERSON), "Green");
		panel.update(seenPeople, new Card("Nova", CardType.PERSON), "Purple");
		panel.update(seenPeople, new Card("Neon", CardType.PERSON), "Pink");
		panel.update(seenPeople, new Card("Vortex", CardType.PERSON), "Orange");
		
		panel.update(inhandRoom, new Card("None", CardType.ROOM), "None");
		panel.update(seenRoom, new Card("Neon Ally", CardType.ROOM), "Pink");
		panel.update(seenRoom, new Card("Corporate Tower", CardType.ROOM), "Blue");
		panel.update(seenRoom, new Card("Data Core", CardType.ROOM), "Green");
		panel.update(seenRoom, new Card("Slum District", CardType.ROOM), "Red");
		
		panel.update(inhandWeapon, new Card("Data Spike", CardType.WEAPON), "Green");
		panel.update(inhandWeapon, new Card("EMP Grenade", CardType.WEAPON), "Blue");
		panel.update(seenWeapon, new Card("Cybernetic Augmentation", CardType.WEAPON), "Blue");
		panel.update(seenWeapon, new Card("Nanobot Injector", CardType.WEAPON), "Orange");		
		
	}

}

