package Presentation;

import BussinesLogic.Cancellation;
import BussinesLogic.DailyShift;
import BussinesLogic.ShiftManager;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * this class is made to manage a shift and keep track of all the cancellations
 * has user interactions
 */
public class ManageShift {

    //variables
    private ShiftManager shiftManager;
    private DailyShift currentShift;
    private LocalDate date;

    //constructor
    public ManageShift(ShiftManager shiftManager, DailyShift currentShift, LocalDate date) {
        this.shiftManager = shiftManager;
        this.currentShift = currentShift;
        this.date = date;
    }

    /**
     * only Shift manager can cancel items
     * saves item name and amount that are canceled
     */
    public void cancelItem()
    {
        System.out.println("CANCELLATION SYSTEM");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter item name: ");
        String itemName = scanner.nextLine();
        System.out.println("Enter item amount: ");
        int amount = scanner.nextInt();
        Cancellation cancellation = new Cancellation(itemName,amount);
        this.shiftManager.addToCancelations(cancellation);
        System.out.println("ITEM CANCELLED !!!\nCancellation ID: "+cancellation.getCancelID()+"Cancellation Details:\nItem: "+cancellation.getItem()+" x"+cancellation.getAmount());
        String temp = scanner.nextLine();

    }

    /**
     * upload an end of day file
     */
    public void uploadEndofDayReport()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a file path: ");
        File file = new File(scanner.nextLine());
        currentShift.setEndOfDayReport(file);
        System.out.println("Done.");
    }

    /**
     * prints Cancellation information from id given
     */
    public void getCancellation()
    {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Please enter Cancellation ID: ");
            String id = scanner.nextLine();
            Cancellation cancellation = shiftManager.findCancellationInList(Integer.parseInt(id));
            if(cancellation == null)
                System.out.println("Cancellation not found");
            else {
                System.out.println("Cancellation Details:\nItem: "+cancellation.getItem()+" x"+cancellation.getAmount());
            }
        }
        catch (Exception e)
        {
            System.out.println("Sorry, invalid input");
        }


    }
}