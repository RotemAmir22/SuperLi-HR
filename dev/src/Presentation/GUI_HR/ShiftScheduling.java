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
    List<Employee> employeeList;
    LocalDate date;
    int id;

    private ShiftsTable table;
    private Map<String, Integer> mValues; // Map to store M values
    private Map<String, Integer> eValues; // Map to store E values
    public ShiftScheduling(int id, LocalDate date, ShiftsTable table){
        this.id = id;
        this.date = date;
        this.table = table;
        mValues = new HashMap<>();
        eValues = new HashMap<>();
        manageBranches = new SManageBranches();
        employeeList = manageBranches.getAllEmployees(id);

    }
    public void submit() {
        List<List<ShiftsTable.CellValues>> cellsData = table.getCellData();
        int day = 0;
        int valM = 0;
        int valE = 0;
        for (int row = 1; row < cellsData.size(); row++) {
            for (int role = 0; role < cellsData.get(row).size() - 1; role++) {
                ShiftsTable.CellValues cellVal = cellsData.get(role).get(row);
                valM = Integer.parseInt(cellVal.getValueM());
                valE = Integer.parseInt(cellVal.getValueE());
                if(table.getRowsName(role).equalsIgnoreCase("SHIFTMANAGER")){
                    if(valM == 0) {
                        while (valM <= 0) {
                            valM = Integer.parseInt(JOptionPane.showInputDialog(null, "Morning Shift - " + date.plusDays(row -1) + "\nMust have at least 1 Shift-Manager, enter new number:"));
                            if (!manageBranches.isInteger(valM))
                                JOptionPane.showMessageDialog(null, "Invalid input!\nEnter a number", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        cellVal.setValueM(String.valueOf(valM));
                    }
                    if(valE == 0) {
                        while (valE <= 0) {
                            valE = Integer.parseInt(JOptionPane.showInputDialog(null, "Evening Shift - " + date.plusDays(row - 1) + "\nMust have at least 1 Shift-Manager, enter new number:"));
                            if (!manageBranches.isInteger(valE))
                                JOptionPane.showMessageDialog(null, "Invalid input!\nEnter a number", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        cellVal.setValueE(String.valueOf(valE));
                    }
                }
                mValues.put(table.getRowsName(role).toUpperCase(), valM);
                eValues.put(table.getRowsName(role).toUpperCase(), valE);
            }
            buildShift(day++);
            if (day == 7)
                break;
        }
    }


    private void buildShift(int i)
    {
        LocalDate currentDate = date.plusDays(i);
        DailyShift newShift = new DailyShift(currentDate);

        newShift = ShiftOrganizer.assignEmployeesToShift(mValues, employeeList, newShift, 0);
        StringBuilder outM = ShiftOrganizer.checkShiftValidation(mValues, manageBranches.getStoreKeeper(id), currentDate);
        if (!outM.isEmpty())
            JOptionPane.showMessageDialog(this, outM, "Error - shift " + currentDate + " morning", JOptionPane.ERROR_MESSAGE);

        newShift = ShiftOrganizer.assignEmployeesToShift(eValues, employeeList, newShift, 1);
        StringBuilder outE = ShiftOrganizer.checkShiftValidation(eValues, manageBranches.getStoreKeeper(id), currentDate);
        if (!outE.isEmpty())
            JOptionPane.showMessageDialog(this, outE, "Error - shift " + currentDate + " evening", JOptionPane.ERROR_MESSAGE);

        SManageShifts SMS = new SManageShifts();
        SMS.insert(newShift, id);
        JOptionPane.showMessageDialog(this, "Shifts for " + currentDate + " added successfully!\n" + newShift.showMeSchedualing(), "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
