package DataAccess;

import java.sql.*;
public class Database {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/PC/Documents/GitHub/ADSS_Group_H/identifier.sqlite";
    public static Connection connect() throws SQLException {return DriverManager.getConnection(DB_URL);
    }
}
