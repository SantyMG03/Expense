package com.expensemanager.ui;

import com.expensemanager.database.ExpenseDAO;
import com.expensemanager.database.RoomDAO;
import com.expensemanager.database.UserDAO;
import com.expensemanager.service.ExpenseManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ExpenseManagerApp extends Application {

    private ExpenseManager expenseManager;
    private Stage stage;

    public ExpenseManagerApp(){
        this.expenseManager = new ExpenseManager();
    }

    public ExpenseManagerApp(Stage stage, ExpenseManager expenseManager) {
        this.stage = stage;
        this.expenseManager = expenseManager;
    }

    @Override
    public void start(Stage primaryStage) {
        this.expenseManager = new ExpenseManager(); // Inicializa el manejador de gastos
        primaryStage.setTitle("Expense Manager");

        // Botones de navegación
        Button createRoomButton = new Button("Crear Sala");
        Button joinRoomButton = new Button("Unirse a una Sala");

        // Acción para crear una sala
        createRoomButton.setOnAction(e -> {
            System.out.println("Navegando a CreateRoomScreen");
            new CreateRoomScreen(primaryStage, expenseManager).show();
        });

        // Acción para unirse a una sala
        joinRoomButton.setOnAction(e -> {
            System.out.println("Navegando a JoinRoomScreen");
            new JoinRoomScreen(primaryStage, expenseManager).show();
        });

        VBox layout = new VBox(10, createRoomButton, joinRoomButton);
        Scene scene = new Scene(layout, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void show(){
        VBox layout = new VBox(10);
        Button createRoomButton = new Button("Crear Sala");
        Button joinRoomButton = new Button("Unirse a una Sala");

        createRoomButton.setOnAction(e -> new CreateRoomScreen(stage, expenseManager));
        joinRoomButton.setOnAction(e -> new JoinRoomScreen(stage, expenseManager));

        layout.getChildren().addAll(createRoomButton, joinRoomButton);
        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
