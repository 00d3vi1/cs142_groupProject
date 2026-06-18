import java.util.Arrays;

public class UnoTest {

	public static void main(String[] args) {
		// deck
		// last card played
		// draw card. 
		
		System.out.println("Uno Tester");
		
		String[] deck1 = {"green_1", "green_2", "red_3", "yellow_8"};
		String[] deckFull = {"red_2", "yellow_2", "red_8", "blue_10", "green_1"};
		String[] deckUno = { "black_5", "empty", "empty", "empty", "empty" };
		
		
		UnoCard cDeck = new UnoCard();
		cDeck.createDeck();
		cDeck.shuffleDeck();
		String[] startDraw = cDeck.dealHand(5);
		
		
		Player player1 = new Player();
		
//		Player player2 = new Player();
//		player2.playerInit(5, deckFull);
//		
//		Player player3 = new Player();
//		player3.playerInit(5, deckUno);
		
		
		
		System.out.println("Start Draw array " + Arrays.toString(startDraw));
		player1.playerInit(10, startDraw);
		
		player1.playerTurn("green_5");
		
		
		
		
		//String cardPicker = pickCard(unoDeck);
		//System.out.println("Show me string: " + cardPicker);
		//System.out.println(player1.toString());
		
		
	}

}
