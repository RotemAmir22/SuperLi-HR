package BussinesLogic;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

/**
 * BranchStore class
 * Include details about a specific branch
 * Every branch get a unique ID
 * The shifts history saved at a specific branch for later use
 */
public class BranchStore {

    private static int serialNumCounter=0;
    private int branchID; //cannot change to ID once created
    private String name;
    private String address; //cannot change the address once created
    private String phoneNum;
    private List<Employee> employees;
    private int[][] openHours; // days (0 - Sunday, 1- Monday, etc.) and hours ([0][0] - Sunday morning, [0][1] - Sunday evening)
    private String openingTime; // a summery for employees to know when the branch is open
    private Map<LocalDate,DailyShift> shiftsHistory; //Save shifts by date


    /**
     * Constructor
     * @param openingtime: description
     */
    public BranchStore(String name, String address, String phoneNum, String openingtime) {
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        serialNumCounter++;
        this.branchID = serialNumCounter;
        this.employees = new ArrayList<Employee>();
        this.openHours = new int[7][2]; //default value is 0 means open 24/7
        this.shiftsHistory = new HashMap<LocalDate, DailyShift>();
        this.openingTime = openingtime;

    }

    //getters
    public String getOpeningTime() { return openingTime; }
    public int getBranchID() {
        return branchID;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public int[][] getOpenHours() {
        return openHours;
    }
    public Map<LocalDate, DailyShift> getShiftsHistory() {
        return shiftsHistory;
    }
    public List<Employee> getEmployees() {return employees;}


    //setters
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public void setOpenHours(int day, int shift, int availability) {
        this.openHours[day][shift] = availability;
    }
    public String setOpeningTime(String openingTime) { return this.openingTime = openingTime; }


    /**
     * saves branches new employee in list
     * @param employee: new employee to add to branch
     */
    public void addEmployee(Employee employee)
    {
        this.employees.add(employee);
    }

    /**
     *
     * @param employee: removes given employee from branch
     */
    public void removeEmployee(Employee employee)
    {
        this.employees.remove(employee); //returns bool
    }

    /**
     *
     * @param dailyShift: adds this shift to history
     */
    public void addShiftToHistory(DailyShift dailyShift) {
        this.shiftsHistory.put(dailyShift.getDate(),dailyShift);
    }

    /**
     * clears all shift history of the past month
     * this function is called once a month (by the user)
     * the deletion is this way so that if more dates have been added to the map, they won't be deleted.
     */
    public void deleteHistory()
    {
        // Get the current date
        LocalDate today = LocalDate.now();

        // Get the month before the current month
        Month previousMonth = today.getMonth().minus(1);

        // Get the number of days in the previous month
        int daysInPreviousMonth = previousMonth.length(today.isLeapYear());

        // Loop through the dates of the previous month
        for (int i = 1; i <= daysInPreviousMonth; i++) {
            //get the date of the previous month
            LocalDate date = LocalDate.of(today.getYear(), previousMonth, i);
            //remove date from history
            if(this.shiftsHistory.get(date) != null)
                this.shiftsHistory.remove(date);
        }

    }

    /**
     * print shift by given date
     * @param dateString: date in string
     */
    public void showShiftByDate(String dateString)
    {
        //convert string to key type LocalDate
        LocalDate date = LocalDate.parse(dateString);
        if(this.shiftsHistory.get(date)== null)
            System.out.println("NO SHIFT YET");
        else this.shiftsHistory.get(date).showMeSchedualing();
    }

    /**
     *
     * @param dateString: gets a date
     * @return daily shift of this date
     */
    public DailyShift getShiftByDate(String dateString)
    {
        //convert string to key type LocalDate
        LocalDate date = LocalDate.parse(dateString);
        return this.shiftsHistory.get(date);
    }

    public void printOpenHours()
    {
        System.out.println("Branch no. "+this.branchID+" open hours are "+this.openingTime);
    }

    /**
     * finds employee in branch by ID
     * @param ID: id to search
     * @return return the employee or returns null
     */
    public Employee findEmployeeInBranch(String ID)
    {
        for (Employee employee : employees) {
            if (Objects.equals(employee.getId(), ID)) {
                return employee;
            }
        }
        return null;
    }

    /**
     * prints all branches details
     */
    public void printBranchDetails()
    {
        System.out.println("- "+getName()+", ID: "+getBranchID()+" -\nAddress: "+getAddress()+"\nPhone number: "+getPhoneNum()+"\nOpen Hours: "+getOpeningTime());
        System.out.println("Employees in this Branch:");
        int counter = 1;
        for (Employee employee : getEmployees())
        {
            System.out.println(counter+++". "+employee.getName());
        }

    }

    /**
     * view the transit that will arrive at the store
     * only shift managers and inventory workers can view
     * @param date : date that supply will arrive
     * @param employeeID : ID of the employee that wants to view
     */
    public void viewTransit(LocalDate date, String employeeID)
    {
        DailyShift dailyShift = getShiftByDate(date.toString());
        //works in the daily shift
        Employee employee = dailyShift.isEmployeeInShift(employeeID);
        if( employee != null)
        {
            //can view transit
            Role role = dailyShift.getEmployeeRoleInShift(employee);
            if(role != null)
            {
                if(Objects.equals(role.toString(), "SHIFTMANAGER") || Objects.equals(role.toString(), "STORAGE"))
                {
                    //TODO: getTransit(LocalDate date, String branchStoreID) the function already prints the details
                }
            }
        }
        System.out.println("You cannot view the transit information");
    }
}

