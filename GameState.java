import java.util.*;

public class GameState{
    // Variables
    private Deck deck;
    private Card played_card;
    private Player player1;
    private Player player2;
    // private

    // Constructor
    public GameState(){
        deck = new Deck();
        // for (int i = 0; i < players)
        Player player1 = new Player();
        Player player2 = new Player();
    }

    // Methods
    private void deal(){
        for (int i = 1; i <= 7; i++) {
            player1.draw(deck.getTopCard());
            player2.draw(deck.getTopCard());
        }
    }
    public void startGame(){
        deal();
        played_card = deck.getTopCard();
    }

}