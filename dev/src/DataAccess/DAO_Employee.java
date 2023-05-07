package DataAccess;
import BussinesLogic.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static BussinesLogic.Role.DRIVER;


public class DAO_Employee implements DAO{

    private Map<String ,Employee> networkEmployees;

    private Map<String, Driver> newtworkDrivers;

    private Connection conn;

    // constructor
    public DAO_Employee() throws SQLException, ClassNotFoundException {
        conn = Database.connect();
        networkEmployees = new HashMap<>();

    }

    @Override
    public Object findByID(Object ID) throws SQLException {
        if (networkEmployees.containsKey(ID))
            return networkEmployees.get(ID);
        else if(newtworkDrivers.containsKey(ID))
            return newtworkDrivers.get(ID);
        // if the employee not in the MAP
        else
        {
            PreparedStatement stmt = conn.prepareStatement("SELECT firstName, lastName, bankAccount, salary, empTerms, startDate, shiftsLimit, cumulativeSalary FROM Employees WHERE employeeID = ?");stmt.setString(1, (String) ID);
            ResultSet rs = stmt.executeQuery();
            Employee employee;
            EmployeeGenerator generator;
            if (rs.next()) {
                // get the details
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String bankAccount = rs.getString("bankAccount");
                double salary = rs.getDouble("salary");
                String empTerms = rs.getString("empTerms");
                String startDate = rs.getString("startDate");
                // insert to new employee
                generator = new EmployeeGenerator();
                employee = generator.CreateEmployee(firstName, lastName, (String) ID, bankAccount, salary, empTerms, startDate);
                // update salary and limits
                employee.setCumulativeSalary(rs.getDouble("cumulativeSalary"));
                employee.setShiftsLimit(rs.getInt("shiftsLimit"));
            }
            else
                return null;
            // update qualifications
            stmt = conn.prepareStatement("SELECT eq.qualificationId FROM EmployeeQualifications eq JOIN Qualifications q ON eq.qualificationId = q.qualificationId WHERE eq.employeeID = ?");
            stmt.setString(1, (String) ID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int roleID = rs.getInt("qualificationId");
                employee.addRole(Role.values()[roleID - 1]);
            }
            stmt = conn.prepareStatement("SELECT * FROM EmployeeConstraints ec WHERE ec.employeeID = ?");
            stmt.setString(1, (String) ID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int day = rs.getInt("dayOfWeek");
                int morning = rs.getInt("morningShift");
                int evening = rs.getInt("eveningShift");
                employee.setConstrains(day, 0, morning == 0);
                employee.setConstrains(day, 1, evening == 0);
            }
            // check if it's driver
            if (employee.canDoRole(DRIVER))
            {
                stmt = conn.prepareStatement("SELECT d.licenseId FROM Drivers d WHERE d.employeeID = ?");
                stmt.setString(1, (String) ID);
                rs = stmt.executeQuery();
                Driver driver = null;
                while (rs.next()) {
                    int license = rs.getInt("licenseId");
                    driver = generator.CreateDriver(employee);
                    driver.addLicense(License.values()[license]);
                }
                return driver;
            }
            else
                return employee;
        }
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

    public List<Driver> getNetworkDrivers(){
        return (List<Driver>) newtworkDrivers.values();
    }
}
