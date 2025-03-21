package com.expensemanager.database;

import com.expensemanager.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /**
     * Metodo para crear la tabla de usuario con los parametros id, name y psw
     *  -   id: sera un valor entero asignado automaticamente e incrementado automaticamente
     *  -   name: sera un valor String con el nombre del usuario
     *  -   psw: sera un valor String con la contraseña del usuario
     *  */
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL UNIQUE, psw TEXT NOT NULL);";
        try (Connection conn = DataBaseManager.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table" + e.getMessage());
        }
    }

    /**
     * Metodo para insertar un usario
     * Todo -> Encriptar la contraseña del usuario
     * @param name nombre del usuario
     * @param psw contraseña del usuario
     * @return True si se inserto correctamente, False en otro caso
     */
    public static boolean insertUser(String name, String psw) {
        String sql = "INSERT INTO users (name, psw) VALUES (?, ?)";
        if (name.isBlank() || name.isEmpty() || psw.isBlank() || psw.isEmpty()) {
            return false;
        }
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, psw);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar un usuario" + e.getMessage());
            return false;
        }
    }

    /**
     * Metodo para obtener un usuario a partir de un id
     * @param id id del usuario a localizar
     * @return Un usuario o null en otro caso
     */
    public static User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("name"), rs.getString("psw"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Metodo para obtener un usuario a partir de un nombre
     * @param name nombre del usuario
     * @return Un usario o null en otro caso
     */
    public static User getUserByName(String name) {
        String sql = "SELECT name FROM users WHERE name = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("name"), rs.getString("psw"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Metodo para obtene un id de un usuario a parti del nombre
     * @param name nombre del usuario
     * @return id del usuario o -1 si hubo algun error
     */
    public static int getUserIdByName(String name) {
        String sql = "SELECT id FROM users WHERE name = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Echar un vistazo a este metdo si necesita una modificacion
    public static boolean validateUser(String name, String psw) {
        String sql = "SELECT COUNT(*) FROM users WHERE name = ? AND psw = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, psw);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo para obtener todos los usuarios de la base de datos
     * @return Una lista vacia si no hay usuarios o una lista con los nombres de los usuarios
     */
    public static List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT name FROM users";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){

            while (rs.next()) {
                users.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Metodo para borrar un usuario por su nombre
     * @param name nombre del usuario a borra
     * @return True si se borro correctamente, false si no
     */
    public static boolean deleteUser(String name) {
        String sql = "DELETE FROM users WHERE name = ?";
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
