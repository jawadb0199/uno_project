abstract public class Card{
    protected String color;
//    protected String trait;
//    protected boolean action_card;

    public Card(String color){
        this.color = color;
    }
    public Card(){
        this.color = null;
    }
//    public Card(String color, String trait){
//        this.color = color;
//        this.trait = trait;
//        action_card = trait.length() != 1;
//    }
//    public Card(String trait){
//        this.trait = trait;
//    }
//
//    public boolean isActionCard() {
//        return action_card;
//    }
    public String getColor(){
        return color;
    }
//    public String getTrait(){
//        return trait;
//    }
//
//    @Override
//    public String toString(){
//        if (trait.equals("draw4") || trait.equals("wildcard")) {
//            return trait;
//        } else {
//            return color + trait;
//        }
//    }
}