package com.example.hello;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Stick{
    @FXML
    private Rectangle stick;
    private Timeline growthTimeline;
    private int stickLength;
    public Stick(){
        stick = new Rectangle();
        this.stick.setWidth(5);
        this.stick.setHeight(20);
        stick.setFill(Color.RED);
        stick.setX(130);
        stick.setY(220);
        stick.toFront();
    }

    public Rectangle getStick() {
        return stick;
    }
    public int getStickLength() {
        return stickLength;
    }
    public void setStickLength(int stickLength) {
        this.stickLength = stickLength;
    }
    public void increaseLength(){}

    public void  reset(){
        HelloController.stick = new Stick();
        HelloController.root.getChildren().add(HelloController.stick.getStick());
        //return new Stick();
    }

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
    public void lowerStick(Rectangle curpillar, Rectangle nextpillar, Rectangle newpillar) throws InterruptedException {
        StickHero hero = new StickHero();
        //System.out.println("starting to lower stick player one is null" + hero.getPlayer1() == null);
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
            HelloController.isFlag = false;
            hero.walk(stick.getHeight(),HelloController.curpillar, HelloController.nextpillar, HelloController.newpillar);
        } else if (stick.getHeight() + curpillar.getWidth() >= nextpillar.getLayoutX() && curpillar.getWidth() + stick.getHeight() <= nextpillar.getLayoutX() + nextpillar.getWidth()){
            System.out.println("stick is sufficiently long");
            hero.walk(HelloController.nextpillar.getLayoutX() + HelloController.nextpillar.getWidth() - HelloController.curpillar.getWidth() , HelloController.curpillar, HelloController.nextpillar, HelloController.newpillar);
        } else{
            System.out.println("stick too long");
            hero.walk(stick.getHeight(),HelloController.curpillar, HelloController.nextpillar, HelloController.newpillar);
        }

        //System.out.println("ending lowering stick player one is null" + hero.getPlayer1() == null);
    }


}