package com.expensemanager.database;

import com.expensemanager.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL)";

        try (Connection conn = DataBaseManager.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creando la tabla users: " + e.getMessage());
        }
    }

    public static int insertUser(String name) {
        String sql = "INSERT INTO users (name) VALUES (?)";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error insertando usuario: " + e.getMessage());
        }
        return -1;
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DataBaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
                 while (rs.next()) {
                     users.add(new User(rs.getInt("id"), rs.getString("name")));
                 }

        } catch (SQLException e) {
            System.out.println("Error obteniendo usuarios: " + e.getMessage());
        }
        return users;
    }

    public static boolean updateUser(int id, String name) {
        String sql = "UPDATE users SET name = ? WHERE id = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizando usuario: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteUser(int id) {
        String checkExpensesSql = "SELECT COUNT(*) FROM expenses WHERE payer_id = ?";
        String deleteUserSql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DataBaseManager.connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkExpensesSql)) {

            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("No se puede eliminar el usuario con ID " + id + " porque tiene gastos registrados.");
                return false;
            }

            // Si no tiene gastos, proceder con la eliminación
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteUserSql)) {
                deleteStmt.setInt(1, id);
                int affectedRows = deleteStmt.executeUpdate();
                return affectedRows > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error eliminando usuario: " + e.getMessage());
            return false;
        }
    }

    public static User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo usuario por ID: " + e.getMessage());
        }
        return null;
    }

    public static int getIdByUser(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo ID por usuario: " + e.getMessage());
        }
        return -1;
    }
}
