package com.expensemanager.database;

import com.expensemanager.model.Expense;
import com.expensemanager.model.User;
import com.expensemanager.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS expenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "amount REAL NOT NULL, " +
                "payer_id INTEGER NOT NULL, " +
                "room_id INTEGER NOT NULL, " +
                "FOREIGN KEY(payer_id) REFERENCES users(id), " +
                "FOREIGN KEY(room_id) REFERENCES rooms(id))";
        try (Connection conn = DataBaseManager.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creando la tabla expenses: " + e.getMessage());
        }
    }

    public static int insertExpense(double amount, int payerId, int roomId) {
        String sql = "INSERT INTO expenses (amount, payer_id, room_id) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, payerId);
            pstmt.setInt(3, roomId);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error insertando gasto: " + e.getMessage());
        }
        return -1;
    }

    public static List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT e.id, e.amount, u.id AS user_id, u.name, r.id AS room_id, r.name AS room_name " +
                "FROM expenses e " +
                "JOIN users u ON e.payer_id = u.id " +
                "JOIN rooms r ON e.room_id = r.id";
        try (Connection conn = DataBaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User payer = new User(rs.getInt("user_id"), rs.getString("name"));
                Room room = new Room(rs.getInt("room_id"), rs.getString("room_name"));
                expenses.add(new Expense(rs.getInt("id"), rs.getDouble("amount"), payer, room));
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo gastos: " + e.getMessage());
        }
        return expenses;
    }

    public static boolean updateExpense(int id, double newAmount, int newPayerId, int newRoomId) {
        String sql = "UPDATE expenses SET amount = ?, payer_id = ?, room_id = ? WHERE id = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newAmount);
            pstmt.setInt(2, newPayerId);
            pstmt.setInt(3, newRoomId);
            pstmt.setInt(4, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizando la tabla expenses: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE id = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminando gasto: " + e.getMessage());
            return false;
        }
    }

    public static Expense getExpenseById(int id) {
        String sql = "SELECT id, amount, payer_id, room_id FROM expenses WHERE id = ?";

        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = UserDAO.getUserById(rs.getInt("payer_id"));
                int roomId = rs.getInt("room_id");
                double amount = rs.getDouble("amount");
                Room room = RoomDAO.getRoomById(roomId);
                return new Expense(rs.getInt("id"), amount, user, room);
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo usuario por ID: " + e.getMessage());
        }
        return null;
    }

    public double calculateBalance(int userId, int roomId) {
        String sql = "SELECT SUM(CASE WHEN payer_id = ? THEN amount ELSE -amount / (SELECT COUNT(*) FROM room_users WHERE room_id = ?) END) AS balance " +
                "FROM expenses WHERE room_id = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, roomId);
            pstmt.setInt(3, roomId);  // Falta este parámetro para la consulta principal
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("Error al calcular el balance: " + e.getMessage());
        }
        return 0.0;
    }

    public static List<Expense> getExpensesByRoom(int roomId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT id, amount, payer_id, room_id FROM expenses WHERE room_id = ?";

        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User u = UserDAO.getUserById(rs.getInt("payer_id"));
                Room room = RoomDAO.getRoomById(rs.getInt("room_id"));
                expenses.add(new Expense(rs.getInt("id"), rs.getDouble("amount"), u, room));
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo gastos: " + e.getMessage());
        }
        return expenses;
    }
}
