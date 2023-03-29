package Module_HR_Part1.src;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ShiftManager {

    private String fullName;
    private String id;
    private List<ShiftM_Permissions> permissions;
    private LocalDate shiftDate; //date of shift
    private int shiftSlot; //morning - 0  or evening shift - 1
    private List<Cancelation> cancelations;

    //constructor
    public ShiftManager(String name, String id, LocalDate shiftDate, int shiftSlot) {
        this.fullName = name;
        this.id = id;
        this.shiftDate = shiftDate;
        this.shiftSlot = shiftSlot;

        this.permissions = new ArrayList<ShiftM_Permissions>();
        //add permissions to shift manager - these are the basic permission that evey shift manager has
        ShiftM_Permissions cancellation = new ShiftM_Permissions("cancellations", "shift manager can cancel items");
        this.permissions.add(cancellation);
        ShiftM_Permissions manageStaff = new ShiftM_Permissions("manage staff", "shift manager can manage shift staff");
        this.permissions.add(manageStaff);

        this.cancelations=new ArrayList<Cancelation>();
    }

    //getters
    public String getFullName() {return fullName;}
    public String getId() {return id;}
    public List<ShiftM_Permissions> getPermissions() {return permissions;}
    public LocalDate getShiftDate() {return shiftDate;}
    public int getShiftSlot() {return shiftSlot;}
    public List<Cancelation> getCancelations() {return cancelations;}

    /**
     *
     * @param permission: add this permission the list of permission of specific shift manager
     */
    public void addPermission(ShiftM_Permissions permission)
    {
        this.permissions.add(permission);
    }

    /**
     *
     * @param permission: remove this permission from specific shift manager
     */
    public void removePermission(ShiftM_Permissions permission)
    {
        this.permissions.remove(permission);
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
        this.cancelations.add(cancelation);
        System.out.println("ITEM CANCELLED !!!");

    }

}
