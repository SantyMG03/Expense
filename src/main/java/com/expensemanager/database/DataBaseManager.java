package com.expensemanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    private static final String URL = "jdbc:sqlite:expensemanager.db";

    public static Connection connect() {
        Connection conn = null;
        try{
            return DriverManager.getConnection(URL);
        }catch (SQLException e) {
            System.out.println("Error al conectar" + e.getMessage());
            return null;
        }
    }

}
