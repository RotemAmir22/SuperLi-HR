package Module_HR_Part1.src;

public class Cancelation {

    private String item;
    private int amount;

    private int cancelID;
    private static int serialNumCounter=0;

    public Cancelation(String itemName, int aamount)
    {
        this.item = itemName;
        this.amount = aamount;
        serialNumCounter++;
        this.cancelID = serialNumCounter;
    }
}
