package com.expensemanager.database;

import com.expensemanager.model.Expense;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseDAOTest {

    @BeforeAll
    static void setupDatabase() {
        DataBaseManager.connect();
        UserDAO.createTable();
        RoomDAO.createTable();
        ExpenseDAO.createTable();
    }

    @Test
    @Order(1)
    void testInsertExpense() {
        int userId = UserDAO.insertUser("Luis");
        int roomId = RoomDAO.insertRoom("Fiesta de fin de año");

        int expenseId = ExpenseDAO.insertExpense(200.0, userId, roomId);
        assertThat(expenseId).isGreaterThan(0);

        Expense expense = ExpenseDAO.getExpenseById(expenseId);
        assertThat(expense).isNotNull();
        assertThat(expense.getAmount()).isEqualTo(200.0);
    }

    @Test
    @Order(2)
    void testUpdateExpense() {
        int userId = UserDAO.insertUser("Pedro");
        int roomId = RoomDAO.insertRoom("Cena con amigos");
        int expenseId = ExpenseDAO.insertExpense(150.0, userId, roomId);

        boolean updated = ExpenseDAO.updateExpense(expenseId, 180.0, userId, roomId);
        assertThat(updated).isTrue();

        Expense expense = ExpenseDAO.getExpenseById(expenseId);
        assertThat(expense.getAmount()).isEqualTo(180.0);
    }

    @Test
    @Order(3)
    void testGetAllExpenses() {
        List<Expense> expenses = ExpenseDAO.getAllExpenses();
        assertThat(expenses).isNotEmpty();
    }

    @Test
    @Order(4)
    void testDeleteExpense() {
        int userId = UserDAO.insertUser("María");
        int roomId = RoomDAO.insertRoom("Camping");
        int expenseId = ExpenseDAO.insertExpense(250.0, userId, roomId);

        boolean deleted = ExpenseDAO.deleteExpense(expenseId);
        assertThat(deleted).isTrue();
        assertThat(ExpenseDAO.getExpenseById(expenseId)).isNull();
    }
}
