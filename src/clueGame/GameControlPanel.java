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
	private JTextField who;
	JPanel mainPanel;
	JPanel subPanelTop;
	JPanel subPanelTop1;
	JPanel subPanelTop2;
	JButton subButtonTop1;
	JButton subButtonTop2;
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
        subPanelTop1.add(new JTextField(10));
        
        this.subPanelTop2 = new JPanel();
        subPanelTop2.add(new JLabel("<html><b>Roll:</b></html>"));
        subPanelTop2.add(new JTextField(5));
        
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
		// TODO Auto-generated method stub
		
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
		//panel.setTurn(new ComputerPlayer( "Col. Mustard", "orange", 0, 0), 5);
		//panel.setGuess( "I have no guess!");
		//panel.setGuess( "So you have nothing?");
	}


}
