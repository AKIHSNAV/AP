package com.example.hello;

import java.io.*;

public class Saveprogress implements Serializable {
    public int getCherries() {
        return cherries;
    }

    public void setCherries(int cherries) {
        this.cherries = cherries;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int cherries;
    private int score;
    Saveprogress(int cherries, int score){
        this.cherries = cherries;
        this.score = score;

    }

    public static void saveProgress(int cherries, int score) {
        System.out.println("in here");
        Saveprogress saveprogress = new Saveprogress(cherries, score);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("progress.txt"))) {
            oos.writeObject(saveprogress);
            System.out.println("Progress saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Saveprogress loadProgress() {
        Saveprogress saveprogress = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("progress.txt"))) {
            saveprogress = (Saveprogress) ois.readObject();
            System.out.println("Progress loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return saveprogress;
    }

    public boolean checkforprogress() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("progress.txt"))) {
            Saveprogress saveprogress = (Saveprogress) ois.readObject();
            return saveprogress != null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
