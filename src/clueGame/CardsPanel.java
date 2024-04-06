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
	private static JPanel peoplePanel;
	private static JPanel roomPanel;
	private static JPanel weaponsPanel;
	private static JPanel inHandPeople;
	private static JPanel seenPeople;
	private static JPanel inHandRoom;
	private static JPanel seenRoom;
	private static JPanel inHandWeapon;
	private static JPanel seenWeapon;
	public CardsPanel() {
		setLayout(new GridLayout(3,1));
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
		peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(0,1));
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
	 	
	 	// In hand cards
	 	JLabel inHandLabel = new JLabel("In Hand:");
	 	peoplePanel.add(inHandLabel);
	 	inHandPeople = new JPanel();
	 	inHandPeople.setLayout(new GridLayout(0,1));
	 	peoplePanel.add(inHandPeople);
	 	
	 	// Seen cards:
	 	JLabel seenLabel = new JLabel("Seen:");
	 	peoplePanel.add(seenLabel);
	 	seenPeople = new JPanel();
	 	seenPeople.setLayout(new GridLayout(0,1));
	 	peoplePanel.add(seenPeople);
	 	
		return peoplePanel;
		
	}
	
	/*
	 * Create GUI for in hand and seen rooms cards
	 */
	private JPanel roomsPanel() {
		roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(0,1));
	 	roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
	 	
	 	// In hand cards
	 	JLabel inHandLabel = new JLabel("In Hand:");
	 	roomPanel.add(inHandLabel);
	 	inHandRoom = new JPanel();
	 	inHandRoom.setLayout(new GridLayout(0,1));
	 	roomPanel.add(inHandRoom);
	 	
	 	// Seen cards:
	 	JLabel seenLabel = new JLabel("Seen:");
	 	roomPanel.add(seenLabel);
	 	seenRoom = new JPanel();
	 	seenRoom.setLayout(new GridLayout(0,1));
	 	roomPanel.add(seenRoom);
	 	
		return roomPanel;
	}
	
	/*
	 * Create GUI for in hand and seen rooms cards
	 */
	private JPanel weaponsPanel() {
		weaponsPanel = new JPanel();
		weaponsPanel.setLayout(new GridLayout(0,1));
	 	weaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
	 	
	 	// In hand cards
	 	JLabel inHandLabel = new JLabel("In Hand:");
	 	weaponsPanel.add(inHandLabel);
	 	inHandWeapon = new JPanel();
	 	inHandWeapon.setLayout(new GridLayout(0,1));
	 	weaponsPanel.add(inHandWeapon);
	 	
	 	// Seen cards:
	 	JLabel seenLabel = new JLabel("Seen:");
	 	weaponsPanel.add(seenLabel);
	 	seenWeapon = new JPanel();
	 	seenWeapon.setLayout(new GridLayout(0,1));
	 	weaponsPanel.add(seenWeapon);
	 	
		return weaponsPanel;
	}
	
	/*
	 * Updates panel to include new card in hand or seen
	 */
	private void update(JPanel panel, Card card) {
	    // Create a panel for the card
	    JPanel cardPanel = new JPanel();
	    cardPanel.setLayout(new GridLayout(1, 1));
	    String name = card.getCardName();
	    JTextField cardTextField = new JTextField();
	    cardTextField.setText(name);
	    cardPanel.add(cardTextField);
	    // Add the card panel to the specified panel
	    panel.add(cardPanel);
	    // Repaint the panel to reflect the changes
	    panel.revalidate();
	    panel.repaint();
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
		panel.update(inHandPeople, new Card("Cipher", CardType.PERSON));
		panel.update(seenPeople, new Card("Nova", CardType.PERSON));
		panel.update(seenPeople, new Card("Neon", CardType.PERSON));
		panel.update(seenPeople, new Card("Vortex", CardType.PERSON));
		
		panel.update(inHandRoom, new Card("None", CardType.ROOM));
		panel.update(seenRoom, new Card("Neon Ally", CardType.ROOM));
		panel.update(seenRoom, new Card("Corporate Tower", CardType.ROOM));
		panel.update(seenRoom, new Card("Data Core", CardType.ROOM));
		panel.update(seenRoom, new Card("Slum District", CardType.ROOM));
		
		panel.update(inHandWeapon, new Card("Data Spike", CardType.WEAPON));
		panel.update(inHandWeapon, new Card("EMP Grenade", CardType.WEAPON));
		panel.update(seenWeapon, new Card("Cybernetic Augmentation", CardType.WEAPON));
		panel.update(seenWeapon, new Card("Nanobot Injector", CardType.WEAPON));
		
		
		
	}
}

