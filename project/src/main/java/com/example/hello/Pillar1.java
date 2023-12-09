package com.example.hello;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Pillar1 extends Rectangle {
    private final int height= 166;
    private final int width;

    private static int minWidth = 4;
    private static int maxWidth = 36;
    private static int minDistance = 30;
    private static int maxDistance = 100;

    Rectangle pillar;

    public Pillar1(int layoutx) {
        this.pillar = new Rectangle();
        this.width = generateRandomWidth();
        pillar.setWidth(this.width);
        pillar.setHeight(this.height);
        pillar.setLayoutX(layoutx);
        pillar.setLayoutY(244);
        pillar.setFill(Color.BLACK);

    }

    private int generateRandomWidth() {
        Random random = new Random();
        return (random.nextInt(maxWidth + 1) + minWidth)*5;
    }
    private static int generateRandomDistance() {
        Random random = new Random();
        return random.nextInt(maxDistance + 1) + minDistance;
    }

    public static Pillar1 generatePillar(int layoutx){

        Pillar1 newpillar = new Pillar1(layoutx + generateRandomDistance());
        //int distance = generateRandomDistance();
        return newpillar;
    }

    public Rectangle getPillar() {
        return pillar;
    }

    public void setPillar(Rectangle pillar) {
        this.pillar = pillar;
    }

    public static void shiftPillar(AtomicInteger count, Rectangle curpillar, Rectangle nextpillar, Rectangle newpillar) {
        Rectangle stick = HelloController.stick.getStick();
        //System.out.println("shifting pillar player one is null" + (HelloController.hero.getPlayer1() == null));
        newpillar = Pillar1.generatePillar((int) (nextpillar.getLayoutX() + nextpillar.getWidth())).getPillar();
        //System.out.println("Root is null: " + (HelloController.root == null)); // prints true
        HelloController.root.getChildren().remove(stick);
        HelloController.root.getChildren().add(newpillar);

        newpillar.toFront();

        Timeline timeline = new Timeline();

        KeyFrame keyFrame = new KeyFrame(Duration.millis(5), (ActionEvent event) -> {
            count.addAndGet(1);
            if (curpillar.getWidth() < nextpillar.getLayoutX() + nextpillar.getWidth()) {
                nextpillar.setLayoutX(nextpillar.getLayoutX() - 2);
                HelloController.newpillar.setLayoutX(HelloController.newpillar.getLayoutX() - 2);
                HelloController.hero.getPlayer1().setLayoutX(HelloController.hero.getPlayer1().getLayoutX() - 2);
            } else{
                timeline.stop();
                HelloController.stick.reset();
//                stick = Stick.reset().getStick();
//                HelloController.root.getChildren().add(stick);
//                HelloController.nextpillar = HelloController.newpillar;
                HelloController.generatecherry(HelloController.curpillar,HelloController.nextpillar);
            }
        });
        timeline.getKeyFrames().add(keyFrame);

        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();
        System.out.println("ending shifting pillar player one is null" + (HelloController.hero.getPlayer1() == null));
    }
}
