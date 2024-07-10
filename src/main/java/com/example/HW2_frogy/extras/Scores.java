package com.example.HW2_frogy.extras;

public class Scores {
    private String time;
    private int score;
    private double x;
    private double y;

    public Scores() {
        // Default constructor
    }

    public String getTime() {
        return time;
    }

    public Scores setTime(String time) {
        this.time = time;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Scores setScore(int score) {
        this.score = score;
        return this;
    }

    public double getX() {
        return x;
    }

    public Scores setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public Scores setY(double y) {
        this.y = y;
        return this;
    }

    @Override
    public String toString() {
        return "Time=" + time + "\n" + "X coord: " + x + "\n" + "Y coord: " + y + "\n" + "Score= " + score;
    }
}
