package com.expensemanager.ui;

import javafx.scene.Scene;
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
            if (!roomName.isEmpty()) {
                // Todo agregar aqui el codigo correspondiente a la BD
                System.out.println("Sala creada: " + roomName);
                this.close();  // Cerrar la ventana de creación
            } else {
                System.out.println("El nombre de la sala no puede estar vacío");
            }
        });

        // Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(roomNameField, createButton);

        // Escena
        Scene scene = new Scene(layout, 300, 200);
        this.setScene(scene);
    }
}
