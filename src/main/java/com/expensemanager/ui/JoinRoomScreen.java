package com.expensemanager.ui;


import com.expensemanager.service.ExpenseManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JoinRoomScreen {
    private final Stage stage;
    private final ExpenseManager expenseManager;

    public JoinRoomScreen(Stage stage, ExpenseManager expenseManager) {
        this.stage = stage;
        this.expenseManager = expenseManager;
    }

    public void show() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Unirse a una sala");
        TextField roomIdField = new TextField();
        roomIdField.setPromptText("Ingrese el ID de la sala");

        Button joinButton = new Button("Unirse");
        Button backButton = new Button("Volver");

        joinButton.setOnAction(e -> handleJoinRoom(roomIdField.getText().trim()));
        //backButton.setOnAction(e -> returnToMainMenu());

        layout.getChildren().addAll(titleLabel, roomIdField, joinButton, backButton);

        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void handleJoinRoom(String roomIdText) {
        if (!roomIdText.matches("\\d+")) {
            showAlert("Error", "El ID de la sala debe ser un número válido.");
            return;
        }

        int roomId = Integer.parseInt(roomIdText);
        if (!expenseManager.getRooms().contains(roomId)) {
            showAlert("Error", "No existe una sala con ese ID.");
            return;
        }

        showAlert("Éxito", "Te has unido a la sala con ID: " + roomId);
        // Aquí puedes agregar la lógica de navegación o actualización de UI
    }
/*
    private void returnToMainMenu() {
        App mainApp = new App(stage, expenseManager);
        App.show();
    }
 */

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
