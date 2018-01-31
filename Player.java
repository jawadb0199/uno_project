import java.util.ArrayList;

//Player class to encapsulate ArrayList of PLayer's cards in hand
public class Player{
    // Variables
    private ArrayList<Card> hand;
    private String name;

    // Constructors
    public Player(){
        this.hand = new ArrayList<Card>(); //
    }
    public Player(String name){
        this();
        this.name = name;
    }

    // Methods

    // Add a card to Player's hand
    public void draw(Card card){
        hand.add(card);
    }
    // Return number of cards in Player's hand
    public int handSize(){
        return hand.size();
    }
    // Return card in Player's hand at parameter index
    public Card get(int index){
        if (index >= 0 && index < hand.size()) {
             return hand.get(index);
        } else {
            return null;
        }
    }
    // Remove and return card in Player's hand at parameterx index
    public Card remove(int index){
        if (index >= 0 && index < hand.size()) {
             return hand.remove(index);
        } else {
            return null;
        }
    }
    // Overrriden toString() method returns name instance variable
    @Override
    public String toString(){
        return name;
    }
}
