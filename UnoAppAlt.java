import java.util.Random;
import java.util.Scanner;

public class UnoAppAlt {
	
	//each card# 1-108 has color & number value associated
	public static int[] colorIndex = new int[108]; //color index, with set value for each card
	public static int[] numIndex = new int[108]; //number index, with set value for each card
	public static int[] pile = new int[108]; //establishes pile (where cards are played to)
	public static Random r = new Random(); //establishes random
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); //establishes scanner
		int players = -1;
		while(players < 2 || players > 8) {
			System.out.println("From 1-7, how many enemies would you like?");
			String playerInput = scanner.nextLine();
			players = tryParseInt(playerInput) + 1;
			if(players < 2 || players > 8) {
				System.out.println("Please pick a number between 1 and 7!");
			}
		}
		
		int[] deck = new int[108]; //establishes main deck
		int[][] hands= new int[players][20]; //establishes player/opponent hands
		int[] playerHand = hands[0]; //effectively names player's hand for sake of clarity
		int[] graveyard = new int[players]; //keeps track of dead opponents (1 at their hands[] value)
		
		//method calls for debugging
		//deckTest(deck);
		//deckTest(colorIndex);
		//deckTest(numIndex);
		
		//sets up deck and pile
		for(int i = 0; i < 108; i++) {
			deck[i] = i + 1;
		}
		deckSetup(deck);
		drawAmount(pile, deck, 1);
		if(colorIndex[pile[pile.length - totalCardsLeft(pile)] - 1] == 4) {
			if((r.nextInt() % 2) == 0) {
				if((r.nextInt() % 2) == 0) {
					colorIndex[pile[pile.length - totalCardsLeft(pile)] - 1] = 0;
				}
				else {
					colorIndex[pile[pile.length - totalCardsLeft(pile)] - 1] = 1;
				}
			}
			else {
				if((r.nextInt() % 2) == 0) {
					colorIndex[pile[pile.length - totalCardsLeft(pile)] - 1] = 2;
				}
				else {
					colorIndex[pile[pile.length - totalCardsLeft(pile)] - 1] = 3;
				}
			}
		}
		
		//draws 7 cards for hands
		for(int i = 0; i < players; i++) {
			drawAmount(hands[i], deck, 7);
		}
		
		int x = 1; //can be modified, allowing main loop to have a variable turn order
		int turn = 0; //tracks turn number for display
		int canPlay = 1; //tracks if player has performed an action or is skipped
		int skipped = 0; //tracks if next player is skipped
		int mustDraw = 0; //tracks amount of cards next player must draw
		int y = 0; //allows for variable turn order through different starting pos. for i
		boolean running = true; //boolean for main while loop
		
		//main game loop
		while(running == true) {
			turn++;
			for(int i = y; i < players && i >= 0; i += x) {
				//player gameplay start
				if(i == 0) {
					canPlay = 1;
					//prints UI
					System.out.println();
					System.out.println("--------------------------------------------------------------------------------------------");
					System.out.print("Current Card: ");
					displayCard(pile[pile.length - totalCardsLeft(pile)]);
					System.out.print(",  { Turn " + turn + " }");
					displayHand(playerHand);
					System.out.println("--------------------------------------------------------------------------------------------");
					Integer input = 0;
					//player skipping & mustDraw logic
					if(skipped == 1) {
						canPlay = 0;
						System.out.println();
						System.out.println("You've been skipped!");
						skipped = 0;
						if (mustDraw > 0) {
							drawAmount(playerHand, deck, mustDraw);
							System.out.println("You had to draw " + mustDraw + " cards!");
							mustDraw = 0;
						}
					}
					//main player loop
					while(canPlay == 1) {
						//player card input logic
						input = -1;
						while(input == -1) {
							System.out.println("Please select card, type 0 to draw: ");
							String rawInput = scanner.nextLine();
							input = tryParseInt(rawInput);
						}
						if(input == 0) {
							drawAmount(playerHand, deck, 1);
							canPlay = 0;
							continue;
						}
						//player card playing logic
						if(playCard(playerHand, input) == true) {	
							int pileDiff = pile.length - totalCardsLeft(pile);
							//skipping cards logic
							if(numIndex[pile[pileDiff] - 1] == 10 || numIndex[pile[pileDiff] - 1] == 12 || numIndex[pile[pileDiff] - 1] == 14) {
								skipped = 1;
								System.out.println("You've skipped the next player!");
								//+# cards logic
								if(numIndex[pile[pileDiff] - 1] == 10 || numIndex[pile[pileDiff] - 1] == 14) {
									if(numIndex[pile[pileDiff] - 1] == 10) {
										mustDraw = 2;
									}
									//+4 logic
									else {
										mustDraw = 4;
										chooseColor();
									}
								}
							}
							//reverse card logic
							else if(numIndex[pile[pileDiff] - 1] == 11) {
								x = -x;
								y -= x * (players - 1);
							}
							//wild card logic
							else if(numIndex[pile[pileDiff] - 1] == 13) {
								chooseColor();
							}
							canPlay = 0;
							continue;
						}
						else {
							System.out.println("Invalid card, please try again");
						}
					}
				} //player gameplay end
				
				//enemy behaviour start
				else if(graveyard[i] == 0) {
					//enemy skipping & mustDraw logic
					if(skipped == 1) {
						skipped = 0;
						System.out.println();
						System.out.print("Enemy " + i + " has been skipped");
						if (mustDraw > 0) {
							drawAmount(hands[i], deck, mustDraw);
							System.out.print(", had to draw " + mustDraw + " cards!");
							mustDraw = 0;
						}
					}
					else {
						//main enemy loop
						for(int j = 0; j < totalCardsLeft(hands[i]); j++) { 
							if(playCard(hands[i], j + 1) == true) {
								j = 100;
								int pileDiff = pile.length - totalCardsLeft(pile);
								//enemy color selection behaviour (for wilds & +4s)
								if(colorIndex[pile[pileDiff] - 1] == 4) { 
									if((r.nextInt() % 2) == 0) {
										if((r.nextInt() % 2) == 0) {
											colorIndex[pile[pileDiff] - 1] = 0;
										}
										else {
											colorIndex[pile[pileDiff] - 1] = 1;
										}
									}
									else {
										if((r.nextInt() % 2) == 0) {
											colorIndex[pile[pileDiff] - 1] = 2;
										}
										else {
											colorIndex[pile[pileDiff] - 1] = 3;
										}
									}
								}
								//enemy skip, +2, & +4 behaviour
								if(numIndex[pile[pileDiff] - 1] == 10 || numIndex[pile[pileDiff] - 1] == 12 || numIndex[pile[pileDiff] - 1] == 14) { 
									skipped = 1;
									if(numIndex[pile[pileDiff] - 1] == 10 || numIndex[pile[pileDiff] - 1] == 14) {
										if(numIndex[pile[pileDiff] - 1] == 10) {
											mustDraw = 2;
										}
										else {
											mustDraw = 4;
										}
									}
								}
								//enemy reverse card behaviour
								else if(numIndex[pile[pileDiff] - 1] == 11) {
									x = -x;
									y -= x * (players - 1);
								}
								//display if enemy played
								System.out.println();
								System.out.print("Enemy " + i + " played: ");
								displayCard(pile[pile.length - totalCardsLeft(pile)]);
							}
							//display if enemy drew
							if(j + 1 == totalCardsLeft(hands[i])) {
								drawAmount(hands[i], deck, 1);
								System.out.println();
								System.out.print("Enemy " + i + " drew 1 card");
								j = 100;
							}
							
						} //main enemy loop end
						//enemy win/loss behaviour
						System.out.print(", has " + totalCardsLeft(hands[i]) + " card(s) left");
						if(totalCardsLeft(hands[i]) == 20) {
							drawAmount(deck, hands[i], 20);
							shuffle(deck);
							System.out.print(", enemy " + i + " is out of the game!");
							graveyard[i] = 1;
						}
						else if(totalCardsLeft(hands[i]) == 0) {
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println("Enemy " + i + " wins!");
							running = false;
							break;
						}
					}

				} //enemy behaviour end
				//player win/loss behaviour
				if(totalCardsLeft(playerHand) == 20) {
					System.out.println("You reached 20 cards, you lose!");
					running = false;
					break;
				}
				else if(totalCardsLeft(playerHand) == 0) {
					System.out.println();
					System.out.println();
					System.out.println();
					System.out.println("Congratulations, you win!");
					running = false;
					break;
				}
				//transfers all pile cards to deck when deck runs out, handles setup logic
				if(totalCardsLeft(deck) == 0) {
					System.out.println("Deck is out of cards! Returning pile and reshuffling...");
					drawAmount(deck, pile, totalCardsLeft(pile) - 1);
					deckSetup(deck);
				}
			}
		} //main loop end
		
	} //method main end
	
	//resets indexes, shuffles deck, places one card from deck on top of pile
	public static void deckSetup(int[] deck) {
		int deckNum = 0; 
		
		//fills numIndex
		deckNum = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 15; j++) {
				if(j == 0 || j == 13 || j == 14) {
					numIndex[deckNum] = j;
					deckNum++;
				}
				else {
					numIndex[deckNum] = j;
					deckNum++;
					numIndex[deckNum] = j;
					deckNum++;
				}
				
			}
		}
		
		//fills colorIndex
		deckNum = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 15; j++) {
				if(j == 0 || j == 13 || j == 14) {
					if(j == 0) {
						colorIndex[deckNum] = i;
						deckNum++;
					}
					else {
						colorIndex[deckNum] = 4;
						deckNum++;
					}
				}
				else {
					colorIndex[deckNum] = i;
					deckNum++;
					colorIndex[deckNum] = i;
					deckNum++;
				}
			}
		}
		
		//shuffles deck
		shuffle(deck);
		shuffle(deck);
		shuffle(deck);
		shuffle(deck);
		shuffle(deck);
	} //method pileSetup end
	
	
	//prompts player to choose a color on playing a wild card
	public static void chooseColor() {
		int pileDiff = pile.length - totalCardsLeft(pile);
		while(colorIndex[pile[pileDiff] - 1] == 4) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Is this card 1: Red, 2: Green, 3: Blue, or 4: Yellow?");
			String colorChoice = scanner.nextLine();
			colorIndex[pile[pileDiff] - 1] = tryParseInt(colorChoice) - 1;
		}
	} //method chooseColor end
	
	//parses input as int, returns -1 and prints message if fails
	static Integer tryParseInt(String input) {
		try {
			Integer x = Integer.parseInt(input);
			return x;
		}
		catch(Exception ex) {
			System.out.println("Invalid input, please try again");
			return -1;
		}
	} //method tryParseInt end
	
	//checks if an inputed card is currently eligible for play, returns boolean
	public static boolean canPlay(int[] hand, int handNum) {
		int pileDif = pile.length - totalCardsLeft(pile);
		if(colorIndex[hand[handNum] - 1] == colorIndex[pile[pileDif] - 1] || numIndex[hand[handNum] - 1] == numIndex[pile[pileDif] - 1] || colorIndex[hand[handNum] - 1] == 4) {
			return true;
		}
		else {
			return false;
		}
	} //method canPlay end
	

	//plays a card from a hand to the pile and eliminates hand gaps, returns whether it was successful
	public static boolean playCard(int[] hand, int handNum) {
		handNum = hand.length - handNum;
		if(canPlay(hand, handNum) == true) {
			putOnTop(pile, hand[handNum]);
			for(int i = handNum; i > 0; i--) {
				hand[i] = hand[i - 1];
			}
			return true;
		}
		else {
			return false;
		}
	} //method playCard end
	
	//prints values of a single card
	public static void displayCard(int card) {
		card--;
		char[] colors = {'r', 'g', 'b', 'y', 'w'};
		String[] specials = {"+2", "Rev.", "Skip", "Wild", "+4"};
		if(numIndex[card] < 10) {
			System.out.print("[" + colors[colorIndex[card]] + numIndex[card] + "] ");
		}
		else if(numIndex[card] < 13){
			System.out.print("[" + colors[colorIndex[card]] + specials[numIndex[card] - 10] + "] ");
		}
		else {
			System.out.print("[" + colors[colorIndex[card]] + specials[numIndex[card] - 10] + "] ");
		}
	} //method displayCard end
	

	//displays playerHand and numbers card options
	public static void displayHand(int[] hand) {
		System.out.println();
		int deckDif = hand.length - totalCardsLeft(hand);
		for(int i = hand.length - 1; i >= deckDif; i--) {
			System.out.print((hand.length - i) + ": ");
			displayCard(hand[i]);
		}
		System.out.println();
	} //method displayHand end
	
	//draws a single card from the top of a deck (highest filled space in the array), returns that card's value
	public static int draw(int[] deck) {
		for(int i = 0; i < deck.length; i++) {
			if(deck[i] != 0) {
				int card = deck[i];
				deck[i] = 0;
				return card;
			}
		}
		return 0;
	} //method draw end
	
	//draws a variable amount of cards from the top of a deck by calling the draw function that number of times
	public static void drawAmount(int[] hand, int[] deck, int n) {
		for(int i = 0; i < n; i++) {
			putOnTop(hand, draw(deck));
		}
	} //method drawAmount end
	
	//inputs a new card value at the lowest empty space in a deck, returns if this was successful (is not if deck is full)
	public static boolean putOnTop(int[] deck, int c) {
		int slot = totalCardsLeft(deck);
		if(slot < deck.length) {
			deck[deck.length - slot - 1] = c;
			return true;
		}
		return false;
	} //method putOnTop end
	
	//prints out an int[] broken into lines of 27 (for debugging)
 	public static void deckTest(int[] deck) {
 		System.out.println();
		for(int i = 0; i < deck.length; i++) {
			System.out.print(deck[i] + " ");
			if((i + 1) % 27 == 0) {
				System.out.println();
			}
		}
		System.out.println();
		System.out.println();
	} // method deckTest end
	
	//randomizes all values in a deck, "shuffling" it
	public static void shuffle(int[] deck) {
		int deckDif = deck.length - totalCardsLeft(deck);
		Random r = new Random();
		for(int i = deckDif; i < deck.length; i++) {
			for(int g = deckDif; g + 1 < deck.length; g++) {
				int temp = deck[i];
				if(r.nextInt() % 2 > 0) {
					deck[i] = deck[g + 1];
					deck[g + 1] = temp;
				}
			}
		}
	} //method shuffle end
	
	//returns how many slots in a deck array are filled
	public static int totalCardsLeft(int[] deck) {
		int count = 0;
		for(int card : deck) {
			if(card != 0) {
				count++;
			}
		}
		return count;
	} //method totalCardsLeft end
}
