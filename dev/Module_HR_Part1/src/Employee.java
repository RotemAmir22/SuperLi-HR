package Module_HR_Part1.src;

import java.util.*;

public class Employee {

    private String firstName;
    private String lastName;
    private String id;
    private String bankAccount;
    private double salary;
    private String empTerms;
    private String startDate;
    private List<AEmployeeDetails> details;
    private boolean[][] constrains; // [0,0] sunday morning, [0,1] sunday eve...
    private int shiftsLimit;
    private List<Role> qualifications;

    //constructor
    public Employee(String firstName, String lastName, String id, String bankAccount, double salary, String empTerms, String startDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.bankAccount = bankAccount;
        this.salary = salary;
        this.empTerms = empTerms;
        this.startDate = startDate;
        this.shiftsLimit = 6; // worker can only work 6 shifts a week
        this.constrains = new boolean[7][2]; //if worker can work in the morning or evening shift of each day
        this.details= new ArrayList<AEmployeeDetails>(); //additional details about worker
        this.qualifications=new ArrayList<Role>(); //employees qualifications to perform a role, description can be saved in details if required
    }

    //getters
    public String getId() {
        return id;
    }
    public String getName() {
        return firstName+" "+lastName;
    }
    public boolean[][] getConstraints() {return constrains;}
    public List<Role> getQualifications() {return qualifications;}
    public int getShiftsLimit() {return shiftsLimit;}
    public String getBankAccount() {return bankAccount;}
    public double getSalary() {return salary;}
    public String getEmpTerms() {return empTerms;}
    public List<AEmployeeDetails> getDetails() {return details;}
    public boolean[][] getConstrains() {return constrains;}


    //setters
    public void setConstrains(int day, int shift,boolean limit)
    {
        if(limit){this.constrains[day][shift] = true;}
        else {this.constrains[day][shift] = false;}
    }
    public void setShiftsLimit(int shiftsLimit) {this.shiftsLimit = shiftsLimit;}
    public void setBankAccount(String bankAccount) {this.bankAccount = bankAccount;}
    public void setSalary(double salary) {this.salary = salary;}
    public void setEmpTerms(String empTerms) {this.empTerms = empTerms;}

    //adding/removing detail/qualifications from employee

    //Employee Details
    /**
     *
     * @param detail: add detail to employee
     */
    public void addEmployeeDetails(AEmployeeDetails detail) {
        this.details.add(detail);
    }

    /**
     *
     * @param detail: removes this detail from employee
     */
    public void removeEmployeeDetail(AEmployeeDetails detail)
    {
        this.details.remove(detail);
    }

    //Employee Qualifications
    /**
     * add qualifications to this employee
     * @param newRole: new role that this employee can do in a shift
     */
    public void addRole(Role newRole)
    {
        qualifications.add(newRole);
    }

    /**
     * as a result from removing the qualification, the employee cannot do this role in a shift
     * @param oldRole: remove this qualification from employee
     */
    public void removeRole(Role oldRole)
    {
        qualifications.remove(oldRole);
    }
}
