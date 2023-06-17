package Presentation.GUI_HR;

import BussinesLogic.BranchStore;
import BussinesLogic.Employee;
import Service_HR.SManageBranches;

import javax.swing.*;
import java.util.List;

public class ShiftScheduling extends JFrame {

    SManageBranches manageBranches;
    public ShiftScheduling(int id){
        manageBranches = new SManageBranches();
        List<Employee> employeeList = manageBranches.getAllEmployees(id);

    }
}
