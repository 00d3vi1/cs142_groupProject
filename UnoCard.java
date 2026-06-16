

import java.util.Random;

//Team Members: Collins Kariuki, Ptolemaeus McClusky
//Christopher Miller, Alisa Panchenko
//This class handles the deck of Uno cards
public class UnoCard {
	// fields
	private String[] cards;
	private int cardsInDeck;
	private boolean isShuffled;
	private int totalCardsPulled;
	// constructors

	public UnoCard() {
		this.cards = new String[112];
		this.cardsInDeck = 0;
		this.totalCardsPulled = 0;
	}

	// getters

	public String[] getCards() {
		return cards;
	}

	public void setCards(String[] cards) {
		this.cards = cards;
	}

	public int getCardsInDeck() {
		return cardsInDeck;
	}

	public void setCardsInDeck(int cardsInDeck) {
		this.cardsInDeck = cardsInDeck;
	}

	public boolean isShuffled() {
		return isShuffled;
	}

	public void setShuffled(boolean isShuffled) {
		this.isShuffled = isShuffled;
	}

	public int getTotalCardsPulled() {
		return totalCardsPulled;
	}

	public void setTotalCardsPulled(int totalCardsPulled) {
		this.totalCardsPulled = totalCardsPulled;
	}

	// method to add a card to deck
	public void addCard(String newCard) {
		System.out.println("Adding card at  " + this.cardsInDeck + " : " + newCard);
		this.cards[this.cardsInDeck] = newCard;
		this.cardsInDeck++;
	}

	/// method to create the deck
	public void createDeck() {
		// Arrays for the cards
		String[] colors = { "red", "yellow", "green", "blue" };
		String[] actionCards = { "skip", "reverse", "draw 2" };

		// loops through the colors

		for (int i = 0; i < colors.length; i++) {
			String color = colors[i];
			// Accounting for zero
			this.addCard(color + "0");

			for (int num = 1; num <= 9; num++) {
				this.addCard(color + "_" + num);
				this.addCard(color + "_" + num);
			}
			// adding action cards
			for (int j = 0; j < actionCards.length; j++) {
				this.addCard(color + "_" + actionCards[j]);
				this.addCard(color + "_" + actionCards[j]);
			}

		}
		// adding the wild cards and draw 4
		for (int i = 0; i < 4; i++) {
			this.addCard("wild card");
			this.addCard("wild draw4");
		}
	}

	// deals the cards to the players of the game
	public String[] dealHand(int handCards) {
		String[] hand = new String[handCards];
		for (int i = 0; i < handCards; i++) {
			hand[i] = this.drawCard();
		}
		return hand;
	}

	// shuffles the deck
	public void shuffleDeck() {
		Random r = new Random();

		for (int i = 0; i < this.cardsInDeck; i++) {
			if (this.cards[i] != null) {

				int newIndex = r.nextInt(this.cardsInDeck);

				String temp = this.cards[i];

				this.cards[i] = this.cards[newIndex];
				this.cards[newIndex] = temp;
			}

		}

	}

	//calculates the total cards left
	public int totalCardsLeft() {
		int total = 0;

		for (int i = 0; i < this.cards.length; i++) {

			if (this.cards[i] != null) {
				total++;
			}
		}
		return total;
	}

	// draws card from the top
	public String drawCard() {

		for (int i = 0; i < this.cards.length; i++) {

			if (this.cards[i] != null) {
				String topCard = this.cards[i];
				this.cards[i] = null;
				this.cardsInDeck--;
				return topCard;

			}
		}
		return null;
	}
	
}
	