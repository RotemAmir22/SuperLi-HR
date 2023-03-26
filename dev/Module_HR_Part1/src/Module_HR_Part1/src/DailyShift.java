package Module_HR_Part1.src;

import java.io.File;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class DailyShift {

    private String date;//format of date: dd-MM-yyyy
    private File endOfDayReport;
    private Map<Role, Employee> morningShift;
    private Map<Role, Employee> eveningShift;

    public DailyShift() {
        LocalDate today = LocalDate.now();
        this.date = today.toString().chars().mapToObj(c -> String.valueOf((char)c)).collect(Collectors.joining(""));
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
    public String getDate() {
        return date;
    }

    //setters


}
