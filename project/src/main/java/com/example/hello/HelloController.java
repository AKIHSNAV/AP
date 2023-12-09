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
    @FXML
    public static ImageView cherry;
    public static int cherrycount = 0;
    public ImageView player1 = hero.getPlayer1();

    public static int getCherrycount() {
        return cherrycount;
    }

    public void setCherrycount(int cherrycount) {
        this.cherrycount = cherrycount;
    }


    public static boolean ischerry=false;
    public static boolean isFlipped = false;
    public static boolean isFlag = true;



    public boolean isFlipped() {
        return isFlipped;
    }
    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }
    public boolean isFlag() {
        return isFlag;
    }
    public void setFlag(boolean flag) {
        isFlag = flag;
    }
    public static boolean isIscherry() {
        return ischerry;
    }
    public static void setIscherry(boolean ischerry) {
        HelloController.ischerry = ischerry;
    }


    public static StickHero hero;
    public static Stick stick;
    @FXML
    public static Rectangle stick1 = stick.getStick();


    public static ImageView background;
    @FXML
    public static AnchorPane root ;




    public static AnchorPane getRoot() {
        return root;
    }
    public static void setRoot(AnchorPane root) {
        HelloController.root = root;
    }
    public static ImageView getBackground() {
        return background;
    }
    public void setBackground(ImageView background) {
        this.background = background;
    }


    @FXML
    private AnchorPane gameover;

    @FXML
    private AnchorPane confirmrevive;
    private Stage stage;
    private Scene scene;


    public Rectangle getNextpillar() {
        return nextpillar;
    }

    public void setNextpillar(Rectangle nextpillar) {
        this.nextpillar = nextpillar;
    }

    public Rectangle getCurpillar() {
        return curpillar;
    }

    public void setCurpillar(Rectangle curpillar) {
        this.curpillar = curpillar;
    }

    @FXML
    public static Rectangle nextpillar;
    @FXML
    public static Rectangle curpillar;
    @FXML
    public static Text cherryscore;

    public Rectangle getNewpillar() {
        return newpillar;
    }

    public void setNewpillar(Rectangle newpillar) {
        this.newpillar = newpillar;
    }

    public static Rectangle newpillar;
    @FXML
    private static Text displayscore;
    @FXML
    private AnchorPane pausescreen;
    private Image player2;
    public static Boolean walking = false;
    private static int reset= 0;
    private Timeline fallTimeline;


    public HelloController() throws IOException {
    }

    public void handleSwitchToMainScreen(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreen.fxml"));
            root = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Root is null: " + (root == null));
            return;
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
                    hero.setScore((Text) node);
                    break; // Assuming there is only one node with ID "player1"
                }
            }
            if (reset>0){
                cherryscore.setText(Integer.toString(getCherrycount() - reset));

                //hero.setScore((Text) );
                //System.out.println(score == null);

                hero.getScore().setText(Integer.toString(hero.getNscore()));
                reset = 0;
            } else{
                hero = new StickHero();
                cherryscore.setText("0");
                hero.getScore().setText("0");
                setCherrycount(0);
                //System.out.println(score == null);

                //reset = false;
            }

        });
        scene.setOnKeyPressed(keyEvent -> {
            System.out.println("in here");
            if (keyEvent.getCode() == KeyCode.SPACE) {
                System.out.println("calling from  handlestms");
                hero.FlipHero();
            }
        });
    }

    public static void generatecherry(Rectangle curpillar, Rectangle nextpillar) {
        Random random = new Random();
        if ( random.nextInt(2) == 1 &&(nextpillar.getLayoutX() - curpillar.getWidth() > 50)){
            System.out.println("next pillar x=" + nextpillar.getLayoutX());
            System.out.println("curr pillar width=" +curpillar.getWidth());
            int tmp = (int) random.nextInt((int) (nextpillar.getLayoutX() - curpillar.getWidth() - 45));
            //System.out.println(tmp);
            int position = tmp + 8 + (int) curpillar.getWidth();
            //System.out.println(position);
            cherry.setLayoutX(position);
            cherry.setOpacity(1);
            HelloController.ischerry = true;
        } else {
            HelloController.ischerry = false;
        }
    }
    public static void SwitchToGameOverScreen() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(HelloController.class.getResource("gameover.fxml"));
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
            //displayscore.setText(Integer.toString(hero.getNscore()));
            System.out.println("display score commented");
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

    public void revive() throws InterruptedException{
        System.out.println("in revive"+getCherrycount());
        if (getCherrycount()>= 1){
            confirmrevive.toFront();
            confirmrevive.setOpacity(1);
            reset = 1;
            System.out.println("idhar");
        } else {
            getNocherrymessage().toFront();
            getNocherrymessage().setOpacity(1);
        }
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

    @FXML
    private static AnchorPane nocherrymessage;




    public static AnchorPane getNocherrymessage() {
        return nocherrymessage;
    }

    public void setNocherrymessage(AnchorPane nocherrymessage) {
        this.nocherrymessage = nocherrymessage;
    }

    public ImageView getCherry() {
        return cherry;
    }

    public void setCherry(ImageView cherry) {
        this.cherry = cherry;
    }


    public void insufficientcherry(){
        nocherrymessage.setOpacity(0);
        nocherrymessage.toBack();
    }
    public void generatecherry(Pillar1 curpillar, Pillar1 nextpillar) {
        Random random = new Random();
        if ( random.nextInt(2) == 1 &&(nextpillar.getLayoutX() - curpillar.getWidth() > 50)){
            System.out.println("next pillar x=" + nextpillar.getLayoutX());
            System.out.println("curr pillar width=" +curpillar.getWidth());
            int tmp = (int) random.nextInt((int) (nextpillar.getLayoutX() - curpillar.getWidth() - 45));
            int position = tmp + 8 + (int) curpillar.getWidth();
            cherry.setLayoutX(position);
            cherry.setOpacity(1);
            HelloController.ischerry = true;
        } else {
            HelloController.ischerry = false;
        }
    }

    public void handlePlayer1KeyPress(KeyEvent keyEvent) {
        hero.FlipHero();
    }

    public void growStick(MouseEvent mouseEvent) {
        stick.growStick();
    }

    public void lowerStick(MouseEvent mouseEvent) throws InterruptedException {
        stick.lowerStick(curpillar, nextpillar, newpillar);
    }
}