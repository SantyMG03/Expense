package com.expensemanager.database;

import com.expensemanager.model.Room;
import com.expensemanager.model.User;

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
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
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
        String sql = "DELETE FROM rooms WHERE id = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminando sala: " + e.getMessage());
            return false;
        }
    }

    public static Room getRoomById(int id) {
        String sql = "SELECT id, name FROM rooms WHERE id = ?";

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

    public static boolean addUserToRoom(int userId, int roomId) {
        System.out.println("Añadiendo usuario con ID " + userId + " a la sala con ID " + roomId);
        String sql = "INSERT INTO room_users (room_id, user_id) VALUES (?, ?)";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar usuario a la sala: " + e.getMessage());
            return false;
        }
    }

    public static boolean isUserInRoom(int userId, int roomId) {
        String sql = "SELECT COUNT(*) FROM room_users WHERE user_id = ? AND room_id = ?";

        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, roomId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error al verificar usuario en la sala: " + e.getMessage());
            return false;
        }
    }

    public static List<User> getUsersInRoom(int roomId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.id, u.name FROM users u " +
                "JOIN room_users ru ON u.id = ru.user_id " +
                "WHERE ru.room_id = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo usuarios en la sala: " + e.getMessage());
        }
        return users;
    }
}
