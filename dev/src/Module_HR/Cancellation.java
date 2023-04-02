package Module_HR;

public class Cancellation {


    private static int serialNumCounter=0;
    private int cancelID;
    private String item;
    private int amount;

    //constructor
    public Cancellation(String itemName, int amount)
    {
        this.item = itemName;
        this.amount = amount;
        serialNumCounter++;
        this.cancelID = serialNumCounter;
    }

    //getters
    public int getCancelID() {return cancelID;}
    public String getItem() {return item;}
    public int getAmount() {return amount;}
}
