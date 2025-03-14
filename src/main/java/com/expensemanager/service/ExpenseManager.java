package com.expensemanager.service;

import com.expensemanager.database.ExpenseDAO;
import com.expensemanager.database.RoomDAO;
import com.expensemanager.database.UserDAO;
import com.expensemanager.model.User;

import java.util.List;

public class ExpenseManager {

    private final UserDAO userDAO;
    private final ExpenseDAO expenseDAO;
    private final RoomDAO roomDAO;

    public ExpenseManager() {
        this.userDAO = new UserDAO();
        this.expenseDAO = new ExpenseDAO();
        this.roomDAO = new RoomDAO();
    }

    public boolean addUserToRoom(int userId, int roomId) {
        if (userDAO.getUserById(userId) == null || roomDAO.getRoomById(roomId) == null) {
            return false;
        }
        return roomDAO.addUserToRoom(userId, roomId);
    }

    public List<User> getUsersInRoom(int roomId) {
        return roomDAO.getUsersInRoom(roomId);
    }

    public int addExpense(double amount, int payerId, int roomId) {
        if (amount <= 0) {
            System.out.println("El monto del gasto tiene que se mayor a 0");
            return -1;
        }
        if (!roomDAO.isUserInRoom(roomId, payerId)) {
            System.out.println("El usuario no pertenece a esta sala");
            return -1;
        }
        return expenseDAO.insertExpense(amount, payerId, roomId);
    }
}
