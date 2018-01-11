public class WildCard extends Card{
    // Variables
    final private boolean draw4;

    // Constructor
    public WildCard(boolean draw4, String color){
        super(color);
        this.draw4 = draw4;
    }
    public WildCard(boolean draw4){
        super();
        this.draw4 = draw4;
    }

    // Methods
    @Override
    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color = color;
    }
    public boolean isDraw4(){
        return draw4;
    }
    @Override
    public String toString(){
        if (draw4) {
            return "draw4";
        } else {
            return "wildcard";
        }
    }
}