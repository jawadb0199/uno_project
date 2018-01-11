public class ActionCard extends Card{
    // Variables
    protected String action;

    // Constructor
    public ActionCard(String color, String action){
        super(color);
        this.action = action;
    }

    //Methods
    @Override
    public String getColor(){
        return color;
    }
    public String getAction(){
        return action;
    }
    @Override
    public String toString(){
        return color + action;
    }
}
