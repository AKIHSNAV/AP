package com.example.hello;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloController {
    public ImageView background;
    @FXML
    private AnchorPane gameover;
    @FXML
    private AnchorPane nocherrymessage;
    @FXML
    private AnchorPane confirmrevive;
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane root ;
    @FXML
    private Rectangle nextpillar;
    @FXML
    private Rectangle curpillar;
    @FXML
    private Text cherryscore;
    private Rectangle newpillar;

    @FXML
    private Text displayscore;

    @FXML
    private Rectangle stick;
    @FXML
    private ImageView player1;
    @FXML
    private AnchorPane pausescreen;
    private Image player2;

    private Boolean walking = false;
    private boolean isFlag = true;
    private boolean isFlipped = false;
    @FXML
    private ImageView cherry;
    private boolean ischerry=false;
    private static int reset= 0;
    private static int nscore=0;

    public Text getScore() {
        return score;
    }

    public void setScore(Text score) {
        this.score = score;
    }

    @FXML
    private Text score;
    public int getCherrycount() {
        return cherrycount;
    }

    public void setCherrycount(int cherrycount) {
        HelloController.cherrycount = cherrycount;
    }
    @FXML
    private static int cherrycount = 0;
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

        System.out.println("flipped player one is null" + (player1 == null));
    }

    public void handleSwitchToMainScreen(MouseEvent event) throws IOException {
        getprogress();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreen.fxml"));
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

        Platform.runLater(() -> {
            for (Node node : root.getChildren()) {
                if (node instanceof Text && node.getId() != null && node.getId().equals("cherryscore")) {
                    cherryscore = (Text) node;
                    break; // Assuming there is only one node with ID "player1"
                }
            }
            for (Node node : root.getChildren()) {
                if (node instanceof Text && node.getId() != null && node.getId().equals("score")) {
                    score = (Text) node;
                    break; // Assuming there is only one node with ID "player1"
                }
            }
            if (reset>0){
                cherryscore.setText(Integer.toString(cherrycount - reset));

                //System.out.println(score == null);
                score.setText(Integer.toString(nscore));
                reset = 0;
            } else{
                cherryscore.setText("0");
                nscore= 0;
                cherrycount =0;
                //System.out.println(score == null);
                score.setText("0");
                //reset = false;
            }

        });
        scene.setOnKeyPressed(keyEvent -> {
            System.out.println("in here");
            if (keyEvent.getCode() == KeyCode.SPACE) {
                System.out.println("calling from  handlestms");
                FlipHero();
            }
        });
    }

    public void SwitchToGameOverScreen() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameover.fxml"));
            AnchorPane newScreenRoot = loader.load();
            Stage currentStage = (Stage)(root.getScene().getWindow());
            Scene newScreenScene = new Scene(newScreenRoot);
            currentStage.setScene(newScreenScene);
            currentStage.show();
            // Inject displayscore
            HelloController controller = loader.getController();
            controller.displayscore = (Text) newScreenRoot.lookup("#displayscore");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Root is null: " + (root == null));
            // Handle the exception appropriately
            return;  // Exit the method to prevent further execution
        }
        Platform.runLater(() -> {
            // Now you can access displayscore safely
            displayscore.setText(Integer.toString(nscore));
        });
    }

    public void SwitchToHomeScreen(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homescreen.fxml"));
//            AnchorPane newScreenRoot = loader.load();
            this.root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            Stage currentStage = (Stage)(root.getScene().getWindow());
            Scene newScreenScene = new Scene(root);
//            currentStage.setScene(newScreenScene);
            stage.setScene(newScreenScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load homescreen.fxml");
            // Handle the exception appropriately
        }
    }


    private Timeline growthTimeline;
    private final int scaleFactor = 4;
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
        } else if (stick.getHeight() + curpillar.getWidth() >= nextpillar.getLayoutX() && curpillar.getWidth() + stick.getHeight() <= nextpillar.getLayoutX() + nextpillar.getWidth()){
            System.out.println("stick is sufficiently long");
            walk(nextpillar.getLayoutX() + nextpillar.getWidth() - curpillar.getWidth() );
        } else{
            System.out.println("stick too long");
            walk(stick.getHeight());
        }

        System.out.println("ending lowering stick player one is null" + player1 == null);
    }

    private void fall() {
        Timeline fallAnimation = new Timeline();
        fallAnimation.getKeyFrames().add(
                new KeyFrame(Duration.millis(16), event -> {
                    player1.setRotate(player1.getRotate() + 20);
                    player1.setY(player1.getY() + 5);
                    if (player1.getY() >= background.getFitHeight()) {
                        fallAnimation.stop();
                        try {
                            SwitchToGameOverScreen();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
        fallAnimation.setCycleCount(Timeline.INDEFINITE);
        fallAnimation.play();

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
            //System.out.println(Arrays.toString(distance_walked));
            // call flip fn
            if (distance_walked[0] < distance) {
                player1.setX(player1.getX() + speed);
                distance_walked[0] += speed;
                if (ischerry && player1.getScaleY() == -1 && player1.getX()>= cherry.getX() ){
                    cherry.setOpacity(0);
                    cherrycount+=1;
                    setCherrycount(cherrycount);
                    nscore++;
                    ischerry = false;
                    cherryscore.setText(Integer.toString(cherrycount));
                    score.setText(Integer.toString(nscore));
                }
            } else {
                timeline.stop();
                if (!isFlag) {    //stick is short -> fall
                    System.out.println("stick is short i will fall now");
                    fall();
                }
//                System.out.println("distance" + distance);
//                System.out.println("curr width" + curpillar.getWidth() );
//                System.out.println(nextpillar.getLayoutX() + nextpillar.getWidth());
                else if (player1.getScaleY() == 1 && distance + curpillar.getWidth() > nextpillar.getLayoutX() + nextpillar.getWidth()) {
                    System.out.println("i should fall---------");
                    fall();
                } else if (player1.getScaleY() == -1 && distance_walked[0] + curpillar.getWidth() >= nextpillar.getLayoutX() - nextpillar.getWidth() ){
                    timeline.stop();
                    fall();
                } else {
                    System.out.println("??????");
                    nscore++;
                    score.setText(Integer.toString(nscore));
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
        newpillar = Pillar1.generatePillar((int) (nextpillar.getLayoutX() + nextpillar.getWidth())).getPillar();
        System.out.println("Root is null: " + (root == null)); // prints true
        root.getChildren().remove(stick);
        root.getChildren().add(newpillar);

        newpillar.toFront();

        Timeline timeline = new Timeline();

        KeyFrame keyFrame = new KeyFrame(Duration.millis(5), (ActionEvent event) -> {
            count.addAndGet(1);
            if (curpillar.getWidth() < nextpillar.getLayoutX() + nextpillar.getWidth()) {
                nextpillar.setLayoutX(nextpillar.getLayoutX() - 2);
                newpillar.setLayoutX(newpillar.getLayoutX() - 2);
                player1.setLayoutX(player1.getLayoutX() - 2);
            } else{
                timeline.stop();
                stick = Stick.reset().getStick();
                root.getChildren().add(stick);
                nextpillar = newpillar;
                generatecherry();
            }
        });
        timeline.getKeyFrames().add(keyFrame);

        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();
        System.out.println("ending shifting pillar player one is null" + (player1 == null));
    }
    private void generatecherry() {
        Random random = new Random();
        if ( random.nextInt(2) == 1 &&(nextpillar.getLayoutX() - curpillar.getWidth() > 50)){
            System.out.println("next pillar x=" + nextpillar.getLayoutX());
            System.out.println("curr pillar width=" +curpillar.getWidth());
            int tmp = random.nextInt((int) (nextpillar.getLayoutX() - curpillar.getWidth() - 45));
            //System.out.println(tmp);
            int position = tmp + 8 + (int) curpillar.getWidth();
            //System.out.println(position);
            cherry.setLayoutX(position);
            cherry.setOpacity(1);
            ischerry = true;
        } else {
            ischerry = false;
        }
    }

    public void revive() throws InterruptedException{
        System.out.println("in revive"+ getCherrycount());
        if (getCherrycount()>= 1){
            confirmrevive.toFront();
            confirmrevive.setOpacity(1);
            reset = 1;
            System.out.println("idhar");
        } else {
            nocherrymessage.toFront();
            nocherrymessage.setOpacity(1);
        }
    }
    public void insufficientcherry(){
        nocherrymessage.setOpacity(0);
        nocherrymessage.toBack();
    }
    public void notsure(){
        confirmrevive.setOpacity(0);
        confirmrevive.toBack();
        reset = 0;
    }
    public void pause(){
        pausescreen.toFront();
        pausescreen.setOpacity(1);
    }
    public void resume(){
        pausescreen.setOpacity(0);
        pausescreen.toBack();
    }
    public void save2(MouseEvent event){
        System.out.println("saving progress!");
        Saveprogress.saveProgress(cherrycount, nscore);

    }
    public void getprogress(){
        Saveprogress saveprogress = new Saveprogress(0,0);
        if (saveprogress.checkforprogress()){
            saveprogress = Saveprogress.loadProgress();
            nscore = saveprogress.getScore();
            cherrycount = saveprogress.getCherries();
            cherryscore.setText(Integer.toString(cherrycount));
            score.setText(Integer.toString(nscore));
        } else {
            System.out.println("No progress saved.");
        }
    }
}