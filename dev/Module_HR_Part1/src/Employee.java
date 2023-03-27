package Module_HR_Part1.src;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

public class Employee {

    private String firstName;
    private String lastName;
    private String id;
    private String bankAccount;
    private double salary;
    private File empTerms;
    private LocalDate startDate;
    private List<AEmployeeDetails> details;
    private boolean[][] constrains; // [0,0] sunday morning, [0,1] sunday eve...
    private int shiftsLimit;
    private List<Role> qualifications;

    //constructor
    public Employee(String firstName, String lastName, String id, String bankAccount, double salary, File empTerms, LocalDate startDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.bankAccount = bankAccount;
        this.salary = salary;
        this.empTerms = empTerms;
        this.startDate = startDate;
        this.shiftsLimit = 6; // worker can only work 6 shifts a week
        this.constrains = new boolean[7][2]; //if worker can work in the morning or evening shift of each day
        this.details= new List<AEmployeeDetails>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<AEmployeeDetails> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(AEmployeeDetails aEmployeeDetails) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends AEmployeeDetails> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends AEmployeeDetails> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public AEmployeeDetails get(int index) {
                return null;
            }

            @Override
            public AEmployeeDetails set(int index, AEmployeeDetails element) {
                return null;
            }

            @Override
            public void add(int index, AEmployeeDetails element) {

            }

            @Override
            public AEmployeeDetails remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<AEmployeeDetails> listIterator() {
                return null;
            }

            @Override
            public ListIterator<AEmployeeDetails> listIterator(int index) {
                return null;
            }

            @Override
            public List<AEmployeeDetails> subList(int fromIndex, int toIndex) {
                return null;
            }
        }; //additional details about worker
        this.qualifications=new List<Role>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Role> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Role role) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Role> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Role> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Role get(int index) {
                return null;
            }

            @Override
            public Role set(int index, Role element) {
                return null;
            }

            @Override
            public void add(int index, Role element) {

            }

            @Override
            public Role remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Role> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Role> listIterator(int index) {
                return null;
            }

            @Override
            public List<Role> subList(int fromIndex, int toIndex) {
                return null;
            }
        }; //employees qualifications to perform a role, description can be saved in details if required
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
    public File getEmpTerms() {return empTerms;}
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
    public void setEmpTerms(File empTerms) {this.empTerms = empTerms;}

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
