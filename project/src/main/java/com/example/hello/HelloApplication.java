package com.example.hello;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("main game screen.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Stick Hero");
        stage.setScene(scene);
        stage.show();
    }
//    public void switchToAnotherScene(Stage gamescreen) throws IOException {
//        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("main screen.fxml"));
//        Scene anotherScene = new Scene(loader.load(), 600, 400);
//        gamescreen.setScene(anotherScene);
//        gamescreen.show();
//    }

    public static void main(String[] args) {
        launch();
    }
}
