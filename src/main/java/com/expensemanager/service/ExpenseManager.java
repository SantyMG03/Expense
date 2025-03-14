package com.expensemanager.service;

import com.expensemanager.database.ExpenseDAO;
import com.expensemanager.database.RoomDAO;
import com.expensemanager.database.UserDAO;

public class ExpenseManager {

    private final UserDAO userDAO;
    private final ExpenseDAO expenseDAO;
    private final RoomDAO roomDAO;

    public ExpenseManager() {
        this.userDAO = new UserDAO();
        this.expenseDAO = new ExpenseDAO();
        this.roomDAO = new RoomDAO();
    }

    
}
