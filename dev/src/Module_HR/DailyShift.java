package Module_HR;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;


public class DailyShift {

    private LocalDate date;
    private File endOfDayReport;
    private Map<Role, ArrayList<Employee>> morningShift;
    private Map<Role, ArrayList<Employee>> eveningShift;
    private List<ShiftManager> shiftManagers;

    //constructor
    public DailyShift(LocalDate date) {
        this.date = date;
        this.morningShift = new HashMap<Role,ArrayList<Employee>>();
        this.eveningShift = new HashMap<Role,ArrayList<Employee>>();
        this.shiftManagers= new ArrayList<ShiftManager>();
    }

    //getters
    public LocalDate getDate() {
        return date;
    }
    public Map<Role, ArrayList<Employee>> getMorningShift() {
        return morningShift;
    }
    public Map<Role, ArrayList<Employee>> getEveningShift() {
        return eveningShift;
    }
    public File getEndOfDayReport() {
        return endOfDayReport;
    }
    public List<ShiftManager> getShiftManagers() {return shiftManagers;}

    //setters
    public void setMorningShift(Map<Role, ArrayList<Employee>> morningShift) {
        this.morningShift = morningShift;
    }
    public void setEveningShift(Map<Role, ArrayList<Employee>> eveningShift) {
        this.eveningShift = eveningShift;
    }
    public void setEndOfDayReport(File endOfDayReport) {
        this.endOfDayReport = endOfDayReport;
    }
    public void addShiftManager(ShiftManager shiftManager) {
        this.shiftManagers.add(shiftManager);
    }
    public void removeShiftManager(ShiftManager shiftManager) {
        this.shiftManagers.remove(shiftManager);
    }


    /*change shift
    * remove from shift
    * remove from morning shift
    */
    public void removeEmployeeFromMorning(Employee employee, Role role)
    {
        this.morningShift.remove(role,employee);
    }

    //remove from evening shift
    public void removeEmployeeFromEvening(Employee employee, Role role)
    {
        this.eveningShift.remove(role,employee);
    }
    //according to shift this function refers to the right helper
    public void removeEmployeeFromShift(Employee employee, Role role, int shift)
    {
        //if the employee is a shift manager
        if(role == Role.SHIFTMANAGER)
        {
            ShiftManager shiftManager = findEmployeeInShiftManager(employee.getId());
            if(shiftManager != null)
                removeShiftManager(shiftManager);
        }

        if(shift == 0)
            removeEmployeeFromMorning(employee,role);
        else
            removeEmployeeFromEvening(employee, role);

        //update limit
        employee.setShiftsLimit(employee.getShiftsLimit()+1);
        //update salary
        employee.setCumulativeSalary(employee.getCumulativeSalary() - employee.getSalary());
    }

    /* add to shift
    * remove from morning shift
    */
    public void addEmployeeToMorning(Employee employee, Role role)
    {
        if(!this.morningShift.containsKey(role))
            this.morningShift.put(role, new ArrayList<>());
        this.morningShift.get(role).add(employee);

    }

    //remove from evening shift
    public void addEmployeeToEvening(Employee employee, Role role)
    {
        if(!this.eveningShift.containsKey(role))
            this.eveningShift.put(role, new ArrayList<>());
        this.eveningShift.get(role).add(employee);
    }

    //according to shift this function refers to the right helper
    public void addEmployeeToShift(Employee employee, Role role, int shift)
    {
        if(employee.canDoRole(role) && !isExistEvening(employee) && !isExistMorning(employee))
        {
            //if the employee is a shift manager
            if(role == Role.SHIFTMANAGER)
            {
                ShiftManager shiftManager = findEmployeeInShiftManager(employee.getId());
                if(shiftManager == null)
                {
                    shiftManager = new ShiftManager(employee.getName(), employee.getId(), LocalDate.now(),shift);
                    addShiftManager(shiftManager);
                }
            }

            if(shift == 0)
                addEmployeeToMorning(employee,role);
            else
                addEmployeeToEvening(employee, role);

            //update limit
            employee.setShiftsLimit(employee.getShiftsLimit()-1);
            //update salary
            employee.setCumulativeSalary(employee.getCumulativeSalary() + employee.getSalary());
        }
    }

    public ShiftManager findEmployeeInShiftManager(String ID)
    {
        for (ShiftManager shiftManager : shiftManagers) {
            if (Objects.equals(shiftManager.getId(), ID)) {
                return shiftManager;
            }
        }
        return null;
    }

    public void showMeSchedualing()
    {
        int count = 1;
        DayOfWeek dayOfWeek=this.date.getDayOfWeek();
        System.out.println("Daily shift - "+ dayOfWeek + this.date+"\nMORNING:\n");
        Map<Role, ArrayList<Employee>> sortedMap = new TreeMap<>(morningShift);
        for (Map.Entry<Role, ArrayList<Employee>> entry : sortedMap.entrySet()) {
            Role key = entry.getKey();
            System.out.println(key.name()+":");
            for (Employee employee : sortedMap.get(key))
            {
                System.out.println(count+++". "+employee.getName());
            }
        }
        count = 1;
        System.out.println("EVENING:\n");
        sortedMap = eveningShift;
        for (Map.Entry<Role, ArrayList<Employee>> entry : sortedMap.entrySet()) {
            Role key = entry.getKey();
            System.out.println(key.name()+":");
            for (Employee employee : sortedMap.get(key))
            {
                System.out.println(count+++". "+employee.getName());
            }
        }
    }

    /**
     * checks if employee is in morning shift
     * @param e : employee to search for
     * @return true of in shift
     */
    public boolean isExistMorning(Employee e)
    {
        for(Map.Entry<Role,ArrayList<Employee>> employees : morningShift.entrySet())
            for (Employee employee : employees.getValue())
            {
                if(employee == e)
                    return true;
            }
        return false;
    }

    /**
     * checks if employee is in evening shift
     * @param e : employee to search for
     * @return true of in shift
     */
    public boolean isExistEvening(Employee e)
    {
        for(Map.Entry<Role,ArrayList<Employee>> employees : eveningShift.entrySet())
            for (Employee employee : employees.getValue())
            {
                if(employee == e)
                    return true;
            }
        return false;
    }
}
