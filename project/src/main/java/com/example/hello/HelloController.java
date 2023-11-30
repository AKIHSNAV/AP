package com.example.hello;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private AnchorPane root ;

    @FXML
    private Rectangle stick;

    public HelloController() throws IOException {
    }

    public void handleSwitchToMainScreen(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main screen.fxml"));
            this.root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Root is null: " + (root == null));
            // Handle the exception appropriately
            return;  // Exit the method to prevent further execution
        }

        // Now, root is properly initialized
        System.out.println("Root is null: " + (root == null));  // prints false

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private Timeline growthTimeline;
    private int scaleFactor = 4;
    public void growStick() {
        double scaleFactor = 4; // You can adjust the scale factor as needed

        // Check if the timeline is not null and already running
        if (growthTimeline == null || !growthTimeline.getStatus().equals(Timeline.Status.RUNNING)) {
            growthTimeline = new Timeline(new KeyFrame(Duration.millis(16), event -> {
                // Check if the rectangle is not null
                if (stick != null) {
                    stick.setFill(Color.BROWN);

                    // Adjust the y-coordinate to move the rectangle upwards
                    stick.setY(stick.getY() - scaleFactor);

                    // Increase the height of the rectangle
                    stick.setHeight(stick.getHeight() + scaleFactor);
                }
            }));
            growthTimeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until stopped
            growthTimeline.play();
        }
    }

    private Timeline fallTimeline;
    public void lowerStick() {
        if (growthTimeline != null && growthTimeline.getStatus().equals(Timeline.Status.RUNNING)) {
            growthTimeline.stop();

            // Create a new timeline for lowering the stick
            fallTimeline = new Timeline(new KeyFrame(Duration.millis(16), event -> {
                // Check if the rectangle is not null
                if (stick != null) {
                    // Set the pivot point for rotation to be the bottom-center of the rectangle
//                    stick.setTranslateX(stick.getWidth());

                    System.out.println(stick.getHeight());
                    System.out.println(stick.getWidth());
                    stick.setTranslateY(stick.getHeight()/2.0);
                    stick.setTranslateX(stick.getHeight()/2.0);

                    // Rotate the stick to 90 degrees around the bottom-center
                    stick.setRotate(90);

                    // You may also want to change the color or other properties as needed
                }
            }));
            fallTimeline.setCycleCount(1); // Run only once
            fallTimeline.play();
        }
    }

    public void walk(){

    }

    double startTime, endTime, holdTime;
    boolean flag = false;

    public final void mousePressed(final MouseEvent e) {
        startTime = System.nanoTime();
        flag = true;
    }
    public final void mouseReleased(final MouseEvent e) {
        if(flag) {
            endTime = System.nanoTime();
            flag = false;
        }
        holdTime = (endTime - startTime) / Math.pow(10,9);
    }
}
