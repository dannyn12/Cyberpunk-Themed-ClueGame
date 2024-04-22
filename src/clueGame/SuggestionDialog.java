/* SuggestionDialog class displays a dialog that allows the player to make a suggestion when they go into a room
 * Authors: Jordan Lam, Danny Nguyen
 */
package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SuggestionDialog extends JDialog{
	private JComboBox<String> personComboBox, weaponComboBox;
	private JButton submitButton, cancelButton;
	private Card personSuggestion;
	private Card weaponSuggestion;
	private Card roomSuggestion;
	private Card guessResult;

	public SuggestionDialog(JFrame parent, Board board, BoardCell selectedCell, Player player) {
		super(parent, "Make a Suggestion", true);
		// get room player is currently in
		char roomInitial = selectedCell.getInitial();
		Room roomIn = board.getRoom(roomInitial);
		// Initialize JComboBox for room selection
		personComboBox = createComboBox(board.getPeopleCards());
		weaponComboBox = createComboBox(board.getWeaponCards());

		// Initialize buttons
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");

		// Set layout manager for the dialog
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));

		// Add components to the panel
		panel.add(new JLabel("Room:"));
		panel.add(new JLabel(roomIn.getName()));
		panel.add(new JLabel("Person:"));
		panel.add(personComboBox);
		panel.add(new JLabel("Weapon:"));
		panel.add(weaponComboBox);
		panel.add(submitButton);
		panel.add(cancelButton);

		// Add the panel to the dialog
		getContentPane().add(panel);

		// Set dialog properties
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(parent);
		setResizable(false);

		// Get the players selected suggestion and see if its correct or wrong
		submitButton.addActionListener(e -> {
			// get player selected suggestion
			ArrayList<Card> roomCards = board.getRoomCards();
			ArrayList<Card> personCards = board.getPeopleCards();
			ArrayList<Card> weaponCards = board.getWeaponCards();
			
			// get suggested card 
			for (Card card: roomCards) {
				if (card.getCardName() == roomIn.getName()) {
					roomSuggestion = card;
				}
			}
			personSuggestion = personCards.get(personComboBox.getSelectedIndex());
			weaponSuggestion = weaponCards.get(weaponComboBox.getSelectedIndex());
			
			// add suggestion logic
			guessResult = board.handleSuggestion(roomSuggestion, personSuggestion, weaponSuggestion, player);
		
			// Close the dialog 
			dispose();
		});

		// Cancel an accusation
		cancelButton.addActionListener(e -> {
			dispose();
		});
	}
	
    /*
     * Create pull down for rooms, people, and weapons
     */
    private JComboBox<String> createComboBox(ArrayList<Card> cards) {
        ArrayList<String> options = new ArrayList<>();
        if (cards != null) {
            for (Card card : cards) {
                options.add(card.getCardName());
            }
        }
        return new JComboBox<>(options.toArray(new String[0]));
    }

	public Card getPersonSuggestion() {
		return personSuggestion;
	}

	public Card getWeaponSuggestion() {
		return weaponSuggestion;
	}

	public Card getRoomSuggestion() {
		return roomSuggestion;
	}

	public Card getGuessResult() {
		return guessResult;
	}
}
