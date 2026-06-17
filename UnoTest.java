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
		
		
		Player player1 = new Player();
		player1.playerInit(5, deck1, "Jerry");
		
		Player player2 = new Player();
		player2.playerInit(5, deckFull, "Oscar");
		
		Player player3 = new Player();
		player3.playerInit(5, deckUno, "Gob");
		
		
		
		
		System.out.println(player1.toString());
		deck1[1] = "blue_6";
		System.out.println("Deck 1 Values: " + Arrays.toString(deck1));
		System.out.println(player1.toString());
		
		player1.validHand("blue");
		player1.clearHand();
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		System.out.println(player2.toString());
		
		
		//player2.knockOutPlayer();
		player2.playerTurn("green_5");
		
		
		
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(player3.toString());
		player3.playerTurn("blue_3");
		System.out.println(player3.toString());
	}

}
