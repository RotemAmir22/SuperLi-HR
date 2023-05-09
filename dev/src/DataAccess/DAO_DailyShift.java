package DataAccess;

import BussinesLogic.*;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

/**
 * This DAO is for branchStore
 * All the dailyShifts saved in DB and BranchStore using it
 */
public class DAO_DailyShift implements IDAO_DailyShift {

    private ArrayList<DailyShift> networkDailyShift;

    private DAO_BranchStore daoBranchStore;
    private Connection conn;


    public DAO_DailyShift() throws SQLException, ClassNotFoundException {
        conn = Database.connect();
        networkDailyShift = new ArrayList<>();
        daoBranchStore = DAO_Generator.getBranchStoreDAO();
    }

    @Override
    public Object findByKey(Object date, Object id) throws SQLException, ClassNotFoundException {
        DailyShift dailyShift = null;
        PreparedStatement stmt = conn.prepareStatement("SELECT date, branchID, endOfDayReport FROM DailyShifts WHERE date = ? AND branchID = ?");
        stmt.setDate(1, (Date) date);
        stmt.setInt(2, (Integer) id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            LocalDate shiftDate = rs.getDate("date").toLocalDate();
            dailyShift = new DailyShift(shiftDate);
            dailyShift.setEndOfDayReport(new File(rs.getString("endOfDayReport")));

        }
        // get the morning employees

        // get the evening employees

        // get shift managers
        return dailyShift;
    }

    public ArrayList<DailyShift> findByBranchID(Object ID) throws SQLException, ClassNotFoundException {
        ArrayList<DailyShift> dailyShifts = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM DailyShifts WHERE branchID = ?");
        statement.setInt(1, (Integer) ID);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            dailyShifts.add((DailyShift) findByKey(rs.getDate("date"), ID));
        }
        return dailyShifts;
    }

    @Override

    public void insert(Object o, Object id) throws SQLException {
        DailyShift dailyShift = (DailyShift) o;
        if (dailyShift != null) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO DailyShifts (date, branchID, endOfDayReport)" +
                    "VALUES (?, ?, ?)");
            stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
            stmt.setString(2, (String) id);
            stmt.setString(3, dailyShift.getEndOfDayReport().toString());
            stmt.executeQuery();

            // add employees
            for (Map.Entry<Role, ArrayList<Employee>> shift : dailyShift.getMorningShift().entrySet()) {
                for (Employee e : shift.getValue()) {
                    stmt = conn.prepareStatement("INSERT INTO MorningShiftEmployees (date, employeeID, role)" + "VALUES(?,?,?)");
                    stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                    stmt.setString(2, e.getId());
                    stmt.setInt(3, shift.getKey().ordinal());
                    stmt.executeQuery();
                }
            }
            for (Map.Entry<Role, ArrayList<Employee>> shift : dailyShift.getEveningShift().entrySet()) {
                for (Employee e : shift.getValue()) {
                    stmt = conn.prepareStatement("INSERT INTO EveningShiftEmployees (date, employeeID, role)" + "VALUES(?,?,?)");
                    stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                    stmt.setString(2, e.getId());
                    stmt.setInt(3, shift.getKey().ordinal());
                    stmt.executeQuery();
                }
            }
            // add shift managers
            for (ShiftManager shiftManager : dailyShift.getShiftManagers()) {
                stmt = conn.prepareStatement("INSERT INTO ShiftManagers (shiftDate, shiftManagerID, fullName, shiftSlot)" + "VALUES(?,?,?,?)");
                stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                stmt.setString(2, shiftManager.getId());
                stmt.setString(3, shiftManager.getFullName());
                stmt.setInt(4, shiftManager.getShiftSlot());
                stmt.executeQuery();

                // add permissions
                for (ShiftM_Permissions perm : shiftManager.getPermissions()) {
                    stmt = conn.prepareStatement("INSERT INTO ShiftM_Permissions (shiftDate, shiftManagerID, permissionName, permissionDescription)" + "VALUES(?,?,?,?)");
                    stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                    stmt.setString(2, shiftManager.getId());
                    stmt.setString(3, perm.getName());
                    stmt.setInt(4, shiftManager.getShiftSlot());
                    stmt.executeQuery();
                }

                // add cancellations
                for (Cancellation cancel : shiftManager.getCancelations()) {
                    stmt = conn.prepareStatement("INSERT INTO Cancellations (shiftDate, shiftManagerID, cancelID, item, amount)" + "VALUES(?,?,?,?,?)");
                    stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                    stmt.setString(2, shiftManager.getId());
                    stmt.setInt(3, cancel.getCancelID());
                    stmt.setString(4, cancel.getItem());
                    stmt.setInt(5, cancel.getAmount());
                    stmt.executeQuery();
                }
            }
        }
    }

    @Override
    public void update(Object o, Object id) throws SQLException, ClassNotFoundException {
        DailyShift dailyShift = (DailyShift) o;
        if (dailyShift != null) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE DailyShifts SET date = ?, branchID = ?, endOfDayReport = ?");
            stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
            stmt.setString(2, (String) id);
            stmt.setString(3, dailyShift.getEndOfDayReport().toString());
            stmt.executeQuery();

            // add employees
            for (Map.Entry<Role, ArrayList<Employee>> shift : dailyShift.getMorningShift().entrySet()) {
                for (Employee e : shift.getValue()) {
                    stmt = conn.prepareStatement("UPDATE MorningShiftEmployees SET date = ?, employeeID = ?, role = ?");
                    stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                    stmt.setString(2, e.getId());
                    stmt.setInt(3, shift.getKey().ordinal());
                    stmt.executeQuery();
                }
            }
            for (Map.Entry<Role, ArrayList<Employee>> shift : dailyShift.getEveningShift().entrySet()) {
                for (Employee e : shift.getValue()) {
                    stmt = conn.prepareStatement("UPDATE EveningShiftEmployees date = ?, employeeID = ?, role = ?");
                    stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                    stmt.setString(2, e.getId());
                    stmt.setInt(3, shift.getKey().ordinal());
                    stmt.executeQuery();
                }
            }
            // add shift managers
            for (ShiftManager shiftManager : dailyShift.getShiftManagers()) {
                stmt = conn.prepareStatement("UPDATE ShiftManagers SET shiftDate = ?, shiftManagerID = ?, fullName = ?, shiftSlot = ?");
                stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                stmt.setString(2, shiftManager.getId());
                stmt.setString(3, shiftManager.getFullName());
                stmt.setInt(4, shiftManager.getShiftSlot());
                stmt.executeQuery();

                // add permissions
                for (ShiftM_Permissions perm : shiftManager.getPermissions()) {
                    stmt = conn.prepareStatement("UPDATE ShiftM_Permissions SET shiftDate = ?, shiftManagerID = ?, permissionName = ?, permissionDescription = ?");
                    stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                    stmt.setString(2, shiftManager.getId());
                    stmt.setString(3, perm.getName());
                    stmt.setInt(4, shiftManager.getShiftSlot());
                    stmt.executeQuery();
                }

                // add cancellations
                for (Cancellation cancel : shiftManager.getCancelations()) {
                    stmt = conn.prepareStatement("UPDATE Cancellations SET shiftDate = ?, shiftManagerID = ?, cancelID = ?, item = ?, amount = ?");
                    stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                    stmt.setString(2, shiftManager.getId());
                    stmt.setInt(3, cancel.getCancelID());
                    stmt.setString(4, cancel.getItem());
                    stmt.setInt(5, cancel.getAmount());
                    stmt.executeQuery();
                }
            }
        }

    }

    @Override
    public void delete(Object date, Object id) throws SQLException, ClassNotFoundException {
        DailyShift dailyShift = (DailyShift) findByKey(date,id);
        if (dailyShift != null) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM DailyShifts WHERE date = ? AND branchID = ?");
            stmt.setDate(1, (Date) date);
            stmt.setString(2, (String) id);
            stmt.executeQuery();

            // add employees
            for (Map.Entry<Role, ArrayList<Employee>> shift : dailyShift.getMorningShift().entrySet()) {
                for (Employee e : shift.getValue()) {
                    stmt = conn.prepareStatement("DELETE FROM MorningShiftEmployees WHERE date = ? AND employeeID = ?");
                    stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                    stmt.setString(2, e.getId());
                    stmt.executeQuery();
                }
            }
            for (Map.Entry<Role, ArrayList<Employee>> shift : dailyShift.getEveningShift().entrySet()) {
                for (Employee e : shift.getValue()) {
                    stmt = conn.prepareStatement("DELETE FROM EveningShiftEmployees WHERE date = ? AND employeeID = ?");
                    stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                    stmt.setString(2, e.getId());
                    stmt.executeQuery();
                }
            }
            // add shift managers
            for (ShiftManager shiftManager : dailyShift.getShiftManagers()) {
                stmt = conn.prepareStatement("DELETE FROM ShiftManagers WHERE shiftDate = ? AND shiftManagerID = ?");
                stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                stmt.setString(2, shiftManager.getId());
                stmt.executeQuery();

                // add permissions
                for (ShiftM_Permissions perm : shiftManager.getPermissions()) {
                    stmt = conn.prepareStatement("DELETE FROM ShiftM_Permissions WHERE shiftDate = ? AND shiftManagerID = ?");
                    stmt.setDate(1, java.sql.Date.valueOf(dailyShift.getDate()));
                    stmt.setString(2, shiftManager.getId());
                    stmt.executeQuery();
                }

            }
        }
        networkDailyShift.remove(dailyShift);

    }
}

