package Module_HR_Part1.src;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

public class EmployeeGenerator {

    public Employee CreateEmployee(String firstName, String lastName, String id, String bankAccount, double salary, String empTermsPath, LocalDate startDate){
        File empTerms = new File(empTermsPath);
        return new Employee(firstName,lastName,id,bankAccount,salary,empTerms,startDate);
    }

}
