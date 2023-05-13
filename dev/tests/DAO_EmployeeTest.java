import BussinesLogic.Driver;
import BussinesLogic.Employee;
import BussinesLogic.EmployeeGenerator;
import DataAccess.DAO_Employee;
import DataAccess.DAO_Generator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class DAO_EmployeeTest {

    private DAO_Employee testDAOemployees;
    private Employee e;
    private Employee e1;
    private Employee e2;
    private Employee e3;

    @Before
    // test also insert and find
    public void setUp() {
        e = new Employee("person","test","0134224","943-17689",
                780,"Salary - 780","2023-01-03");
        e1 = new Employee("person1","test","111333","943-17689",
                780,"Salary - 780","2023-01-03");
        e2 = new Employee("person2","test","555444","943-17689",
                780,"Salary - 780","2023-01-03");
        e3 = new Employee("person3","test","777222","943-17689",
                780,"Salary - 780","2023-01-03");
        try {
            testDAOemployees = DAO_Generator.getEmployeeDAO();
            testDAOemployees.insert(e);
            Employee tmp = (Employee) testDAOemployees.findByID(e.getId()); // also test findByID
            assertEquals(e.getId(),tmp.getId());
        }
        catch (SQLException | ClassNotFoundException s){
            System.out.println(s);
        }
    }

    @Test
    public void update() {
        try {
            Employee e = (Employee) testDAOemployees.findByID("0134224");
            assertNotNull(e);
            e.setShiftsLimit(5);
            testDAOemployees.update(e);
            Employee tmp = (Employee) testDAOemployees.findByID("0134224");
            assertEquals(5,tmp.getShiftsLimit());
        }
        catch (SQLException s){
            System.out.println(s);
        }
    }

    @After
    public void delete() {
        try{
            Employee emTodelete = (Employee)testDAOemployees.findByID("0134224");
            assertNotNull(emTodelete);
            testDAOemployees.delete(emTodelete);
            assertNull(emTodelete);

            emTodelete = (Employee) testDAOemployees.findByID("111333");
            assertNotNull(emTodelete);
            testDAOemployees.delete(emTodelete);
            emTodelete = (Employee) testDAOemployees.findByID("111333");
            assertNull(emTodelete);

            emTodelete = (Employee) testDAOemployees.findByID("555444");
            assertNotNull(emTodelete);
            testDAOemployees.delete(emTodelete);
            emTodelete = (Employee) testDAOemployees.findByID("555444");
            assertNull(emTodelete);

            emTodelete = (Employee) testDAOemployees.findByID("777222");
            assertNotNull(emTodelete);
            testDAOemployees.delete(emTodelete);
            emTodelete = (Employee) testDAOemployees.findByID("777222");
            assertNull(emTodelete);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getNetworkEmployees() {

        try{
            testDAOemployees.insert(e1);
            testDAOemployees.insert(e2);
            testDAOemployees.insert(e3);
            e1.setShiftsLimit(5);
            e2.setConstrains(1,1,false);
            e3.setSalary(800);
            testDAOemployees.update(e1);
            testDAOemployees.update(e2);
            testDAOemployees.update(e3);
            assertTrue(testDAOemployees.getNetworkEmployees().contains(e1));
            assertTrue(testDAOemployees.getNetworkEmployees().contains(e2));
            assertTrue(testDAOemployees.getNetworkEmployees().contains(e3));
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getNetworkDrivers() {
        try{
            Employee e = (Employee) testDAOemployees.findByID("0134224");
            assertNotNull(e);
            EmployeeGenerator employeeGenerator = new EmployeeGenerator();
            Driver driver = employeeGenerator.CreateDriver(e);
            assertNotNull(driver);
            testDAOemployees.update(driver);
            assertTrue(testDAOemployees.getNetworkDrivers().contains(driver));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}