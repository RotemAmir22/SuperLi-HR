package Module_HR_Part1.src;

import java.io.File;
import java.util.Date;
import java.util.List;

public class Employee {

    private String firstName;
    private String lastName;
    private String id;
    private String bankAccount;
    private double salary;
    private File empTerms;
    private Date startDate;
    private List<AEmployeeDetails> details;
    private boolean[][] constrains; // [0,0] sunday morning, [0,1] sunday eve...
    private int shiftsLimit = 6;
    private List<Role> qualifications;
}
