UnoAppAlt.java is the functioning draft of the project.

Player.java is the class intended for the user player. Player can be instanced and initialized with Player.initPlayer(int maxHand, String[] startingDraw). The playerTurn(String discardPile, String drawCard) method can be called to start the player turn. player.getDiscardOut() retrieves the value of the played card. It is tested through UnoTest.java.

UnoCard.java is the class that handles everything related to the deck.

UnoUi.java handles displaying game information. 
