package com.example.hello;

import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Pillar1 extends Rectangle {
    private final int height= 166;
    private final int width;

    private static int minWidth = 15;
    private static int maxWidth = 100;
    private static int minDistance = 30;
    private static int maxDistance = 200;

    Rectangle pillar;

    public Pillar1(int layoutx) {
        this.width = generateRandomWidth();
        pillar.setWidth(this.width);
        pillar.setHeight(this.height);
        pillar.setLayoutX(layoutx);
        pillar.setLayoutY(244);

    }

    private int generateRandomWidth() {
        Random random = new Random();
        return random.nextInt(maxWidth + 1) + minWidth;
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
}
