package com.example.admin.fingertwister;


public class Score {

    private int id;
    private String name;
    private int points;

    public Score(int idNew, String nameNew, int pointsNew) {
        this.setId(idNew);
        this.setName(nameNew);
        this.setPoints(pointsNew);
    }

    public Score(String nameNew, int pointsNew) {
        this.setName(nameNew);
        this.setPoints(pointsNew);
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

}
