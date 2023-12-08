package com.example.hello;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

class Pillar extends Rectangle {
    private final int height;
    private final int width;

    public Pillar(int height, int minWidth, int maxWidth) {
        this.height = height;
        this.width = generateRandomWidth(minWidth, maxWidth);
    }

    private int generateRandomWidth(int minWidth, int maxWidth) {
        Random random = new Random();
        return random.nextInt(maxWidth - minWidth + 1) + minWidth;
    }
    public static Pillar generatePillar(){
        Pillar newpillar = new Pillar(200, 200, 200);
        System.out.println("generating");
        //int distance = generateRandomDistance();
        return newpillar;
    }
    public void shiftPillar(){
        System.out.println("shifting");
    }


}

interface Character {
    void move();
    void extendStick();
    void collectCherry();
    void flip();
}
class Stick{
    private int stickLength;

    public Rectangle getStick() {
        return stick;
    }

    private Rectangle stick;
    public Stick(){
        stick = new Rectangle();
        this.stick.setWidth(5);
        this.stick.setHeight(20);
        stick.setFill(Color.RED);
        stick.setX(130);
        stick.setY(220);
        stick.toFront();
    }

    public Stick(int stickLength) {
        this.stickLength = stickLength;
    }

    public int getStickLength() {
        return stickLength;
    }
    public void setStickLength(int stickLength) {
        this.stickLength = stickLength;
    }
    public void increaseLength(){}

    public static Stick reset(){
        return new Stick();
    }


}
class StickHero extends Stick implements Character{
    private int cherry;
    private boolean isAlive;
    private int score;
    public StickHero(){
        this.cherry = 0;
        this.isAlive = true;
        this.score = 0;
    }
    public int getCherry() {
        return cherry;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getScore() {
        return score;
    }

    public void setCherry(int cherry) {
        this.cherry = cherry;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void move() {}
    @Override
    public void extendStick() {}
    @Override
    public void collectCherry() {}
    @Override
    public void flip(){}
}

class Game {
    private StickHero gameCharacter;

    public Game(StickHero gameCharacter) {
        this.gameCharacter = gameCharacter;
    }

    public void displayGame() {

    }
    public void gameStats() {

    }
    public void pauseGame() {

    }
    public void saveGame() {

    }
    public void continueGame() {

    }

    public void endGame() {
    }

    public void returnToHome() {
    }
    public void revive(){}
}

public class main {
    public static void main(String[] args) {
        StickHero stickHero = new StickHero();
        Game game = new Game(stickHero);
        Pillar pillar = new Pillar(200, 200,200);
        game.displayGame();

    }
}


