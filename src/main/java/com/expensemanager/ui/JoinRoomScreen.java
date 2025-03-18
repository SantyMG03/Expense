package com.expensemanager.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JoinRoomScreen extends Stage {
    public JoinRoomScreen() {
        this.setTitle("Join Room");

        String[] rooms = {"Sala 1", "Sala 2", "Sala 3"};  // Ejemplo de lista de salas

        VBox layout = new VBox(10);

        for (String room : rooms) {
            Button joinButton = new Button("Unirse a " + room);
            joinButton.setOnAction(e -> {
                // Todo agregar aqui la logica para unirse a una sala seleccionada
                System.out.println("Te has unido a " + room);
                this.close();
            });
            layout.getChildren().add(joinButton);
        }
        Scene scene = new Scene(layout, 300, 300);
        this.setScene(scene);
    }
}
