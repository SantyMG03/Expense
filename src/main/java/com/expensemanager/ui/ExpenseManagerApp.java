package com.expensemanager.ui;

import com.expensemanager.database.ExpenseDAO;
import com.expensemanager.database.UserDAO;
import com.expensemanager.database.RoomDAO;
import com.expensemanager.model.Room;
import com.expensemanager.model.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExpenseManagerApp extends Application {

    private ExpenseDAO expenseDAO = new ExpenseDAO();
    private UserDAO userDAO = new UserDAO();
    private RoomDAO roomDAO = new RoomDAO();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Gestion de gastos");
        Button btnAddExpense = new Button("Agregar gasto");
        Button btnShowBalance = new Button("Mostrar balance");

        TextField amountField = new TextField();
        amountField.setPromptText("Ingrese el monto del gasto");

        ComboBox<User> userComboBox = new ComboBox<>();
        userComboBox.getItems().setAll(userDAO.getAllUsers()); // Poblar con usuarios

        ComboBox<Room> roomComboBox = new ComboBox<>();
        roomComboBox.getItems().setAll(roomDAO.getAllRooms()); // Poblar con salas

        btnAddExpense.setOnAction(e -> {
            double amount = Double.parseDouble(amountField.getText());
            User payer = userComboBox.getSelectionModel().getSelectedItem();
            Room room = roomComboBox.getSelectionModel().getSelectedItem();

            if (payer != null && room != null && amount > 0) {
                expenseDAO.insertExpense(amount, payer.getId(), room.getId());
                System.out.println("Gasto agregado: " + amount);
            } else {
                System.out.println("Por favor, ingrese datos válidos.");
            }
        });

        btnShowBalance.setOnAction(e -> {
            User selectedUser = userComboBox.getSelectionModel().getSelectedItem();
            Room selectedRoom = roomComboBox.getSelectionModel().getSelectedItem();

            if (selectedUser != null && selectedRoom != null) {
                double balance = expenseDAO.calculateBalance(selectedUser.getId(), selectedRoom.getId());
                System.out.println("Balance de " + selectedUser.getName() + ": " + balance);
            } else {
                System.out.println("Por favor, seleccione un usuario y una sala.");
            }
        });

        VBox vbox = new VBox(10, amountField, userComboBox, roomComboBox, btnAddExpense, btnShowBalance);
        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
