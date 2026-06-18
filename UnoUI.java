import java.util.Scanner;

// This class handles what player sees and player input
public class UnoUI {
	// Scanner for getting user input
	Scanner input = new Scanner(System.in);

	public void showWelcome() {
		System.out.println("===================");
		System.out.println("  Welcome to UNO!  ");
		System.out.println("===================");
	}

	// Shows the current card being played
	public void showTopCard(String card) {
		System.out.println();
		System.out.println("Current Card: " + card);
	}

	// Shows all the cards in the player's hand
	public void showHand(String[] hand) {
		System.out.println();
		System.out.println("Your Cards: ");
		for (int i = 0; i < hand.length; i++) {
			// Doesn't print empty card spaces
			if (hand[i] != null && hand[i] != "empty") {
				System.out.println((i + 1) + ". " + hand[i]);

			}
		}
	}

	// Show whose turn it is
	public void showTurn(String playerName) {
		System.out.println("It is " + playerName + "'s turn.");
	}

// Get the card choice from player
	public int getChoice() {
		System.out.println("Choose a card number or enter 0 to draw: ");
		int choice = input.nextInt();
		return choice;
	}

// Prints a message when a card is played
	public void cardPlayed(String card) {
		System.out.println("Card Played: " + card);
	}

// Prints when the player needs to draw
	public void drawCard() {
		System.out.println("Draw a card ");
	}

// Shows the winner
	public void showWinner(String playerName) {
		System.out.println(playerName + " won UNO!");
	}
}
