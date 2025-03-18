package com.expensemanager.ui;

import com.expensemanager.database.ExpenseDAO;
import com.expensemanager.database.RoomDAO;
import com.expensemanager.database.UserDAO;
import com.expensemanager.model.Expense;
import com.expensemanager.model.Room;
import com.expensemanager.model.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ExpenseManagerApp extends Application {

    private ExpenseDAO expenseDAO = new ExpenseDAO();
    private UserDAO userDAO = new UserDAO();
    private RoomDAO roomDAO = new RoomDAO();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestión de Gastos");

        // Crear los controles de UI
        TextField amountField = new TextField();
        amountField.setPromptText("Ingrese el monto del gasto");

        ComboBox<User> userComboBox = new ComboBox<>();
        userComboBox.getItems().setAll(userDAO.getAllUsers());

        ComboBox<Room> roomComboBox = new ComboBox<>();
        roomComboBox.getItems().setAll(roomDAO.getAllRooms());

        Button btnAddExpense = new Button("Agregar Gasto");
        Button btnShowBalance = new Button("Mostrar Balance");
        Button btnShowHistory = new Button("Ver Historial de Gastos");

        Label messageLabel = new Label();
        TextArea historyArea = new TextArea();
        historyArea.setEditable(false);
        historyArea.setPrefHeight(150);

        // Lógica para agregar un gasto con validación
        btnAddExpense.setOnAction(e -> {
            String amountText = amountField.getText();
            User payer = userComboBox.getSelectionModel().getSelectedItem();
            Room room = roomComboBox.getSelectionModel().getSelectedItem();

            if (amountText.isEmpty() || payer == null || room == null) {
                messageLabel.setText("Por favor, complete todos los campos.");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    messageLabel.setText("El monto debe ser mayor a 0.");
                    return;
                }
                expenseDAO.insertExpense(amount, payer.getId(), room.getId());
                messageLabel.setText("Gasto agregado correctamente.");
            } catch (NumberFormatException ex) {
                messageLabel.setText("Ingrese un número válido.");
            }
        });

        // Lógica para mostrar el balance de un usuario
        btnShowBalance.setOnAction(e -> {
            User selectedUser = userComboBox.getSelectionModel().getSelectedItem();
            Room selectedRoom = roomComboBox.getSelectionModel().getSelectedItem();

            if (selectedUser == null || selectedRoom == null) {
                messageLabel.setText("Seleccione un usuario y una sala.");
                return;
            }

            double balance = expenseDAO.calculateBalance(selectedUser.getId(), selectedRoom.getId());
            messageLabel.setText("Balance de " + selectedUser.getName() + ": " + balance);
        });

        // Lógica para mostrar el historial de gastos de la sala
        btnShowHistory.setOnAction(e -> {
            Room selectedRoom = roomComboBox.getSelectionModel().getSelectedItem();
            if (selectedRoom == null) {
                messageLabel.setText("Seleccione una sala.");
                return;
            }

            List<Expense> expenses = expenseDAO.getExpensesByRoom(selectedRoom.getId());
            if (expenses.isEmpty()) {
                historyArea.setText("No hay gastos registrados en esta sala.");
            } else {
                StringBuilder historyText = new StringBuilder("Historial de Gastos:\n");
                for (Expense exp : expenses) {
                    historyText.append(exp.getPayer().getName())
                            .append(" pagó ")
                            .append(exp.getAmount())
                            .append(" en la sala ")
                            .append(selectedRoom.getName())
                            .append("\n");
                }
                historyArea.setText(historyText.toString());
            }
        });

        // Layout
        VBox vbox = new VBox(10, amountField, userComboBox, roomComboBox, btnAddExpense, btnShowBalance, btnShowHistory, messageLabel, historyArea);
        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
