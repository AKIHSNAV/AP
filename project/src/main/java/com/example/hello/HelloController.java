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
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloController {
    public ImageView background;

    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane root ;
    @FXML
    private Rectangle nextpillar;
    @FXML
    private Rectangle curpillar;

    private Rectangle newpillar;

    @FXML
    private Rectangle stick;
    @FXML
    private ImageView player1;
    private Image player2;

    private Boolean walking = false;
    private boolean isFlag = true;
    private boolean isFlipped = false;
    public HelloController() throws IOException {
    }

    public void FlipHero() {
        if (player1 == null){
            for (Node node : root.getChildren()) {
                if (node instanceof ImageView && node.getId() != null && node.getId().equals("player1")) {
                    player1 = (ImageView) node;
                    System.out.println("Found player1 ImageView!");
                    break; // Assuming there is only one node with ID "player1"
                }
            }
        }

        System.out.println("here in fliphero -------------");
        int value;
        if (isFlipped) {
            value = 180;
            isFlipped = false;
            player1.setScaleY(1);
            player1.setLayoutY(player1.getLayoutY()-55);
        } else {
            isFlipped = true;
            value =0;
            player1.setScaleY(-1);
            player1.setLayoutY(player1.getLayoutY()+55);
        }

//        Timeline fallAnimation = new Timeline();
//        fallAnimation.getKeyFrames().add(
//                new KeyFrame(Duration.millis(16), event -> {
//                    player1.setRotate(player1.getRotate() + 10);
//                    if (player1.getRotate() == value) {
//                        fallAnimation.stop();
//                    }
//                })
//        );
//        fallAnimation.setCycleCount(Timeline.INDEFINITE);
//        fallAnimation.play();
        System.out.println("flipped player one is null" + (player1 == null));
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
        root.requestFocus();
        stage.show();
        scene.setOnKeyPressed(keyEvent -> {
            System.out.println("in here");
            if (keyEvent.getCode() == KeyCode.SPACE) {
                System.out.println("calling from  handlestms");
                FlipHero();
            }
        });
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
        System.out.println("starting to lower stick player one is null" + player1 == null);
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
        System.out.println("----I will walk now---");
        System.out.println(stick.getHeight());
        System.out.println(nextpillar.getLayoutX());
        System.out.println(curpillar.getWidth());
        System.out.println(curpillar.getLayoutX());
        if ( stick.getHeight() < (nextpillar.getLayoutX() - curpillar.getWidth())  ){
            System.out.println("stick is short");
            isFlag = false;
            walk(stick.getHeight());
        }
        walk(nextpillar.getLayoutX() - curpillar.getWidth() + nextpillar.getWidth() );
        System.out.println("ending lowering stick player one is null" + player1 == null);
    }

    private void fall() {
        System.out.println("starting fall player one is null" + (player1 == null));
        Timeline fallAnimation = new Timeline();
        fallAnimation.getKeyFrames().add(
                new KeyFrame(Duration.millis(16), event -> {
                    player1.setRotate(player1.getRotate() + 20);
                    player1.setY(player1.getY() + 5);
                    if (player1.getY() >= background.getFitHeight()) {
                        fallAnimation.stop();
                    }
                })
        );
        fallAnimation.setCycleCount(Timeline.INDEFINITE);
        fallAnimation.play();
        System.out.println("ending fall player one is null" + (player1 == null));
    }

    public void walk(double distance) {
        System.out.println("starting walk player one is null" + (player1 == null));
        walking = true;
        Timeline timeline = new Timeline();
        double speed = 3.0; // Adjust the speed as needed
        double[] distance_walked = {0.0};

        // Calculate the duration based on distance and speed
        double durationMillis = distance / speed;

        AtomicInteger count = new AtomicInteger();

        // Create a key frame for the animation
        KeyFrame keyFrame = new KeyFrame(Duration.millis(16), (ActionEvent event) -> {
            count.addAndGet(1);
            System.out.println(Arrays.toString(distance_walked));
            // call flip fn
            if (distance_walked[0] < distance) {
                player1.setX(player1.getX() + speed);
                distance_walked[0] += speed;
            } else {
                timeline.stop();
                if (!isFlag) {
                    fall();
                }
                if (isFlag) {
                    System.out.println("??????");
                    shiftPillar(count);
                }
            }
        });
        timeline.getKeyFrames().add(keyFrame);

        // Set the cycle count to 1 since we want to play it only once
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Set the duration of the timeline
        timeline.setDelay(Duration.millis(durationMillis));

        // Play the timeline
        timeline.play();
        System.out.println("ending walk, player one is null" + (player1 == null));
    }

    private void shiftPillar(AtomicInteger count) {

        System.out.println("shifting pillar player one is null" + (player1 == null));
//        System.out.println("in shift pillar-----------");
        newpillar = Pillar1.generatePillar((int) (nextpillar.getLayoutX() + nextpillar.getWidth())).getPillar();
        System.out.println("Root is null: " + (root == null)); // prints true
        root.getChildren().remove(stick);
        root.getChildren().add(newpillar);

        newpillar.toFront();

        Timeline timeline = new Timeline();

        KeyFrame keyFrame = new KeyFrame(Duration.millis(16), (ActionEvent event) -> {
            count.addAndGet(1);
            if (curpillar.getWidth() < nextpillar.getLayoutX() + nextpillar.getWidth()) {
                nextpillar.setLayoutX(nextpillar.getLayoutX() - 2);
                newpillar.setLayoutX(newpillar.getLayoutX() - 2);
                player1.setLayoutX(player1.getLayoutX() - 2);
            } else{
                timeline.stop();
//                System.out.println("resetting stick now-=---------------");
                stick = Stick.reset().getStick();
                root.getChildren().add(stick);
                nextpillar = newpillar;
            }
        });
        timeline.getKeyFrames().add(keyFrame);

        // Set the cycle count to 1 since we want to play it only once
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Set the duration of the timeline
        //timeline.setDelay(Duration.millis(500));

        // Set the event handler for when the timeline finishes


        // Play the timeline
        timeline.play();
        System.out.println("ending shifting pillar player one is null" + (player1 == null));
    }



}