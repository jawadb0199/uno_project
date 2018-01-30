import java.util.ArrayList;

public class Player{
    // Variables
    private ArrayList<Card> hand;
    private String name;

    // Constructor
    public Player(){
        this.hand = new ArrayList<Card>();
    }
    public Player(String name){
        this.name = name;
        this.hand = new ArrayList<Card>();
    }

    // Methods
    public ArrayList<Card> getHand(){
        return hand;
    }
    public void draw(Card card){
        hand.add(card);
    }
    public int handSize(){
        return hand.size();
    }
    public Card get(int index){
        if (index >= 0 && index < hand.size()) {
             return hand.get(index);
        } else {
            return null;
        }
    }
    public Card remove(int index){
        if (index >= 0 && index < hand.size()) {
             return hand.remove(index);
        } else {
            return null;
        }
    }
    public String toString(){
        return name;
    }
    public boolean hasDrawCard() {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            if (card instanceof WildCard) {
                if (((WildCard) card).isDraw4()) {
                    return true;
                }
            } else if (card instanceof ActionCard) {
                if (((ActionCard) card).getAction().equals("draw2")) {
                    return true;
                }
            }
        }
        return false;
    }
}
