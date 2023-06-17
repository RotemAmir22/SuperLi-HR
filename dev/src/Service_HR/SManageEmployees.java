package Service_HR;

import BussinesLogic.*;
import DataAccess.DAO_Employee;
import DataAccess.DAO_Generator;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static BussinesLogic.Role.DRIVER;

/**
 * This class assist for the GUI_HR in management employees
 */
public class SManageEmployees extends AValidateInput{
    DAO_Employee employeesDAO;
    public SManageEmployees() {
        try {
            employeesDAO = DAO_Generator.getEmployeeDAO();
        }catch ( SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Insert new employee to system, include update the DB
     * @param FN first name
     * @param LN last name
     * @param ID id num
     * @param BA bank account
     * @param SA salary
     * @param TE terms
     * @param SD start date
     * @param driver boolean variable
     * @return if the process success or not
     */
    public boolean insertNewEmployee(String FN, String LN, String ID, String BA, String SA, String TE, String SD, boolean driver, List<License> licenses){
        try{
            EmployeeGenerator employeeGenerator = new EmployeeGenerator();
            Employee employee;
            //create new employee
            if(this.isDouble(SA) && this.isDate(SD)) {
                employee = employeeGenerator.CreateEmployee(FN, LN, ID, BA, Double.parseDouble(SA), TE,SD);
                //add employee to list of all employees in network and update the DB
                employeesDAO.insert(employee);
                if(driver)
                    insertDriver(employee, licenses);
            }
            else
                return false;
        }
        catch (SQLException e){
            return false;
        }
        return true;
    }

    private void insertDriver(Employee e, List<License> l)
    {
        try{
            EmployeeGenerator employeeGenerator = new EmployeeGenerator();
            Driver d = employeeGenerator.CreateDriver(e);
            for(License license : l)
                d.addLicense(license);
            employeesDAO.update(d);
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    /**
     * Update an employee by the user choice
     * @param type of updating
     * @param ID of the existing employee
     * @param input some string to update by
     * @return if the action success
     */
    public boolean update(int type, String ID, String input){
        try {
            Employee e = (Employee) employeesDAO.findByID(ID);
            switch (type) {
                case 1:
                    e.setBankAccount(input);
                    break;
                case 2:
                    e.setSalary(Double.parseDouble(input));
                    break;
                case 3:
                    e.setEmpTerms(input);
                    break;
                case 4:
                    e.setCumulativeSalary(e.getCumulativeSalary() + Double.parseDouble(input));
                    break;
                case 5:
                    e.addRole(Role.valueOf(input));
                    break;
                case 6:
                    e.removeRole(Role.valueOf(input));
                    break;
                case 7:
                    for(Driver d : employeesDAO.getNetworkDrivers())
                        if(d.getId().equals(ID)) {
                            d.addLicense(License.valueOf(input));
                            e = d;
                            break;
                        }
                    break;
                case 8:
                    for(Driver d : employeesDAO.getNetworkDrivers())
                        if(d.getId().equals(ID)) {
                            d.removeLicense(License.valueOf(input));
                            e = d;
                            break;
                        }
                    break;
                case 9:
                    if(input.equals("reset"))
                        e.setCumulativeSalary(0);
                    break;
                case 10:
                    if(input.equals("reset"))
                        e.setShiftsLimit(6);
                    break;
                default:
                    break;
            }
            employeesDAO.update(e);
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    /**
     * Search if there is employee with this ID
     * @param input - id of employee
     * @return true if yes, else false
     */
    public boolean searchEmployee(String input){
        try{
            Employee e = (Employee) employeesDAO.findByID(input);
            if(e != null)
                return true;
        }catch (SQLException e){
            return false;
        }
        return false;

    }

    public boolean setConstraints(String id, boolean[][] constraints){
        try{
            Employee e = ((Employee)employeesDAO.findByID(id));
            e.setConstrains(constraints);
            employeesDAO.update(e);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public int[][] getConstraints(String ID){
        try{
            boolean[][] res = ((Employee)employeesDAO.findByID(ID)).getConstraints();
            int[][] toReturn = new int[7][2];
            for(int i=0; i<7; i++) {
                toReturn[i][0] = res[i][0] ? 0 : 1;
                toReturn[i][1] = res[i][1] ? 0 : 1;
            }
            return toReturn;
        } catch (SQLException e) {
            return null;
        }
    }
    /**
     * Check if the employee by this ID is a driver
     * @param ID of the employee
     * @return true if yes
     */
    public boolean isDriver(String ID){
        try{
            Employee e = (Employee) employeesDAO.findByID(ID);
            return e.canDoRole(DRIVER);
        }
        catch (SQLException e){
            return false;

        }

    }

    /**
     * Check if an employee can do this role
     * @param ID of the employee
     * @param role from Role enum
     * @return true if yes else false
     */
    public boolean existRole(String ID, Role role) {
        try {
            if (searchEmployee(ID)) {
                Employee curE = (Employee) employeesDAO.findByID(ID);
                return curE.canDoRole(role);
            }
            return false;
        }catch (SQLException e){
            return false;
        }
    }

    /**
     * Check if the driver has this license
     * @param ID of the driver
     * @param license of the driver
     * @return true if he has this license, else false
     */
    public boolean existLicense(String ID, License license) {
        try {
            if (isDriver(ID)) {
                for(Driver d : employeesDAO.getNetworkDrivers())
                    if(d.getId().equals(ID))
                        return d.getLicenses().contains(license);
            }
            return false;
        }catch (SQLException e){
            return false;
        }
    }

    /**
     * Insure the role is currect enum
     * @param role to search
     * @return true if yes
     */
    public boolean validateRole(String role)
    {
        for (Role r : Role.values()) {
            if(role.equals(r.name()))
                return true;
        }
        return false;
    }

    /**
     * Insure the role is currect enum
     * @param license to search
     * @return true if yes
     */
    public boolean validateLicence(String license)
    {
        for (License l : License.values()) {
            if(license.equals(l.name()))
                return true;
        }
        return false;
    }

    /**
     * Get all employees, include drivers
     * @return List of all employees
     */
    public List<Employee> getAllEmployees()
    {
        try {
            List<Employee> all = employeesDAO.getNetworkEmployees();
            all.addAll(employeesDAO.getNetworkDrivers());
            return all;
        }
        catch (SQLException e){
            return null;
        }
    }

    /**
     * Get a driver
     * @param ID of the driver employee
     * @return the driver
     */
    public Driver getDriver(String ID) {
        try {
            if (isDriver(ID)) {
                for(Driver d : employeesDAO.getNetworkDrivers())
                    if(d.getId().equals(ID))
                        return d;
            }
            return null;
        }catch (SQLException e){
            return null;
        }
    }

}
