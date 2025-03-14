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

        // Crear una sala y obtener su ID
        RoomDAO.createTable();
        UserDAO.createTable();
        ExpenseDAO.createTable();

        int roomId = RoomDAO.insertRoom("Viaje a la playa");
        System.out.println("Sala creada con ID: " + roomId);

        // Crear usuarios y obtener sus IDs
        int user1 = UserDAO.insertUser("Alice");
        int user2 = UserDAO.insertUser("Bob");
        int user3 = UserDAO.insertUser("Charlie");
        System.out.println("Usuarios creados con IDs: " + user1 + ", " + user2 + ", " + user3);

        // Agregar usuarios a la sala
        RoomDAO.addUserToRoom(user1, roomId);
        RoomDAO.addUserToRoom(user2, roomId);
        RoomDAO.addUserToRoom(user3, roomId);
        System.out.println("Usuarios añadidos a la sala.");

        // Agregar un gasto de $90 pagado por Alice
        int expenseId = expenseManager.addExpense(90, user1, roomId);
        System.out.println("Gasto registrado con ID: " + expenseId);

        // Consultar balance de cada usuario
        System.out.println("Balance de Alice: " + expenseManager.getUserBalance(user1, roomId));
        System.out.println("Balance de Bob: " + expenseManager.getUserBalance(user2, roomId));
        System.out.println("Balance de Charlie: " + expenseManager.getUserBalance(user3, roomId));
    }
}
