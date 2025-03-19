package com.expensemanager.ui;

import com.expensemanager.database.ExpenseDAO;
import com.expensemanager.database.RoomDAO;
import com.expensemanager.database.UserDAO;
import com.expensemanager.model.Expense;
import com.expensemanager.model.Room;
import com.expensemanager.model.User;
import com.expensemanager.service.ExpenseManager;
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
    private ExpenseManager expenseManager = new ExpenseManager();

    @Override
    public void start(Stage primaryStage) {
       Button createRoomButton = new Button("Create Room");
       Button joinRoomButton = new Button("Join Room");

       createRoomButton.setOnAction(e -> {
           CreateRoomScreen createRoomScreen = new CreateRoomScreen();
           createRoomScreen.show();
       });

       joinRoomButton.setOnAction(e -> {
           JoinRoomScreen joinRoomScreen = new JoinRoomScreen(primaryStage, expenseManager);
           joinRoomScreen.show();
       });

       VBox layout = new VBox(20);
       layout.getChildren().addAll(createRoomButton, joinRoomButton);

       Scene scene = new Scene(layout, 300, 200);
       primaryStage.setTitle("Pantalla inicio");
       primaryStage.setScene(scene);
       primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
