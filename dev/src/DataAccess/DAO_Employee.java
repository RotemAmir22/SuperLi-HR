package DataAccess;
import BussinesLogic.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DAO_Employee implements DAO{

    private Map<String ,Employee> networkEmployees;

    // constructor
    public DAO_Employee() {
        networkEmployees = new HashMap<>();
    }

    public Employee findEmployeeByID(String ID) {
        if (networkEmployees.containsKey(ID)){return networkEmployees.get(ID);}
        return null;
    }
    @Override
    public void insert(Object o) {

    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void delete(Object o) {

    }

    public List<Employee> getNetworkEmployees(){
        return (List<Employee>) networkEmployees.values();
    }
}
