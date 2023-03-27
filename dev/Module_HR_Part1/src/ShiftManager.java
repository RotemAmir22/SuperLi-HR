package Module_HR_Part1.src;

import java.io.File;
import java.util.Date;
import java.util.List;

public class ShiftManager {

    private String firstName;
    private String lastName;
    private String id;
    private List<ShiftM_Permissions> permissions;
    private Date shiftDate; //date of shift
    private int shiftSlot; //morning - 0  or evening shift - 1

    //constructor
    public ShiftManager(String firstName, String lastName, String id, Date shiftDate, int shiftSlot) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.shiftDate = shiftDate;
        this.shiftSlot = shiftSlot;

        //add permissions to shift manager - these are the basic permission that evey shift manager has
        ShiftM_Permissions cancellation = new ShiftM_Permissions("cancellations", "shift manager can cancel items");
        this.permissions.add(cancellation);
        ShiftM_Permissions manageStaff = new ShiftM_Permissions("manage staff", "shift manager can manage shift staff");
        this.permissions.add(manageStaff);
    }

    //getters
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getId() {return id;}
    public List<ShiftM_Permissions> getPermissions() {return permissions;}
    public Date getShiftDate() {return shiftDate;}
    public int getShiftSlot() {return shiftSlot;}

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

}
