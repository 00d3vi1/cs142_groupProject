import java.util.Arrays;
import java.util.Scanner;
import java.io.*;


public class Player {
	private String[] hand;
	private boolean hasUno;
	private boolean isKnockedOut; // might change into just a method
	private String playerName;
	private int maxHand;
	private String discardPile;
	private String playable[];
	private String discardOut;	
	
	final static String ANSI_YELLOW = "\u001B[33m";
	final static String ANSI_GREEN = "\u001B[32m";
	final static String ANSI_RED = "\u001B[31m";
	final static String ANSI_BLUE = "\u001B[36m";
	
	
	
	// Constructor
	public void playerInit(int maxHand, String[] hand) {
		this.setPlayableCards();
		this.setName();
		this.setHand(maxHand); // Creating the size of the hand array
		this.startingDraw(hand); // initializing the starting hand
		this.setUno(); // declares Uno! when one card is left
		this.setKnockedOut(false); // Intended to disable a "player" when they exceed max hand count
	}
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	// Setters
	
	// fills the this.hand[] with values from the string[] that is passed into the setter		
	public void startingDraw(String[] hand) { 
		for(int i = 0; i < hand.length; i++) {
			this.hand[i] = hand[i];
		}
	}
		
	// creates the size of the array depending on a max size and fills it with "empty"
	// to make override toString() functional. 
	public void setHand(int handSize) {
		this.hand = new String[handSize]; 
		this.maxHand = handSize;
		for(int i = 0; i < this.hand.length; i++) {
			this.hand[i] = "empty";
		}
	}
	
	// takes user input to create the player name
	public void setName() {
		Scanner nameInput = new Scanner(System.in);
		System.out.print("Enter your Name: ");
		String playerName = nameInput.nextLine();
		while(playerName == null || playerName.length() <= 0) {
			System.out.print("Please enter a valid name: ");
			playerName = nameInput.nextLine();
		}
		this.playerName = playerName;
	}
	
	public void setUno() {
		this.hasUno = false;
	}
	
	public boolean setKnockedOut(boolean koStatus) {
		this.isKnockedOut = koStatus; // probably useless?
		return koStatus;
	}
	
	public void setDiscardPile(String discardPile) { // don't put in constructor
		this.discardPile = discardPile;
	}
	
	public void setPlayableCards() {
		this.playable = new String[maxHand];
	}
	
	public void setDiscardOut(String discardOut) {
		this.discardOut = discardOut;
	}
	
	public static void setColor(String color) {
		System.out.print(color);
	}
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	

	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	// Getters
	public String[] getHand() {
		return this.hand;
	}
	public String getName() {
		return this.playerName;
	}
	public boolean getUno() {
		return this.hasUno;
	}
	public boolean getIsKnockedOut() { // might remove
		return this.isKnockedOut;
	}
	public int getMaxHand() {
		return this.maxHand;
	}
	public String getDiscardPile() {
		return this.discardPile;
	}
	public String[] getPlayable() {
		return this.playable;
	}
	public String getDiscardOut() {
		return this.discardOut;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	// player input functions
	
	
	// Handles the player Turn Loop
	public void playerTurn(String discardPile, String draw) { // maybe pre-draw from top deck when calling this
		int emptyTally = 0;
		int cardTally = 0;
		
		for(int i = 0; i < this.hand.length; i++) {
			if(this.hand[i] == "empty") {
				emptyTally++;
			}
			if(this.hand[i] != "empty") {
				cardTally++;
			}
		}
		
		// debug text
//		System.out.println("Card Total: " + cardTally);
//		System.out.println("Empty Slots: " + emptyTally);
		
		this.knockOutPlayer(emptyTally); // Checks the empty and outputs based on value
		this.hasUno(cardTally); // checks for Uno based on how many cards have been counted.
		this.setDiscardPile(discardPile); // had problems with values not passing through
		setColor(ANSI_GREEN);
		System.out.println("Discard Pile: " + this.discardPile);
		
		
		// win condition
		if(this.validHand(this.discardPile) == true && hasUno) {
			sortHand(pickCard(this.playable));
			System.out.println(this.playerName + " WON UNO!");
		}
		// play card if you have matching cards
		else if(this.validHand(this.discardPile) == true) {
			// remove pickcard from this.hand
			sortHand(pickCard(this.playable));
			organizeHand();
			System.out.println(this.playerName + "'s hand: " + Arrays.toString(this.hand));
		}
		// draw card and check if the drawn card is playable
		else {
			System.out.println("No playable cards. Drawing card");
			//String draw = "green_4"; // mark for delete

			if(validHandDraw(draw)) {
				this.setDiscardOut(draw);
			}
			else {
				drawCard(draw);
			}
			organizeHand();
			System.out.println(ANSI_BLUE + this.playerName + "'s hand: " + Arrays.toString(this.hand)); // MARK FOR DELETE
			setColor(ANSI_GREEN);
		}
		
		
	}
	
	// removes card from hand as a result of "playing" it
	public void sortHand(String removeCard) {
		this.setDiscardPile(removeCard);
		for(int i = 0; i < this.hand.length; i++) {
			if(this.hand[i] == removeCard) {
				this.hand[i] = "empty";
				// shift everything to the left to fill the hole
				break;
			}
		}	
	}
	
	
	// used with pickCard() to display the playable cards for quality of life
	public static void getPlayableCards(String[] hand) {
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("Playable Cards: ");
		
		for(int i = 0; i < hand.length; i++) {
			if(hand[i] != null) {
				System.out.print((i + 1) + ") " + hand[i] + " ");
			}
		}
	}
	
	// Takes in the playable cards from the hand and allows the player to 
	// select a card via card slot number
	public String pickCard(String[] hand) {
		boolean validChoice = false;
		boolean numerical = false;
		int choiceCheck = -1;
		
		//System.out.println(hand[0]); // MARK FOR DELETE
		
		getPlayableCards(hand);
		
		System.out.println();
		System.out.print("\nSelect a card by entering the numbered card slot: ");
		
		Scanner chooseCard = new Scanner(System.in);
		String choice = chooseCard.nextLine();
		
		
		// checks if the player choice is both within bounds of the array and also 
		// a numerical value. Keeps asking for valid input until both conditions are true
		while(!numerical || !validChoice) {
			try {
	            choiceCheck = Integer.parseInt(choice);
	            numerical = true;
	        }
	        catch (NumberFormatException e) {
	        	getPlayableCards(hand);
	            System.out.print(choice + "- is not a valid choice. Please choose a valid card slot: ");
	            choice = chooseCard.nextLine();
	        }
			
			try {
				choice = hand[choiceCheck-1];
				validChoice = true;
			}
			catch (Exception e) {
				getPlayableCards(hand);
				System.out.print(choiceCheck + "- is not an option. Pick a valid card slot: ");	
				choice = chooseCard.nextLine();
				choiceCheck = Integer.parseInt(choice);
			}
		}
		
				
		chooseCard.close();
		// replace the return type to call either playCard() or drawCard()->playCard() or endTurn()
		this.setDiscardOut(choice);
		return choice;
	}

	// gives the player a list of playable options that is compared to the discard pile -> pickCard() 
	public boolean validHand(String discardPile) {
		String[] dpMatch = discardPile.split("_");
		String[] temp = new String[maxHand];
		int pTrack = 0;
		boolean validHand = false;;
		for(int i = 0; i < this.hand.length; i++) {
			if(this.hand[i].startsWith(dpMatch[0]) || this.hand[i].endsWith(dpMatch[1])) {
				temp[pTrack] = this.hand[i];
				pTrack++;
			}
		}
		if(pTrack > 0) {
			this.playable = temp;
			return validHand = true;
		}
		return validHand;
	}
	
	// 2nd call when a card is drawn to see if it's playable
	public boolean validHandDraw(String drawnCard) {
		String[] dcMatch = drawnCard.split("_");
		String[] dpMatch = this.discardPile.split("_");
		boolean match = false;
		
		for(int i = 0; i < dcMatch.length; i++) {
			if(dpMatch[i].contains(dcMatch[i])) {
				System.out.println(drawnCard + " drawn card played");
				match = true;
				break;
			}
		}
		return match;
	}
	
	// adds card to hand
	public void drawCard(String newCard) {
		for(int i = 0; i < this.hand.length; i++) {
			if(this.hand[i] == "empty") {
				this.hand[i] = newCard;
				break;
			}
		}
	}

	// When knocked out, the player is disabled
	public void knockOutPlayer() { 
		for(int i = 0; i < this.hand.length; i++) {
			if(hand[i] != "empty") {
				System.out.println(ANSI_RED + playerName + " has been knocked out!");
				this.setKnockedOut(true);
				break;
			}
		}
	}

	// Checks if there is an hand overload to knock out a player
	public void knockOutPlayer(int emptySlots) { 
		if(emptySlots == 0) { 
			knockOutPlayer();
			return;
		}
		if(emptySlots <= 1) {
			System.out.println(playerName + " is in danger of a knockout!");
			return;
		}
	}
	
	public void winCheck() { 
		if(this.hand[0] == "empty") {
			System.out.println(playerName + " has won Uno!");
			// something to end the game with
		}
	}
	
	// checks if player has Uno and 
	public void hasUno(int cardAmount) {
		if(cardAmount > 1) {
			this.hasUno = false;
			//System.out.println("No Uno, keep going foo"); // MARK FOR DELETE
		}
		if(cardAmount == 1 && this.hand[1] == "empty") {
			this.hasUno = true;
			System.out.println(playerName + " has Uno!");
		}
		
	}
	
	public void organizeHand() {
		String temp = "";
		int emptyIndex = -1;
		boolean firstEmpty = false;
		
		for(int i = 0; i < this.hand.length; i++) {
			if(this.hand[i] == "empty" && firstEmpty == false) {
				emptyIndex = i;
				firstEmpty = true;
			}
			if(firstEmpty == true && this.hand[i] != "empty") {
				temp = this.hand[i];
				this.hand[emptyIndex] = temp;
				emptyIndex++;
			}
		
		}
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	
	
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	// debugging methods
	
	// Testing win by Uno
	public void clearHand() {
		for(int i = 0; i < this.hand.length; i++) {
			this.hand[i] = "empty";
		}
		winCheck();
	}
	
	// "draw" card test
		public String randomCard() {
			String drawnCard = "green_3";
			return drawnCard;
		}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	
	
	@Override
	public String toString() {
		return this.playerName + "'s Hand: " + Arrays.toString(this.hand);
	}
	
	
}
