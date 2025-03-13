package com.expensemanager;

import com.expensemanager.database.UserDAO;
import com.expensemanager.database.RoomDAO;
import com.expensemanager.database.ExpenseDAO;
import com.expensemanager.model.User;
import com.expensemanager.model.Room;
import com.expensemanager.model.Expense;

public class App {
    public static void main(String[] args) {
        System.out.println("Iniciando la aplicación...");

        // Crear las tablas
        UserDAO.createTable();
        RoomDAO.createTable();
        ExpenseDAO.createTable();

        // Insertar datos de prueba
        UserDAO.insertUser("Carlos");
        UserDAO.insertUser("Ana");
        RoomDAO.insertRoom("Viaje a la playa");
        ExpenseDAO.insertExpense(100.0, 1, 1);

        // Mostrar los datos iniciales
        System.out.println("\nUsuarios antes de actualizar:");
        for (User user : UserDAO.getAllUsers()) {
            System.out.println(user);
        }

        System.out.println("\nSalas antes de actualizar:");
        for (Room room : RoomDAO.getAllRooms()) {
            System.out.println(room);
        }

        System.out.println("\nGastos antes de actualizar:");
        for (Expense expense : ExpenseDAO.getAllExpenses()) {
            System.out.println(expense);
        }

        // Actualizar datos
        UserDAO.updateUser(1, "Carlos Rodríguez");
        RoomDAO.updateRoom(1, "Viaje a Cancún");
        ExpenseDAO.updateExpense(1, 150.0, 2, 1);

        // Eliminar datos
        UserDAO.deleteUser(2);
        RoomDAO.deleteRoom(1);
        ExpenseDAO.deleteExpense(1);

        // Mostrar los datos después de las modificaciones
        System.out.println("\nUsuarios después de actualizar y eliminar:");
        for (User user : UserDAO.getAllUsers()) {
            System.out.println(user);
        }

        System.out.println("\nSalas después de actualizar y eliminar:");
        for (Room room : RoomDAO.getAllRooms()) {
            System.out.println(room);
        }

        System.out.println("\nGastos después de actualizar y eliminar:");
        for (Expense expense : ExpenseDAO.getAllExpenses()) {
            System.out.println(expense);
        }
    }
}
