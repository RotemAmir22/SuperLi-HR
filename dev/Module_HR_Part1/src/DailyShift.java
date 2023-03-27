package Module_HR_Part1.src;

import java.io.File;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class DailyShift {

    private LocalDate date;
    private File endOfDayReport;
    private Map<Role, Employee> morningShift;
    private Map<Role, Employee> eveningShift;

    //constructor
    public DailyShift(LocalDate date) {
        this.date = date;
        this.morningShift = new Map<Role, Employee>() {
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
            public Employee get(Object key) {
                return null;
            }

            @Override
            public Employee put(Role key, Employee value) {
                return null;
            }

            @Override
            public Employee remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends Role, ? extends Employee> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<Role> keySet() {
                return null;
            }

            @Override
            public Collection<Employee> values() {
                return null;
            }

            @Override
            public Set<Entry<Role, Employee>> entrySet() {
                return null;
            }
        };
        this.eveningShift = new Map<Role, Employee>() {
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
            public Employee get(Object key) {
                return null;
            }

            @Override
            public Employee put(Role key, Employee value) {
                return null;
            }

            @Override
            public Employee remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends Role, ? extends Employee> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<Role> keySet() {
                return null;
            }

            @Override
            public Collection<Employee> values() {
                return null;
            }

            @Override
            public Set<Entry<Role, Employee>> entrySet() {
                return null;
            }
        };
    }

    //getters
    public LocalDate getDate() {
        return date;
    }
    public Map<Role, Employee> getMorningShift() {
        return morningShift;
    }
    public Map<Role, Employee> getEveningShift() {
        return eveningShift;
    }
    public File getEndOfDayReport() {
        return endOfDayReport;
    }

    //setters
    public void setMorningShift(Map<Role, Employee> morningShift) {
        this.morningShift = morningShift;
    }
    public void setEveningShift(Map<Role, Employee> eveningShift) {
        this.eveningShift = eveningShift;
    }
    public void setEndOfDayReport(File endOfDayReport) {
        this.endOfDayReport = endOfDayReport;
    }

    public void showMeSchedualing()
    {
        if(this.date.equals("Saturday"))
        {
            System.out.println("No shifts for Saturday");
        }
        System.out.println("Daily shift - "+this.date+"\nMORNING:\n");
        Map<Role, Employee> sortedMap = new TreeMap<>(morningShift);
        for (Map.Entry<Role, Employee> entry : sortedMap.entrySet()) {
            Role key = entry.getKey();
            Employee e = entry.getValue();
            System.out.println("["+key.name()+": "+e.getName()+"]\n");
        }
        if(!(this.date.equals("Friday")))
        {
            System.out.println("EVENING:\n");
            sortedMap = new TreeMap<>(eveningShift);
            for (Map.Entry<Role, Employee> entry : sortedMap.entrySet()) {
                Role key = entry.getKey();
                Employee e = entry.getValue();
                System.out.println("["+key.name()+": "+e.getName()+"]\n");
            }
        }

    }

}
