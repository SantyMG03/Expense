package com.expensemanager.database;

import com.expensemanager.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS rooms (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL)";
        try (Connection conn = DataBaseManager.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creando la tabla rooms: " + e.getMessage());
        }
    }

    public static int insertRoom(String name) {
        String sql = "INSERT INTO rooms (name) VALUES (?)";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error insertando sala: " + e.getMessage());
        }
        return -1;
    }

    public static List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Connection conn = DataBaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rooms.add(new Room(rs.getInt("id"), rs.getString("name")));
            }

        } catch (SQLException e) {
            System.out.println("Error obteniendo salas: " + e.getMessage());
        }
        return rooms;
    }

    public static boolean updateRoom(int id, String nameName) {
        String sql = "UPDATE rooms SET name = ? WHERE id = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nameName);
            pstmt.setInt(2, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizando sala: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteRoom(int id) {
        String checkExpensesSql = "SELECT COUNT(*) FROM expenses WHERE room_id = ?";
        String deleteRoomSql = "DELETE FROM rooms WHERE id = ?";

        try (Connection conn = DataBaseManager.connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkExpensesSql)) {

            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("No se puede eliminar la sala con ID " + id + " porque tiene gastos registrados.");
                return false;
            }

            // Si no tiene gastos, proceder con la eliminación
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteRoomSql)) {
                deleteStmt.setInt(1, id);
                int affectedRows = deleteStmt.executeUpdate();
                return affectedRows > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error eliminando sala: " + e.getMessage());
            return false;
        }
    }

    public static Room getRoomById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Room(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo room por ID: " + e.getMessage());
        }
        return null;
    }

    public static int getIdByRoom(String roomname) {
        String sql = "SELECT id FROM rooms WHERE name = ?";

        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, roomname);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo sala: " + e.getMessage());
        }
        return -1;
    }
}
