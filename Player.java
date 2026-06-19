import java.util.Arrays;
import java.util.Scanner;


public class Player {
	private String[] hand;
	private boolean hasUno;
	private boolean playerTurn; // might change into just a method
	private String playerName;
	private int maxHand;
	private String discardPile;
	private String playable[];
	
	// Constructor
	public void playerInit(int maxHand, String[] hand) {
		this.setPlayableCards();
		this.setName();
		this.setHand(maxHand); // Creating the size of the hand array
		this.startingDraw(hand); 
		this.setUno(); // declares Uno! when one card is left
		this.setPlayerTurn(false); // Intended to disable a "player" when they exceed max hand count
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
		this.maxHand = handSize;
		for(int i = 0; i < this.hand.length; i++) {
			this.hand[i] = "empty";
		}
	}
	
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
	
	public boolean setPlayerTurn(boolean isYourTurn) {
		playerTurn = isYourTurn; // probably useless?
		return playerTurn;
	}
	
	public void setDiscardPile(String discardPile) { // don't put in constructor
		this.discardPile = discardPile;
	}
	
	public void setPlayableCards() {
		this.playable = new String[maxHand];
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
	public boolean getPlayerTurn() { // might remove
		return this.playerTurn;
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
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	// player input functions
	
	
	// Main turn function 2nd draft
	public void playerTurn(String discardPile) { // maybe pre-draw from top deck when calling this
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
//		System.out.println("Card Total: " + cardTally);
//		System.out.println("Empty Slots: " + emptyTally);
		
		
		this.knockOutPlayer(emptyTally); // Checks the empty and outputs based on value
		this.hasUno(cardTally); // checks for Uno based on how many cards have been counted.
		this.setDiscardPile(discardPile); // had problems with values not passing through
		System.out.println("Discard Pile: " + this.discardPile);
		//this.validHand(this.discardPile); // -> pickCard() -> playCard() unfinished -> endTurn();
										  // OR -> drawCard() -> check if playable -> endTurn();
		
		
		if(this.validHand(this.discardPile) == true && hasUno) {
			sortHand(pickCard(this.playable));
			System.out.println("YOU WON UNO!");
		}
		else if(this.validHand(this.discardPile) == true) {
			// remove pickcard from this.hand
			sortHand(pickCard(this.playable));
			System.out.println(this.playerName + "'s hand: " + Arrays.toString(this.hand));
		}
		else {
			System.out.println("No playable cards. Call Draw Card method");
			drawCard("green_4");
			System.out.println(this.playerName + "'s hand: " + Arrays.toString(this.hand)); // MARK FOR DELETE
			//validHandDraw();
		}
		
		
	}
	
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
	
	
	// used with pickCard() to display the playable cards
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
	public static String pickCard(String[] hand) {
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
	
	// 2nd call when a card is drawn
	public boolean validHandDraw(String drawnCard) {
		String[] dcMatch = drawnCard.split("_");
		String[] dpMatch = this.discardPile.split("_");
		if(dcMatch[0] == dpMatch[0] || dcMatch[1] == dpMatch[1]) {
			System.out.println("drawn card played");
			return true; // play card
		}
		return false; // end turn by letting the loop end
	}
	
	public void drawCard(String newCard) {
		for(int i = 0; i < this.hand.length; i++) {
			if(this.hand[i] == "empty") {
				this.hand[i] = newCard;
				break;
			}
		}
	}
	
	
	// intended for the skip turn card. should handle this from the main file
	// repurpose for endTurn()?
	public void passTurn() { // MARK FOR DELETE - METHOD
		playerTurn = false;
	}
	

	
	public void knockOutPlayer() { // General call. may be redundant or just remove them from main file
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
		if(emptySlots == 0) { 
			knockOutPlayer();
			return;
		}
		if(emptySlots <= 1) {
			System.out.println(playerName + " is in danger of a knockout!");
			return;
		}
	}
	

	public void winCheck() { // WORKS ATM
		if(this.hand[0] == "empty") {
			System.out.println(playerName + " has won Uno!");
			// something to end the game with
		}
	}
	
	// checks for Uno
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
