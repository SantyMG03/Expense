package com.expensemanager.service;

import com.expensemanager.database.ExpenseDAO;
import com.expensemanager.database.RoomDAO;
import com.expensemanager.database.UserDAO;
import com.expensemanager.model.Expense;
import com.expensemanager.model.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseManager {

    private final UserDAO userDAO;
    private final ExpenseDAO expenseDAO;
    private final RoomDAO roomDAO;

    public ExpenseManager() {
        this.userDAO = new UserDAO();
        this.expenseDAO = new ExpenseDAO();
        this.roomDAO = new RoomDAO();
    }

    /**
     * Metodo para agregar un usuario a una sala, se verificara si es usuario y esa sala existen
     *
     * @param userId identificador de usuario obtenido de la base de datos
     * @param roomId identificador de sala obtenido de la base de datos
     * @return True si se inserto correctamente, false en otro caso.
     */
    public boolean addUserToRoom(int userId, int roomId) {
        if (userDAO.getUserById(userId) == null || roomDAO.getRoomById(roomId) == null) {
            return false;
        }
        return roomDAO.addUserToRoom(userId, roomId);
    }

    /**
     * Metodo para obtener los usuarios pertenecientes a una sala
     * @param roomId identificador de la sala de la que obtener usuarios
     * @return Devuelve una lista con los usuarios de la sala, lista vacia si la sala esta
     *      vacia o no existe (mirar getUsersInRoom)
     */
    public List<User> getUsersInRoom(int roomId) {
        return roomDAO.getUsersInRoom(roomId);
    }

    /**
     * Metodo que agrega un gasto a una sala especifica
     * @param amount es la cantidad pagada
     * @param payerId el identificador de pagador
     * @param roomId el identificador de la sala en la que se realiza el pago
     * @return la cantidad pagada o -1 en caso de error
     */
    public int addExpense(double amount, int payerId, int roomId) {
        if (amount <= 0) {
            System.out.println("El monto del gasto tiene que se mayor a 0");
            return -1;
        }
        if (!roomDAO.isUserInRoom(roomId, payerId)) {
            System.out.println("El usuario no pertenece a esta sala");
            return -1;
        }
        List<User> users = roomDAO.getUsersInRoom(roomId);
        if (users.isEmpty()) {
            System.out.println("No hay usuarios en la sala");
            return -1;
        }
        double amountPerUser = amount / users.size();
        for (User user : users) {
            if (user.getId() == payerId) {
                expenseDAO.insertExpense(amountPerUser, payerId, roomId);
            }
        }
        return expenseDAO.insertExpense(amount, payerId, roomId);
    }

    /**
     * Metodo que divide los gastos entre los usuarios
     * @param expenseId identidicador del gasto (podemos obtener sala solo con esto)
     * @return un mapa donde cada usuario tiene asociado la cantidad que debe
     */
    public Map<Integer, Double> splitExpense(int expenseId) {
        Expense expense = expenseDAO.getExpenseById(expenseId);
        if (expense == null) {
            System.out.println("El gasto no existe");
            return null;
        }
        List<User> users = roomDAO.getUsersInRoom(expense.getRoom().getId());

        int numUsers = users.size();
        if (numUsers <= 0) {
            System.out.println("No hay usuarios en esta sala");
            return null;
        }
        double split = expense.getAmount() / numUsers;
        System.out.println("Cada usuario debe pagar: " + split);

        return users.stream().collect(Collectors.toMap(User::getId, user -> split));
    }

    /**
     * Metodo para consultar el balance de un usuario en una sala
     * @param userId identificador del usuario del que se consulta el balance
     * @param roomId identidicador de la sala en la que se quiere hacer la consulta
     * @return devuelve el balance del usuario o 0.0 si hubo algun error
     */
    public double getBalance(int userId, int roomId) {
        return expenseDAO.calculateBalance(userId, roomId);
    }

    public double getUserBalance(int userId, int roomId) {
        return expenseDAO.calculateBalance(userId, roomId);
    }
}
