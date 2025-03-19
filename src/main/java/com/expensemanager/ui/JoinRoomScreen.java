package com.expensemanager.ui;

import com.expensemanager.database.RoomDAO;
import com.expensemanager.model.Room;
import com.expensemanager.service.ExpenseManager;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class JoinRoomScreen extends Stage {
    public JoinRoomScreen() {
        this.setTitle("Join Room");
        VBox layout = new VBox(10);

        Label title = new Label("Unirse a una sala");
        TextField roomIdField = new TextField();
        roomIdField.setPromptText("Ingrese el ID de la sala");

        Button joinButton = new Button("Unirse");
        Button backButton = new Button("Volver");

        joinButton.setOnAction(e -> {
            String roomIdText = roomIdField.getText().trim();
            if (roomIdText.matches("\\d+")) {
                showAlert("Error", "El ID debe ser un numero valido");

            } else {
                int roomId = Integer.parseInt(roomIdText);
                if ()
            }
        });

        Scene scene = new Scene(layout, 300, 300);
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
