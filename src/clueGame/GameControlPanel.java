package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel{
	private JPanel mainPanel;
	private JPanel subPanelTop;
	private JPanel subPanelTop1;
	private JTextField who;
	private JTextField roll;
	private JPanel subPanelTop2;
	private JButton subButtonTop1;
	private JButton subButtonTop2;
	/*
	 * Constructor for the panel
	 */
	public GameControlPanel() {
		// Create a layout with 2 rows
        // Create a layout with 2 rows and 0 columns for the main panel
        this.mainPanel = new JPanel(new GridLayout(2, 0));

        // Create the first sub-panel with a GridLayout of 1 row and 4 columns
        this.subPanelTop = new JPanel(new GridLayout(1, 4));
        
        // Add components to the first sub-panel
        this.subPanelTop1 = new JPanel(new GridLayout(2,0));
        subPanelTop1.add(new JLabel("<html><b>Whose turn?</b></html>"));
        this.who = new JTextField(10);
        who.setEditable(false);
        subPanelTop1.add(who);
        
        
        this.subPanelTop2 = new JPanel();
        this.roll = new JTextField(5);
        roll.setEditable(false);
        subPanelTop2.add(new JLabel("<html><b>Roll:</b></html>"));
        subPanelTop2.add(roll);
        
        this.subButtonTop1 = new JButton("<html><b>Make Accusation</b></html>");
        this.subButtonTop2 = new JButton("<html><b>NEXT!</b></html>");
        subButtonTop1.setBackground(Color.cyan);
        subButtonTop2.setBackground(Color.cyan);
        subButtonTop1.setForeground(Color.YELLOW);
        subButtonTop2.setForeground(Color.YELLOW);
        subButtonTop1.setPreferredSize(new Dimension(50,50));
        subButtonTop2.setPreferredSize(new Dimension(50,50));
        subButtonTop1.setOpaque(true);
        subButtonTop2.setOpaque(true);
        subButtonTop1.setBorderPainted(false);
        subButtonTop2.setBorderPainted(false);
        
        
        subPanelTop.add(subPanelTop1);
        subPanelTop.add(subPanelTop2);
        subPanelTop.add(subButtonTop1);
        subPanelTop.add(subButtonTop2);
        
        mainPanel.add(subPanelTop);

        // Add the main panel to this GameControlPanel
        add(mainPanel);

	}
	
	
	private void setTurn(ComputerPlayer computerPlayer, int i) {
		if (computerPlayer.getColor().equals("Blue")) {
			who.setBackground(Color.BLUE);
			who.setText(computerPlayer.getName());
			roll.setText(String.valueOf(i));
		} else if (computerPlayer.getColor().equals("Green")) {
			who.setBackground(Color.green);
			who.setText(computerPlayer.getName());
			roll.setText(String.valueOf(i));
		} else if (computerPlayer.getColor().equals("Red")) {
			who.setBackground(Color.red);
			who.setText(computerPlayer.getName());
			roll.setText(String.valueOf(i));
		} else if (computerPlayer.getColor().equals("Orange")) {
			who.setBackground(Color.orange);
			who.setText(computerPlayer.getName());
			roll.setText(String.valueOf(i));
		} else if (computerPlayer.getColor().equals("Yellow")) {
			who.setBackground(Color.yellow);
			who.setText(computerPlayer.getName());
			roll.setText(String.valueOf(i));
		} else if (computerPlayer.getColor().equals("Pink")) {
			who.setBackground(Color.pink);
			who.setText(computerPlayer.getName());
			roll.setText(String.valueOf(i));
		} else if (computerPlayer.getColor().equals("Magenta")) {
			who.setBackground(Color.magenta);
			who.setText(computerPlayer.getName());
			roll.setText(String.valueOf(i));
		} else if (computerPlayer.getColor().equals("White")) {
			who.setBackground(Color.white);
			who.setText(computerPlayer.getName());
			roll.setText(String.valueOf(i));
		}
		
	}
	
	private void setGuess(String string) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", "Orange", 0, 0), 5);
		//panel.setGuess( "I have no guess!");
		//panel.setGuess( "So you have nothing?");
	}


}
