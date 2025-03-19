package com.expensemanager.ui;

import com.expensemanager.model.Room;
import com.expensemanager.service.ExpenseManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateRoomScreen {
    private final Stage stage;
    private final ExpenseManager expenseManager;

    public CreateRoomScreen(Stage stage, ExpenseManager expenseManager) {
        this.stage = stage;
        this.expenseManager = expenseManager;
    }

    public void show() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Crear una nueva sala");
        TextField roomNameField = new TextField();
        roomNameField.setPromptText("Ingrese el nombre de la sala");

        Button createButton = new Button("Crear");
        Button backButton = new Button("Volver");

        // Acción al presionar "Crear"
        createButton.setOnAction(e -> {
            String roomName = roomNameField.getText().trim();
            if (roomName.isEmpty()) {
                showAlert("Error", "El nombre de la sala no puede estar vacío.");
                return;
            }

            Room newRoom = new Room(0, roomName);  // El ID se genera en la BD
            int success = expenseManager.createRoom(roomName);

            if (success != -1) {
                showAlert("Éxito", "Sala creada con éxito: " + roomName);
                new ExpenseManagerApp().start(stage);  // Regresar a la pantalla principal
            } else {
                showAlert("Error", "No se pudo crear la sala.");
            }
        });

        // Acción al presionar "Volver"
        backButton.setOnAction(e -> new ExpenseManagerApp().start(stage));

        layout.getChildren().addAll(titleLabel, roomNameField, createButton, backButton);
        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}