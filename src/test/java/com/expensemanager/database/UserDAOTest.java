package com.expensemanager.database;

import com.expensemanager.model.User;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(userId).isGreaterThan(0);

        User user = UserDAO.getUserById(userId);
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("Juan");
    }

    @Test
    @Order(2)
    void testUpdateUser() {
        int userId = UserDAO.insertUser("Ana");
        boolean updated = UserDAO.updateUser(userId, "Ana María");

        assertThat(updated).isTrue();
        User user = UserDAO.getUserById(userId);
        assertThat(user.getName()).isEqualTo("Ana María");
    }

    @Test
    @Order(3)
    void testGetAllUsers() {
        List<User> users = UserDAO.getAllUsers();
        assertThat(users).isNotEmpty();
    }

    @Test
    @Order(4)
    void testDeleteUser() {
        int userId = UserDAO.insertUser("Carlos");
        boolean deleted = UserDAO.deleteUser(userId);

        assertThat(deleted).isTrue();
        assertThat(UserDAO.getUserById(userId)).isNull();
    }
}


