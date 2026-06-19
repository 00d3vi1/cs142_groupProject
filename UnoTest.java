import java.util.Arrays;
import java.io.*;

public class UnoTest {

	public static void main(String[] args) {
 
		final String ANSI_YELLOW = "\u001B[33m";
		final String ANSI_GREEN = "\u001B[32m";
		final String ANSI_RED = "\u001B[31m";
		
		Player.setColor(ANSI_GREEN);
		
		System.out.println(ANSI_GREEN + "Uno Tester");
		
		
		UnoCard cDeck = new UnoCard();
		cDeck.createDeck();
		cDeck.shuffleDeck();
		String[] startDraw = cDeck.dealHand(5);
		
		
		Player player1 = new Player();
		
		
		Player.setColor(ANSI_RED);
		System.out.println(ANSI_YELLOW + "Start Draw array " + Arrays.toString(startDraw));
		player1.playerInit(10, startDraw);

		String discardPile = "yellow_1";
		String drawCard = "green_5";
		
		player1.playerTurn(discardPile, drawCard);
		

		System.out.println("NEXT PLAYER TURN!");
		
		
	}

}
