package com.expensemanager.database;

import com.expensemanager.model.Expense;
import com.expensemanager.model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseDAOTest {

    @BeforeAll
    static void setupDatabase() {
        DataBaseManager.connect();
        UserDAO.createTable();
        RoomDAO.createTable();
        ExpenseDAO.createTable();
    }

    @BeforeEach
    void cleanDatabase() {
        // Limpiar la tabla antes de cada prueba
        try (Connection conn = DataBaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM expenses")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("Error al limpiar la base de datos: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    void testInsertExpense() {
        boolean userId = UserDAO.insertUser("Julia", "1098");
        int roomId = RoomDAO.insertRoom("Fiesta de fin de año");
        assert (userId);

        int expenseId = ExpenseDAO.insertExpense(200.0, 19, roomId);
        assert(expenseId > 0);

        Expense expense = ExpenseDAO.getExpenseById(expenseId);
        assert(expense != null);
        assert(expense.getAmount() == 200.0);
    }

    @Test
    @Order(2)
    void testUpdateExpense() {
        boolean userId = UserDAO.insertUser("Pedro", "3232");
        int roomId = RoomDAO.insertRoom("Cena con amigos");
        int expenseId = ExpenseDAO.insertExpense(150.0, 10, roomId);

        boolean updated = ExpenseDAO.updateExpense(expenseId, 180.0, 10, roomId);
        assert(updated);

        Expense expense = ExpenseDAO.getExpenseById(expenseId);
        assert(expense.getAmount() == 180.0);
    }

    @Test
    @Order(3)
    void testGetAllExpenses() {
        List<Expense> expenses = ExpenseDAO.getAllExpenses();
        assert(!expenses.isEmpty());
    }

    @Test
    @Order(4)
    void testDeleteExpense() {
        boolean userId = UserDAO.insertUser("Maria", "Contrasena");
        int roomId = RoomDAO.insertRoom("Camping");
        int expenseId = ExpenseDAO.insertExpense(250.0, 777, roomId);

        boolean deleted = ExpenseDAO.deleteExpense(expenseId);
        assert(deleted);
        assert(ExpenseDAO.getExpenseById(expenseId) == null);
    }
}
