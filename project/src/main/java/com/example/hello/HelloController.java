package com.example.hello;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button switchToMainScreenButton;

    // Reference to the stage of the main game screen
    private Stage mainGameStage;
    public void setMainGameStage(Stage mainGameStage) {
        this.mainGameStage = mainGameStage;
    }

    // Method to handle the button click and switch to the main screen
    @FXML
    private void handleSwitchToMainScreen(MouseEvent event) throws IOException {
        System.out.println("working");
        HelloApplication helloApplication = new HelloApplication();
        helloApplication.switchToAnotherScene(mainGameStage);
    }
//    @FXML
//    public void initialize() {
//        // Set the background color of the button
//        switchToMainScreenButton.setStyle("-fx-background-color: #ffffff;");
//    }
}



//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class HelloController {
//    private Stage stage;
//    private Scene scene;
//    private Parent root;
//    public void switchToGameScreen(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(HelloApplication.class.getResource("main screen.fxml"));
//        stage = (Stage)(Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//
//    @FXML
//    private Label welcomeText;
//
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
//}