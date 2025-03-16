package com.expensemanager;

import com.expensemanager.database.UserDAO;
import com.expensemanager.database.RoomDAO;
import com.expensemanager.database.ExpenseDAO;
import com.expensemanager.model.User;
import com.expensemanager.model.Room;
import com.expensemanager.model.Expense;
import com.expensemanager.service.ExpenseManager;

public class App {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();

        int expenseId = 6; // ID del gasto que deseas eliminar
        int userId = 19;   // ID del usuario (puedes usar el ID de cualquier usuario)
        int roomId = 7;    // ID de la sala a la que pertenece el gasto

        ExpenseManager manager = new ExpenseManager();
        manager.removeExpense(expenseId, userId, roomId);
    }
}
