package com.example.hello;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    public ImageView background;
    public Rectangle nextpillar;
    private Stage stage;
    private Scene scene;
    private AnchorPane root ;

    @FXML
    private Rectangle stick;
    @FXML
    private ImageView player1;
    private Image player2;

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
    public void lowerStick() throws InterruptedException {
        if (growthTimeline != null && growthTimeline.getStatus().equals(Timeline.Status.RUNNING)) {
            growthTimeline.stop();

            // Create a new timeline for lowering the stick
                // Check if the rectangle is not null
                if (stick != null) {
                    // Set the pivot point for rotation to be the bottom-center of the rectangle
                    System.out.println(stick.getHeight());
                    System.out.println(stick.getWidth());
                    stick.setTranslateY(stick.getHeight()/2.0);
                    stick.setTranslateX(stick.getHeight()/2.0);
                }
            fallTimeline = new Timeline(
                    new KeyFrame(Duration.millis(16), event -> stick.setRotate(90))
            );
            fallTimeline.setCycleCount(1);
            fallTimeline.play();
        }
        //wait();
        walk(stick.getHeight());
    }

    public void walk(double distance) {
        Timeline timeline = new Timeline();
        double speed = 3.0; // Adjust the speed as needed
        double[] distance_walked = {0.0};

        // Calculate the duration based on distance and speed
        double durationMillis = distance / speed;
        flag = true;

        // Create a key frame for the animation
        KeyFrame keyFrame = new KeyFrame(Duration.millis(16), (ActionEvent event) -> {
            if (distance_walked[0] < distance) {
                player1.setX(player1.getX() + speed);
                distance_walked[0] += speed;
            } else {
                // If the walking distance is covered, initiate fall
                fall();
                timeline.stop();
            }
        });
        timeline.getKeyFrames().add(keyFrame);

        EventHandler<ActionEvent> onFinished = (ActionEvent event) -> {
            // Check if the stick bounds intersect with the next pillar bounds
            if (!stick.getBoundsInParent().intersects(nextpillar.getBoundsInParent())) {
                // Stick does not intersect with the next pillar, initiate fall
                fall();
            }
        };
        // Set the event handler for when the timeline finishes
        timeline.setOnFinished(onFinished);

        // Set the cycle count to 1 since we want to play it only once
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Set the duration of the timeline
        timeline.setDelay(Duration.millis(durationMillis));

        // Play the timeline
        timeline.play();
    }


    private void fall() {
        Timeline fallAnimation = new Timeline();

        fallAnimation.getKeyFrames().add(
                new KeyFrame(Duration.millis(16), event -> {
                    player1.setRotate(player1.getRotate() + 50);
                    player1.setY(player1.getY() + 5);

                    // Check if the player has reached the bottom of the background
                    if (player1.getY() >= background.getFitHeight()) {
                        fallAnimation.stop();
                    }
                })
        );

        fallAnimation.setCycleCount(Timeline.INDEFINITE);
        fallAnimation.play();
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
