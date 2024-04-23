/* ClueGame class creates the GUI of the game. It contains the known cards panel, gameControl panel, and board panel which all form
 * to make the game GUI. 
 * Authors: Jordan Lam, Danny Nguyen
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import javax.sound.sampled.*;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame implements MouseListener, ActionListener{
	private Board board;
	private BoardPanel boardPanel;
	private CardsPanel cardsPanel;
	private GameControlPanel gameControl;
	private int rollNumber;
	private int playerNum;
	private static final int MAX_PLAYER = 5;
	private Player currPlayer;
	private Random random;
	private int firstTurn;
	private Clip clip;
	
	public ClueGame() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		board = Board.getInstance();
		boardPanel = new BoardPanel();
		cardsPanel = new CardsPanel();
		gameControl = new GameControlPanel();
		this.random = new Random();
		this.firstTurn = 0;
		gameControl.setPreferredSize(new Dimension(900, 130));
		cardsPanel.setPreferredSize(new Dimension(243, getHeight()));
		
		gameControl.getSubButtonTop1().addActionListener(this);
		gameControl.getSubButtonTop2().addActionListener(this);

		add(boardPanel, BorderLayout.CENTER);
		add(gameControl, BorderLayout.SOUTH);
		add(cardsPanel, BorderLayout.EAST);
		
	    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/data/Cyber_Theme.wav"));
	    clip = AudioSystem.getClip();
	    clip.open(audioInputStream);
	}	
	
	/*
	 * this function will begin the game
	 */
	public void startGame() {
		startMusic();
		addMouseListener(this);
		this.playerNum = 0;
		
		// finding human player to begin game logic
		for(Player player: board.getPlayers()) {
			if(player instanceof HumanPlayer) {
				this.currPlayer = player;
				this.showPlayerHand(player);
				break;
			} 
			else {
				playerNum += 1;
			}
		}
		this.nextButton();
	}
	
	/*
	 * Show player hand
	 */
	public void showPlayerHand(Player player) {
		int numRoom = 0;
		int numPeople = 0;
		int numWeapon = 0;
		
		// get player hand
		ArrayList<Card> hand = player.getHand();
		for (Card card: hand) {
			if (card.getType() == CardType.ROOM) {
				cardsPanel.update(CardsPanel.getInHandRoom(), card, player.getColor());
				numRoom += 1;
			}
			else if (card.getType() == CardType.PERSON) {
				cardsPanel.update(CardsPanel.getInHandPeople(), card, player.getColor());
				numPeople += 1;
			}		
			else if (card.getType() == CardType.WEAPON) {
				cardsPanel.update(CardsPanel.getInHandWeapon(), card, player.getColor());
				numWeapon += 1;
			}	
		}
		
		// show if player has no room, people, or weapon card 
		if (numRoom == 0) {
			cardsPanel.update(CardsPanel.getInHandRoom(), new Card("None", CardType.ROOM), "White");
		}
		if (numPeople == 0) {
			cardsPanel.update(CardsPanel.getInHandPeople(), new Card("None", CardType.PERSON), "White");
		}
		if (numWeapon == 0) {
			cardsPanel.update(CardsPanel.getInHandWeapon(), new Card("None", CardType.WEAPON), "White");
		}
	}
	
	/*
	 * this button simulates what a next button should do 
	 */
	public void nextButton() {
		if(firstTurn != 0) {
			if(!currPlayer.getPlayerFinished()) {
				JOptionPane.showMessageDialog(this, "Finish the Current Turn!");
				return;
			}
			
			//resets painted target cells
			this.clearTurn();
			this.nextPlayerNum();
			currPlayer = board.getPlayers().get(playerNum);
			if(currPlayer.getOut()) {
				while(currPlayer.getOut()) { // i hate while loops
					this.nextPlayerNum();
					currPlayer = board.getPlayers().get(playerNum);
				}
			}
			// clear game control
			gameControl.setGuess("");
			gameControl.setGuessResult("");
		} 
		else {
			firstTurn += 1;
		}
		
		// finding current location to calc targets
		int[] loc = currPlayer.getLocation();
		BoardCell currCell = board.getCell(loc[0],loc[1]);
		
		// rolling 
		int roll = this.getRollNumber();

		// calc targets
		board.calcTargets(currCell, roll);
		Set<BoardCell> targets = board.getTargets();
		// changing the game control panel
		gameControl.setTurn(board.getPlayers(), playerNum, rollNumber);
		
		// painting target cells when human players turn
		if(currPlayer instanceof HumanPlayer) {
			for(BoardCell cell : targets) {
				cell.setisTarget(true);
			}
			currPlayer.isPlayerFinished(false);
			repaint();
			
		} 
		else {
			//type cast so can use computer player functions
			ComputerPlayer player = (ComputerPlayer)currPlayer;
			BoardCell selectedCell = player.selectTarget(targets);
			
			// set original location as not occupied
			BoardCell cell = board.getCell(player.getRow(), player.getCol());
			cell.setOccupied(false);
			
			if(player.isSolutionFlagged() == true) {
				Solution acc = player.getAccusation();
				JOptionPane.showMessageDialog(this, player.getName() + " accuses the murderer is " + acc.getPerson() + "in " + acc.getRoom() + " with a " + acc.getWeapon());
				if(board.checkAccusation(acc.getRoom().getCardName(), acc.getPerson().getCardName(), acc.getWeapon().getCardName())) {
					JOptionPane.showMessageDialog(this, player.getName() + "You have Won!");
					dispose();
				}else {
					JOptionPane.showMessageDialog(this, player.getName() + "is out of the game!");	
					player.setOut(true);
				}
			} 
			
			if(player.getAccusationChance() >= .80) {
				Solution acc = player.getAccusation();
				JOptionPane.showMessageDialog(this, player.getName() + " accuses the murderer is " + acc.getPerson() + "in " + acc.getRoom() + " with a " + acc.getWeapon());
				if(board.checkAccusation(acc.getRoom().getCardName(), acc.getPerson().getCardName(), acc.getWeapon().getCardName())) {
					JOptionPane.showMessageDialog(this, player.getName() + "You have Won!");
					dispose();
				}else {
					JOptionPane.showMessageDialog(this, player.getName() + "is out of the game!");	
					player.setOut(true);
				}
			}
			
			player.move(selectedCell.getRow(), selectedCell.getCol());	
			
			if(selectedCell.isRoomCenter()) {
				Solution suggestion = player.createSuggestion();
				
				Card room = suggestion.getRoom();
				Card person = suggestion.getPerson();
				Card weapon = suggestion.getWeapon();
				String roomName = room.getCardName();
				String personName = person.getCardName();
				String weaponName = weapon.getCardName();
				gameControl.setGuess(roomName + ", " + personName + ", " + weaponName);
				JOptionPane.showMessageDialog(this, player.getName() + " suggests the murderer is " + personName + " in " + roomName + " with a " + weaponName);
				
				for (Player murder: board.getPlayers()) {
					if(murder.getName() == personName) {
						murder.move(selectedCell.getRow(), selectedCell.getCol());
						break;
					}
				}
				repaint();
				// get guess result
				Card guessResult = board.handleSuggestion(room, person, weapon, player);
				
				if( guessResult != null) {
					player.updateHand(guessResult);
					JOptionPane.showMessageDialog(this, "This suggestion was disproven!");
					gameControl.setGuessResult("Hidden");
				} else {
					JOptionPane.showMessageDialog(this, "This suggestion was not disproven!");
					gameControl.setGuessResult("Not disproven");
					boolean hasRoom = false;
					for(Card card: player.getHand()) {
						if(card.getCardName()==roomName) {
							hasRoom = true;
						}
					}
					if(!hasRoom) {
						player.setSolutionFlagged(true);
						player.setAccusation(suggestion);
					}
				}
				
			}
			
			// set new location after move to occupied
			selectedCell.setOccupied(true);
			
			currPlayer.isPlayerFinished(true);
			repaint();
		}
	}

	// will reset marked cells
	public void clearTurn() {
		Set<BoardCell > targets = board.getTargets();
		for(BoardCell cell: targets) {
			cell.setisTarget(false);
		}
	}
	
	// moves to next player number
	public void nextPlayerNum() {
		playerNum += 1;
		if (playerNum > MAX_PLAYER) {
			playerNum = 0;
		}
	}

	
	/*
	 * Make an accusation if the player is user
	 */
	public void accusationButton() {
		if (currPlayer instanceof HumanPlayer) {  
			// open a dialog
			AccusationDialog dialog = new AccusationDialog(this, board);
			dialog.setVisible(true);

			// check if player wins or loses
			boolean playerAnswer = dialog.isWinOrLose();
			boolean submitted = dialog.isSubmittedAnswer();
			if (playerAnswer == true && submitted == true) {
				JOptionPane.showMessageDialog(this, "You have Won!");
				dispose();
			}
			else if (playerAnswer == false && submitted == true){
				JOptionPane.showMessageDialog(this, "You have Lost!");
				dispose();
			} 	
		}
		else {
			JOptionPane.showMessageDialog(this, "It's not your turn!");
		}
	}
	
	/*
	 * Perform task when mouse is clicked
	 */
	@Override 
	public void mousePressed(MouseEvent e) {
		// will do nothing if mouse is pressed when computers turn or player is finished with turn
		if (!(currPlayer instanceof ComputerPlayer) && !currPlayer.getPlayerFinished()) {
			BoardCell selectedCell = null;
			for(int i = 0; i < board.getNumRows(); i++) {
				for(int j = 0; j < board.getNumColumns(); j++) {
					if(board.getCell(i, j).matchLocation(e.getX(), e.getY())) {
						selectedCell = board.getCell(i, j);
						break;
					}
				}
			}

			if (selectedCell != null && selectedCell.getisTarget()) {
				// player moves
				currPlayer.isPlayerFinished(true);
				
				// set original location as not occupied
				BoardCell cell = board.getCell(currPlayer.getRow(), currPlayer.getCol());
				cell.setOccupied(false);
				
				currPlayer.move(selectedCell.getRow(), selectedCell.getCol());
				
				// set new location after move to occupied
				selectedCell.setOccupied(true);
				
				// clear turn
				this.clearTurn();
				// if target selected is a room suggestion dialog shows up
				if (selectedCell.isRoom()) {
					SuggestionDialog suggestDialog = new SuggestionDialog(this, board, selectedCell, currPlayer);
					suggestDialog.setVisible(true);
					
					if (suggestDialog.isSubmitted()) {
						// show guess on control panel
						String room = suggestDialog.getRoomSuggestion().getCardName();
						String person = suggestDialog.getPersonSuggestion().getCardName();
						String weapon = suggestDialog.getWeaponSuggestion().getCardName();
						gameControl.setGuess(room + ", " + person + ", " + weapon);
						
						for (Player murder: board.getPlayers()) {
							if(murder.getName() == person) {
								murder.move(selectedCell.getRow(), selectedCell.getCol());
								break;
							}
						}

						// get guess result
						Card guessResult = suggestDialog.getGuessResult();
						// if there is a guess show result and color of player with card
						if (guessResult != null) {
							gameControl.setGuessResult(guessResult.getCardName());
							// get player with card to get color
							ArrayList<Player> players = board.getPlayers();
							Player playerWithCard = null;
							for (Player player: players) {
								if (player.getHand().contains(guessResult)) {
									playerWithCard = player;
								}
							}
							repaint();
							// show guess result in card panel
							if (guessResult.getType() == CardType.ROOM) {
								cardsPanel.update(CardsPanel.getSeenRoom(), guessResult, playerWithCard.getColor());
							}
							else if (guessResult.getType() == CardType.PERSON) {
								cardsPanel.update(CardsPanel.getSeenPeople(), guessResult, playerWithCard.getColor());
							}		
							else if (guessResult.getType() == CardType.WEAPON) {
								cardsPanel.update(CardsPanel.getSeenWeapon(), guessResult, playerWithCard.getColor());
							}
						}
						// else no one has card
						else {
							gameControl.setGuessResult("None");
						}
					}
				}
			} 
			else {
				JOptionPane.showMessageDialog(this, "You can't move here!");
			}
			repaint();
		}
	}	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// NO IMPLEMENTATION
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// NO IMPLEMENTATION
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// NO IMPLEMENTATION
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// NO IMPLEMENTATION
		
	}

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == gameControl.getSubButtonTop1()) {
    		accusationButton();
    	}
    	else if (e.getSource() == gameControl.getSubButtonTop2()) {
    		nextButton(); 
    	}
    }	

	// dice roller
	public int getRollNumber() {
        int randomRoll = random.nextInt(6) + 1;
        this.rollNumber = randomRoll;
        return randomRoll;
	}
	
	public void startMusic() {
	    clip.loop(Clip.LOOP_CONTINUOUSLY);
	    clip.start();
	}
	
	/* 
	 * Initialize board and begin game
	 */
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		ClueGame frame = new ClueGame();
		frame.setTitle("Clue Game");
		frame.setSize(925, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		// showing the welcome screen
		JOptionPane.showMessageDialog(frame, "You are Cipher. \n" + "Can you find the solution \n" + "before the Computer players.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		frame.startGame();
	}
}
