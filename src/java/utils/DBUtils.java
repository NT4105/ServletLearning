package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtils {

    private static final String hostname = "localhost";
    private static final String port = "1433";
    private static final String databaseName = "ServletLearning";
    private static final String username = "SA";
    private static final String password = "12345";

    private static final String url = "jdbc:sqlserver://" + hostname + ":" + port + ";databaseName=" + databaseName;

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}