package com.expensemanager.database;

import com.expensemanager.model.User;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ordenar pruebas por @Order
public class UserDAOTest {

    @BeforeAll
    static void setupDatabase() {
        UserDAO.createTable();
    }

    @BeforeEach
    void cleanDatabase() {
        // Limpiar la tabla antes de cada prueba
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM users")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("Error al limpiar la base de datos: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    void testInsertUser() {
        assertTrue(UserDAO.insertUser("testUser", "testPass"), "El usuario debería insertarse correctamente.");
    }

    @Test
    @Order(2)
    void testGetAllUsers() {
        List<String> users = UserDAO.getAllUsers();
        assertTrue(users.isEmpty());
        assertTrue(UserDAO.insertUser("testUser", "testPass"), "El usuario debería insertarse correctamente.");
        users = UserDAO.getAllUsers();
        assertFalse(users.isEmpty());
    }

    @Test
    @Order(3)
    void testDeleteUser() {
        assertTrue(UserDAO.insertUser("testUser", "testPass"), "El usuario debería insertarse correctamente.");
        assertTrue(UserDAO.deleteUser("testUser"), "El usuario no existe");
    }

    @Test
    @Order(4)
    void testInsertDuplicateUser() {
        UserDAO.insertUser("testUser", "testPass");
        assertFalse(UserDAO.insertUser("testUser", "newPass"), "No debería permitirse un usuario duplicado.");
    }

    @Test
    @Order(5)
    void testInsertUserEmptyName() {
        assertFalse(UserDAO.insertUser("", "testPass"), "No debería insertarse un usuario con nombre vacío.");
    }

    @Test
    @Order(6)
    void testInsertUserEmptyPassword() {
        assertFalse(UserDAO.insertUser("testUser", ""), "No debería insertarse un usuario con contraseña vacía.");
    }
}


