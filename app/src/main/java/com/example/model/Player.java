package com.example.model;

import java.io.Serializable;

public class Player implements Serializable {

    private int id;
    private String name;
    private int score;
    private boolean isActive;


    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.score = 0;
        this.isActive = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public boolean isActive() {
        return isActive;
    }

    public void incrementScore() {
        score++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id;
    }

}
