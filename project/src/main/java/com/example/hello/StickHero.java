package com.example.hello;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class StickHero extends Stick implements Character{
    public void setScore(Text score) {
        this.score = score;
    }

    public int getNscore() {
        return nscore;
    }

    public void setNscore(int nscore) {
        this.nscore = nscore;
    }

    public Text getScore() {
        return score;
    }

    @FXML
    private Text score;
    private  int nscore=0;
    @FXML
    private ImageView player1;
    private int cherry;
    private boolean isAlive;


    public ImageView getPlayer1() {
        return player1;
    }
    public void setPlayer1(ImageView player1) {
        this.player1 = player1;
    }
    public int getCherry() {
        return cherry;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public void setCherry(int cherry) {
        this.cherry = cherry;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

//
//    @Override
//    public void move() {}
//    @Override
//    public void extendStick() {}
//    @Override
//    public void collectCherry() {}
//    @Override
//    public void flip(){}

    public  void FlipHero() {
        if (player1 == null){
            for (Node node : HelloController.getRoot().getChildren()) {
                if (node instanceof ImageView && node.getId() != null && node.getId().equals("player1")) {
                    player1 = (ImageView) node;
                    System.out.println("Found player1 ImageView!");
                    break; // Assuming there is only one node with ID "player1"
                }
            }
        }

        System.out.println("here in fliphero -------------");
        int value;
        if (HelloController.isFlipped) {
            value = 180;
            HelloController.isFlipped = false;
            player1.setScaleY(1);
            player1.setLayoutY(player1.getLayoutY()-55);
        } else {
            HelloController.isFlipped = true;
            value =0;
            player1.setScaleY(-1);
            player1.setLayoutY(player1.getLayoutY()+55);
        }

        System.out.println("flipped player one is null" + (player1 == null));
    }
    private void fall() {
        Timeline fallAnimation = new Timeline();
        fallAnimation.getKeyFrames().add(
                new KeyFrame(Duration.millis(16), event -> {
                    player1.setRotate(player1.getRotate() + 20);
                    player1.setY(player1.getY() + 5);
                    if (player1.getY() >= HelloController.getBackground().getFitHeight()) {
                        fallAnimation.stop();
                        try {
                            HelloController.SwitchToGameOverScreen();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
        fallAnimation.setCycleCount(Timeline.INDEFINITE);
        fallAnimation.play();

    }
    public void walk(double distance, Rectangle curpillar, Rectangle nextpillar, Rectangle newpillar) {
        System.out.println("starting walk player one is null" + (player1 == null));
        HelloController.walking = true;
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
                if (HelloController.ischerry && player1.getScaleY() == -1 && player1.getX()>= HelloController.cherry.getX() ){
                    HelloController.cherry.setOpacity(0);
                    HelloController.cherrycount++;
                    //Cherry.setCherrycount(Cherry.getCherrycount()+1);
                    nscore++;
                    HelloController.ischerry = false;
                    HelloController.cherryscore.setText(Integer.toString(HelloController.cherrycount));
                    //cherryscore.setText(Integer.toString(cherrycount));
                    score.setText(Integer.toString(nscore));
                }
            } else {
                timeline.stop();
                if (!HelloController.isFlag) {    //stick is short -> fall
                    System.out.println("stick is short i will fall now");
                    fall();
                }
//                System.out.println("distance" + distance);
//                System.out.println("curr width" + curpillar.getWidth() );
//                System.out.println(nextpillar.getLayoutX() + nextpillar.getWidth());
                else if (player1.getScaleY() ==1 && distance + curpillar.getWidth() > nextpillar.getLayoutX() + nextpillar.getWidth()) {
                    System.out.println("i should fall---------");
                    fall();
                }
                else {
                    System.out.println("??????");
                    setNscore(getNscore()+1);
                    score.setText(Integer.toString(nscore));
                    Pillar1.shiftPillar(count, HelloController.curpillar,HelloController.nextpillar,HelloController.newpillar);
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
}

