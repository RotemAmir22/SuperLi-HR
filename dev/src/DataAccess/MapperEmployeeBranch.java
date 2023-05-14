package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MapperEmployeeBranch {

    private Connection conn;
    public MapperEmployeeBranch() throws SQLException, ClassNotFoundException {
        //conn = Database.connect();
    }

    public void insert(String eID, Integer bID){
        try {
            conn = Database.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT branchID FROM BranchStore WHERE branchID = ?");
            stm.setInt(1,bID);
            ResultSet rs = stm.executeQuery();
            if(rs != null){
                stm = conn.prepareStatement("SELECT employeeID FROM Employees WHERE employeeID = ?");
                stm.setString(1,eID);
                rs = stm.executeQuery();
                if(rs != null){
                    stm = conn.prepareStatement("INSERT INTO EmployeeBranches (employeeID, branchID)" + "VALUES(?,?)");
                    stm.setString(1,eID);
                    stm.setInt(2,bID);
                    stm.executeUpdate();
                    stm.close();
                    rs.close();
                    System.out.println("Employee " + eID + " was added success to branch " + bID);
                }
                else
                    System.out.println("Employee not found.");
            }
            else
                System.out.println("Branch not found.");
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
