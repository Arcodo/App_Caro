package com.example.admin.fingertwister;


public class Score implements Comparable<Score> {

    private int id;
    private String name;
    private int points;

    public Score(int idNeu, String nameNeu, int pointsNeu) {
        this.setId(idNeu);
        this.setName(nameNeu);
        this.setPoints(pointsNeu);
    }

    public Score(String nameNeu, int pointsNeu) {
        this.setName(nameNeu);
        this.setPoints(pointsNeu);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String toString() {
        return "ID: " + getId() + ", Name: " + getName() + ", Punkte: " + getPoints();
    }

    @Override
    public int compareTo(Score score) {
        return score.getPoints() - this.points;
    }
}
