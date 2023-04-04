package src.Module_HR;

public class EmployeeGenerator {

    public Employee CreateEmployee(String firstName, String lastName, String id, String bankAccount, double salary, String empTermsPath, String startDate){
        return new Employee(firstName,lastName,id,bankAccount,salary,empTermsPath,startDate);
    }

}
