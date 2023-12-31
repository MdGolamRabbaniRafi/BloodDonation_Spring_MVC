package dev.Repository.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String URL = "jdbc:mysql://localhost:3306/final_lab_task-1";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public ConnectionManager() {
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/final_lab_task-1", "root", "1234");
    }
}
