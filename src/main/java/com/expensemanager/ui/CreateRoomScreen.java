package com.expensemanager.ui;

import com.expensemanager.database.RoomDAO;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateRoomScreen extends Stage {
    public CreateRoomScreen() {
        this.setTitle("Crear Nueva Sala");

        TextField roomNameField = new TextField();
        roomNameField.setPromptText("Nombre de la Sala");

        Button createButton = new Button("Crear Sala");
        createButton.setOnAction(e -> {
            String roomName = roomNameField.getText();
            if (roomNameField.getText().isEmpty()) {
                showAlert("Error", "El nombre de la sala no puede estar vacio");
            } else {
                int roomId = RoomDAO.insertRoom(roomName);
                if (roomId > 0) {
                    showAlert("Info", "Se ha registrado la sala");
                } else {
                    showAlert("Error", "No se pudo crear la sala");
                }
            }
        });

        // Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(roomNameField, createButton);

        // Escena
        Scene scene = new Scene(layout, 300, 200);
        this.setScene(scene);
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
