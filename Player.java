import java.util.Arrays;


public class Player {
	private String[] hand;
	private boolean hasUno;
	private boolean validPlayer;
	private boolean playerTurn; // might change into just a method
	private String playerName;
	private int maxHand;
	private String discardPile;
	
	// Constructor
	public void playerInit(int maxHand, String[] hand, String playerName) {
		this.setName(playerName);
		this.setHand(maxHand); // Creating the size of the hand array
		this.startingDraw(hand); 
		this.setUno(); // declares Uno! when one card is left
		this.setPlayerTurn(false); // Intended to disable a "player" when they exceed max hand count
		//this.validPlayer(); // Set to true to allow "player" control
	}
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	// Setters
	
	public void startingDraw(String[] hand) { 
		// fills the this.hand[] with values from the string[] that is passed into the setter		
		for(int i = 0; i < hand.length; i++) {
			this.hand[i] = hand[i];
		}
	}
		
	public void setHand(int handSize) {
		// creates the size of the array depending on a max size and fills it with "empty"
		// to make override toString() functional. 
		this.hand = new String[handSize]; 
		setMaxHand(handSize);
		for(int i = 0; i < this.hand.length; i++) {
			this.hand[i] = "empty";
		}
	}
	
	public void setMaxHand(int maxHand) {
		this.maxHand = maxHand;
	}
	
	public void setName(String name) {
		this.playerName = name;
	}
	
	public void setUno() {
		this.hasUno = false;
	}
	
	public boolean setPlayerTurn(boolean isYourTurn) {
		playerTurn = isYourTurn; // probably useless?
		return playerTurn;
	}
	
	public void setValidPlayer() {
		this.validPlayer = true;
	}
	
	public void setDiscardPile(String discardPile) { // don't put in constructor
		this.discardPile = discardPile;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	

	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	// Getters
	public String[] getHand() {
		return this.hand;
	}
	public boolean getUno() {
		return this.hasUno;
	}
	public boolean getPlayerTurn() { // might remove
		return this.playerTurn;
	}
	public boolean getvalidPlayer() {
		return this.validPlayer;
	}
	public int getMaxHand() {
		return this.maxHand;
	}
	public String getDiscardPile() {
		return this.discardPile;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	// player input functions
	
	
	// Main turn function 2nd draft
	public void playerTurn(String discardPile) {
		int emptyTally = 0;
		int cardTally = 0;
		
		// catches the remaining cards and tracks how much space is left until a
		// knockout (aka: exceeding max hand amount to have a hard cap to prevent long games)
		for(int i = 0; i < this.hand.length; i++) {
			if(this.hand[i] == "empty") {
				emptyTally++;
			}
			if(this.hand[i] != "empty") {
				cardTally++;
			}
		}
		
		// debug text
		System.out.println("Card Total: " + cardTally);
		System.out.println("Empty Slots: " + emptyTally);
		
		
		knockOutPlayer(emptyTally); // Checks the empty and outputs based on value
		hasUno(cardTally); // checks for Uno based on how many cards have been counted.
		validHand(discardPile);
		
		// play card
	}
	

	public void validHand(String discardPile) { // split into discardPileColor and discardPileNum?
		// Before going through this loop, we need to check the win conditions. 
		// i.e. 0 cards left or max cards reached.
		for(int i = 0; i < this.hand.length; i++) {
			// need to test .endsWith() to pull the number value
			if(this.hand[i].startsWith(discardPile) || this.hand[i].endsWith(discardPile)){
				System.out.println("valid hand, pick a card");
				// choose a valid card to play and put value "on top" of the discard pile
				// then goes to next player's turn from the playCard method
				this.playCard();
				break;
			}
			// cards at the moment stack to the left towards 0 index. Draws card when it first
			// encounters an "empty" in String[]. Should only be reachable if there is no match
			// with the discard pile.
			if(this.hand[i].contains("empty")) {
				this.hand[i] = randomCard();	
				System.out.println("Card drawn " + this.hand[i] + " into hand.");
				// re-validate and attempt to play another card one more time.
				break;
			}
		}
	}
	
	// intended for the skip turn card
	public void passTurn() { // WAITING TO EDIT
		playerTurn = false;
	}
		
	// EDIT PRIO
	// pass another String[] that is the size of of drawNum
	public void drawXCards(int drawNum, String card1, String card2, int emptySlot) { 
		// Intended to be a flexible drawCard method to be reusable with draw, draw 2, draw 4
		// when it is complete
		boolean inBounds = emptySlot >= drawNum;
		if(inBounds) {
			for(int i = 0; i < this.hand.length; i++) {
				// adjust this to account for String[].length for a flexible draw method
				if(this.hand[i] == "empty" && this.hand[i+1] == "empty") {
					this.hand[i] = card1;
					this.hand[i+1] = card2;
				}
			}
		}
	}
	
	// EDIT ASAP
	public void playCard() {
		System.out.println("Pretending to play a card");
		// use the hasUno bool to initiate the win condition
	}
	
	
	public void knockOutPlayer() { // General call. may be redundant
		for(int i = 0; i < this.hand.length; i++) {
			if(hand[i] != "empty") {
				System.out.println(playerName + " has been knocked out!");
				break;
				// Needs to disable the player involved. So that boolean may be needed. 
			}
		}
	}

	// Checks if there is an hand overload to knock out a player
	public void knockOutPlayer(int emptySlots) { 
		if(emptySlots == 0) { // AND no playable cards...
			knockOutPlayer();
			return;
		}
		if(emptySlots <= 1) {
			System.out.println(playerName + " is in danger of a knockout!");
			return;
		}
	}
	

	// call through validPlayer
	// call after playerChoice AND if player hasUno = true. 
	// if I don't want to make a sorter, I can probably rely on the tallies. 
	public void winCheck() { // WORKS ATM
		if(this.hand[0] == "empty") {
			validPlayer = false; // i dont remember what this is for
			System.out.println(playerName + " has won Uno!");
		}
	}
	
	public void hasUno(int cardAmount) {
		// calls with cardTally and checks index. Currently hard code the index
		if(cardAmount > 1) {
			this.hasUno = false;
			System.out.println("No Uno, keep going foo");
		}
		if(cardAmount == 1 && this.hand[1] == "empty") {
			this.hasUno = true;
			System.out.println(playerName + " Uno!");
		}
		
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	// debugging methods
	
	// debugging for empty hand
	public void clearHand() {
		for(int i = 0; i < this.hand.length; i++) {
			this.hand[i] = "empty";
		}
		winCheck();
	}
	
	// debugging for draw
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
