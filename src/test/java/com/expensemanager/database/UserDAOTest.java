package com.expensemanager.database;

import com.expensemanager.model.User;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ordenar pruebas por @Order
public class UserDAOTest {

    @BeforeAll
    static void setupDatabase() {
        DataBaseManager.connect();
        UserDAO.createTable();
    }

    @Test
    @Order(1)
    void testInsertUser() {
        int userId = UserDAO.insertUser("Juan");
        assert(userId > 0);

        User user = UserDAO.getUserById(userId);
        assert(user != null);
        assert(user.getName().equals("Juan"));
    }

    @Test
    @Order(2)
    void testUpdateUser() {
        int userId = UserDAO.insertUser("Ana");
        boolean updated = UserDAO.updateUser(userId, "Ana María");

        assert(updated);
        User user = UserDAO.getUserById(userId);
        assert(user.getName().equals("Ana María"));
    }

    @Test
    @Order(3)
    void testGetAllUsers() {
        List<User> users = UserDAO.getAllUsers();
        assert(!users.isEmpty());
    }

    @Test
    @Order(4)
    void testDeleteUser() {
        int userId = UserDAO.insertUser("Carlos");
        boolean deleted = UserDAO.deleteUser(userId);

        assert(deleted);
        assert(UserDAO.getUserById(userId) == null);
    }
}


