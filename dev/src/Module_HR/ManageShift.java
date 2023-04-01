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
        Cancelation cancelation = new Cancelation(itemName,amount);
        shiftManager.addToCancelations(cancelation);
        System.out.println("ITEM CANCELLED !!!");

    }

    public File uploadEndofDayReport()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a file path: ");
        File file = new File(scanner.nextLine());
        currentShift.setEndOfDayReport(file);
        System.out.println("Done.");
        return file;
    }
}
