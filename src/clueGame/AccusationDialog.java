/* AccusationDialog class displays a dialog that allows the player to make a accusation.
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

public class AccusationDialog extends JDialog {
    private JComboBox<String> roomComboBox, personComboBox, weaponComboBox;
    private JButton submitButton, cancelButton;
    private Board board;
    private boolean winOrLose;
    private boolean submittedAnswer = false;
    

    public AccusationDialog(JFrame parent, Board board) {
        super(parent, "Make an Accusation", true);
        this.board = board;
      
        // Initialize JComboBox for room selection
		roomComboBox = createComboBox(board.getRoomCards());
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
        panel.add(roomComboBox);
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
        
        // Get the players selected answer and see if its correct or wrong
        submitButton.addActionListener(e -> {
        	// get player selected answer
            String selectedRoom = (String) roomComboBox.getSelectedItem();
            String selectedPerson = (String) personComboBox.getSelectedItem();
            String selectedWeapon = (String) weaponComboBox.getSelectedItem();

            // get the solution answer
            Solution answer = board.getSolution();
            String roomAnswer = answer.getRoom().getCardName();
            String personAnswer = answer.getPerson().getCardName();
            String weaponAnswer = answer.getWeapon().getCardName();
            
            // Check if answer by player is correct
            if (selectedRoom == roomAnswer && selectedPerson == personAnswer && selectedWeapon == weaponAnswer) {
            	winOrLose = true;
            	submittedAnswer = true;
            }
            else {
            	winOrLose = false;
            	submittedAnswer = true;
            }
            
            // Close the dialog if needed
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

	public boolean isWinOrLose() {
		return winOrLose;
	}

	public boolean isSubmittedAnswer() {
		return submittedAnswer;
	}

}