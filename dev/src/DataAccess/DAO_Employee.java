package DataAccess;
import BussinesLogic.*;
import BussinesLogic.Driver;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static BussinesLogic.Role.DRIVER;


public class DAO_Employee implements DAO{

    private Map<String ,Employee> networkEmployees;

    private List<Employee> employeeList;
    private Map<String, Driver> newtworkDrivers;

    private List<Driver> driverList;

    private Connection conn;

    // constructor
    public DAO_Employee() throws SQLException, ClassNotFoundException {
        conn = Database.connect();
        networkEmployees = new HashMap<>();
        newtworkDrivers = new HashMap<>();
        employeeList = new ArrayList<>();
        driverList = new ArrayList<>();
    }

    /**
     * find an Employee (or Driver) by their ID
     * @param ID of the employee
     * @return the employee or null if not exist
     * @throws SQLException in case of error
     */
    @Override
    public Object findByID(Object ID) throws SQLException {
        if (networkEmployees != null && networkEmployees.containsKey(ID))
                return networkEmployees.get(ID);
        else if(newtworkDrivers != null && newtworkDrivers.containsKey(ID))
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
                employee.addRole(Role.values()[roleID]);
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
                newtworkDrivers.put((String) ID,driver);
                return driver;
            }
            else {
                networkEmployees.put((String) ID,employee);
                return employee;
            }
        }
    }

    /**
     * adds an employee to the DB and also the matching map
     * NO QUALIFICATIONS AT THIS POINT
     * @param o - new employee
     * @throws SQLException incase of an error
     */
    @Override
    public void insert(Object o) throws SQLException
    {
        Employee e = (Employee)o;
        if(e != null)
        {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Employees (firstName, lastName, employeeID, bankAccount, salary, empTerms, startDate, shiftsLimit, cumulativeSalary)" +
                    "VALUES (e.getFirstName(), e.getLastName(), e.getId(), e.getBankAccount(), e.getSalary(), e.getEmpTerms(), e.getStartDate(), e.getShiftsLimit(), e.getCumulativeSalary())");
            stmt.executeQuery();

            //add to the right map
            if(e.canDoRole(DRIVER))
                newtworkDrivers.put(e.getId(), (Driver) e);
            else
                networkEmployees.put(e.getId(),e);
        }
    }

    /**
     * update employees details
     * @param o employee to update
     * @throws SQLException in case of an error
     */
    @Override
    public void update(Object o) throws SQLException {
        Employee e = (Employee)o;
        if(e != null)
        {
            //set details
            PreparedStatement stmt = conn.prepareStatement("UPDATE Employees SET firstName = ?, lastName = ?, bankAccount = ?, salary = ?, empTerms = ?, startDate = ?, shiftsLimit = ?, cumulativeSalary = ? WHERE employeeID = e.getId()");
            stmt.setString(1, e.getFirstName());
            stmt.setString(2, e.getLastName());
            stmt.setString(3, e.getBankAccount());
            stmt.setDouble(4, e.getSalary());
            stmt.setString(5, e.getEmpTerms());
            stmt.setDate(6, (Date.valueOf(e.getStartDate())));
            stmt.setInt(7, e.getShiftsLimit());
            stmt.setDouble(8, e.getCumulativeSalary());
            stmt.executeUpdate();

            //set constraints
            for (Days day : Days.values())
            {
                stmt = conn.prepareStatement("UPDATE EmployeeConstraints SET morningShift = ?, eveningShift = ? WHERE employeeID = e.getId() AND dayOfWeek = day.ordianl()");
                stmt.setInt(1, e.getConstraints()[day.ordinal()][0]? 1:0);
                stmt.setInt(2, e.getConstraints()[day.ordinal()][1]? 1:0);
            }

            //set qualifications
            List<Role> roles = e.getQualifications();
            stmt = conn.prepareStatement("SELECT * FROM EmployeeQualification WHERE employeeID = ? ");
            stmt.setString(1,e.getId());
            ResultSet rs = stmt.executeQuery();
            rs.last();
            int amount = rs.getRow();
            rs.beforeFirst();

            //remove role from DB
            if(amount > roles.size()){
                while(rs.next()){
                    int index = rs.getInt("qualificationId");
                    if(!roles.contains(Role.values()[index])) {
                        stmt = conn.prepareStatement("DELETE FROM EmployeeQualification WHERE employeeID = e.getId() AND qualificationId = index");
                        stmt.executeQuery();
                        break;
                    }
                }
            }

            //add role to DB
            else if(amount < roles.size()){
                ArrayList<Integer> rolesInDB = new ArrayList<>();
                while(rs.next()) {
                    rolesInDB.add(rs.getInt("qualificationId"));
                }
                for(Role role: roles){
                    if(!rolesInDB.contains(role.ordinal())) {
                        stmt = conn.prepareStatement("INSERT INTO EmployeeQualification (employeeID,qualificationId) VALUES (e.getId(), role.ordinal())");
                        stmt.executeQuery();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void delete(Object o) {

    }

    public List<Employee> getNetworkEmployees() throws SQLException {
        if(networkEmployees.isEmpty())
        {
            PreparedStatement stmt = conn.prepareStatement("SELECT employeeID FROM Employees");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                findByID(rs.getString("employeeID"));
            }
        }
        employeeList.addAll(networkEmployees.values());
        return employeeList;
    }

    public List<Driver> getNetworkDrivers(){
        return (List<Driver>) newtworkDrivers.values();
    }
}
