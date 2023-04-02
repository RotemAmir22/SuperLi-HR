package Module_HR;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;

public class ManageShift {

    private ShiftManager shiftManager;
    private DailyShift currentShift;
    private LocalDate date;

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
        Cancellation cancelation = new Cancellation(itemName,amount);
        shiftManager.addToCancelations(cancelation);
        System.out.println("ITEM CANCELLED !!!\n Cancellation ID: "+cancelation.getCancelID());

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
     * prints Cancellation information
     */
    public void getCancellation()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter Cancellation ID: ");
        int id = scanner.nextInt();
        Cancellation cancelation = shiftManager.findCancellationInList(id);
        if(cancelation == null)
            System.out.println("Cancellation not found");
        else {
            System.out.println("Cancellation Details:\n Item: "+cancelation.getItem()+" x"+cancelation.getAmount());
        }
    }
}
