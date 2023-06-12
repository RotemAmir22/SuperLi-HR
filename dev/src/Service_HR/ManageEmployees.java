package Service_HR;

import BussinesLogic.Employee;
import BussinesLogic.EmployeeGenerator;
import DataAccess.DAO_Employee;
import DataAccess.DAO_Generator;

import java.sql.SQLException;

public class ManageEmployees extends AValidateInput{

    public ManageEmployees(){

    }
    public boolean insertNewEmployee(String FN, String LN, String ID, String BA, String SA, String TE, String SD, boolean driver){
        DAO_Employee employeesDAO;
        try{
            employeesDAO = DAO_Generator.getEmployeeDAO();
            EmployeeGenerator employeeGenerator = new EmployeeGenerator();
            Employee employee;
            //create new employee
            if(this.isDouble(SA) && this.isDate(SD)) {
                employee = employeeGenerator.CreateEmployee(FN, LN, ID, BA, Double.parseDouble(SA), TE,SD);
                //add employee to list of all employees in network and update the DB
                employeesDAO.insert(employee);
            }
            else
                return false;
            if(driver)
                employeeGenerator.CreateDriver(employee);
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return true;
    }
}
