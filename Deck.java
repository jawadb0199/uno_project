import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck{
    // Variables
    private ArrayList<Card> deck;

    // Constructor
    public Deck(){
        deck = newDeck();
    }
    public Deck(boolean isEmpty){
        if (isEmpty) {
            deck = new ArrayList<Card>();
        } else {
            deck = newDeck();
        }
    }

    // Methods
    private ArrayList<Card> newDeck(){
        ArrayList<Card> new_deck = new ArrayList<Card>();
        String[] colors = {"red", "yellow", "green", "blue"};
        String[] actions = {"skip", "reverse", "draw2"};
        for (int i = 0; i <= 9; i++) {
            for (String color : colors) {
                new_deck.add(new NumberedCard(color, i));
                if (i != 0) {
                    new_deck.add(new NumberedCard(color, i));
                }
                if (i == 9) {
                    for (String action : actions) {
                        new_deck.add(new ActionCard(color, action));
                        new_deck.add(new ActionCard(color, action));
                    }
                }

            }
            if (i < 8) {
                new_deck.add(new WildCard(i%2 == 0));
            }
        }
        Collections.shuffle(new_deck);
        return new_deck;
    }

    // Methods
    public void add(Card card){
        deck.add(card);
    }
    public void addToEnd(Card card){
        deck.add(deck.size(), card);
    }
    public Card getTopCard(){
        return deck.remove(0);
    }
    public int size(){
        return deck.size();
    }
    public Card get(int index){
        if (index >= 0 && index < deck.size()) {
             return deck.get(index);
        } else {
            return null;
        }
    }

    public String toString(){
        return deck.toString();
    }
}
