package Presentation.GUI_HR;

import BussinesLogic.BranchStore;
import BussinesLogic.DailyShift;
import BussinesLogic.Employee;
import BussinesLogic.Role;
import Presentation.CLI.ShiftOrganizer;
import Service_HR.SManageBranches;
import Service_HR.SManageShifts;

import javax.swing.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShiftScheduling extends JFrame {
    SManageBranches manageBranches;
    private Map<String, Integer> mValues; // Map to store M values
    private Map<String, Integer> eValues; // Map to store E values
    public ShiftScheduling(int id, LocalDate date){
        ShiftsTable table = new ShiftsTable(date, id);
        mValues = new HashMap<>();
        eValues = new HashMap<>();
        manageBranches = new SManageBranches();
        List<Employee> employeeList = manageBranches.getAllEmployees(id);

        List<List<ShiftsTable.CellValues>> cellsData = table.getCellData();
        for(int i=1; i<cellsData.size(); i++) {
            int j = 0;
            for (ShiftsTable.CellValues cellVal : cellsData.get(i)) {
                mValues.put(String.valueOf(Role.values()[j]), Integer.parseInt(cellVal.getValueM()));
                eValues.put(String.valueOf(Role.values()[j]), Integer.parseInt(cellVal.getValueE()));
                j++;
            }
        }
        DailyShift newShift = new DailyShift(date);

        ShiftOrganizer.assignEmployeesToShift(mValues, employeeList,newShift,0);
        StringBuilder outM = ShiftOrganizer.checkShiftValidation(mValues,manageBranches.getStoreKeeper(id),date);
        if(!outM.isEmpty())
            JOptionPane.showMessageDialog(this, outM, "Error - shift " + date + " morning", JOptionPane.ERROR_MESSAGE);

        ShiftOrganizer.assignEmployeesToShift(eValues, employeeList,newShift,1);
        StringBuilder outE = ShiftOrganizer.checkShiftValidation(eValues,manageBranches.getStoreKeeper(id),date);
        if(!outE.isEmpty())
            JOptionPane.showMessageDialog(this, outE, "Error - shift " + date + " evening", JOptionPane.ERROR_MESSAGE);

        SManageShifts SMS = new SManageShifts();
        SMS.insert(newShift, id);
        JOptionPane.showMessageDialog(this, "Shifts for " + date + " added successfully!\n" + newShift.showMeSchedualing(), "Success", JOptionPane.INFORMATION_MESSAGE);


    }
}
