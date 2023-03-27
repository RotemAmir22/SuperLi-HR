package Module_HR_Part1.src;

import java.io.File;
import java.util.Date;
import java.util.List;

public class Employee {

    private String firstName;
    private String lastName;

    public String getId() {
        return id;
    }
    public String getName() {
        return firstName+" "+lastName;
    }

    private String id;
    private String bankAccount;
    private double salary;
    private File empTerms;
    private Date startDate;
    private List<AEmployeeDetails> details;
    private boolean[][] constrains; // [0,0] sunday morning, [0,1] sunday eve...
    private int shiftsLimit = 6;
    private List<Role> qualifications;

    public void setConstrains(int day, int shift,boolean limit)
    {
        if(limit){this.constrains[day][shift] = true;}
        else {this.constrains[day][shift] = false;}

    }

    public boolean[][] getConstraints() {return constrains;}

    public List<Role> getQualifications() {return qualifications;}
    public void addRole(Role newRole)
    {
        qualifications.add(newRole);
    }

    public void removeRole(Role oldRole)
    {
        qualifications.remove(oldRole);
    }
}
