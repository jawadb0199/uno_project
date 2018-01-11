public class NumberedCard extends Card{
    // Variables
    protected int number;

    // Constructor
    public NumberedCard(String color, int number){
        super(color);
        this.number = number;
    }

    // Methods
    public int getNumber(){
        return number;
    }
    @Override
    public String getColor(){
        return color;
    }
    @Override
    public String toString(){
        return color + Integer.toString(number);
    }

}
