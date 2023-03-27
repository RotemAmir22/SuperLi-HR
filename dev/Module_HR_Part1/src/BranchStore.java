package Module_HR_Part1.src;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class BranchStore {

    private static int serialNumCounter=0;
    private int branchID; //cannot change to ID once created
    private String name;
    private String address; //cannot change the address once created
    private String phoneNum;
    private Hashtable<String, Employee> employees;
    private int[][] openHours;
    private Map<LocalDate,DailyShift> shiftsHistory; //Save shifts by date

    //constructor
    public BranchStore(String name, String address, String phoneNum) {
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        serialNumCounter++;
        this.branchID = serialNumCounter;
        this.employees = new Hashtable<String, Employee>();
        this.openHours = new int[2][7]; //default value is 0
        this.shiftsHistory = new Map<LocalDate, DailyShift>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public DailyShift get(Object key) {
                return null;
            }

            @Override
            public DailyShift put(LocalDate key, DailyShift value) {
                return null;
            }

            @Override
            public DailyShift remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends LocalDate, ? extends DailyShift> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<LocalDate> keySet() {
                return null;
            }

            @Override
            public Collection<DailyShift> values() {
                return null;
            }

            @Override
            public Set<Entry<LocalDate, DailyShift>> entrySet() {
                return null;
            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        };

    }

    //getters
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
    public Hashtable<String, Employee> getEmployees() {return employees;}


    //setters
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public void setOpenHours(int[][] openHours) {
        this.openHours = openHours;
    }


    /**
     * saves branches new employee in hash table -> id is the key
     * @param employee: new employee to add to branch
     */
    public void addEmployee(Employee employee)
    {
        this.employees.put(employee.getId(),employee); //returns the value
    }

    /**
     *
     * @param employee: removes given employee from branch
     */
    public void removeEmployee(Employee employee)
    {
        this.employees.remove(employee.getId(), employee); //returns bool
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
     * this function is called by the main once a month- after the salary has been paid
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
            this.shiftsHistory.remove(date);
        }

    }
}

