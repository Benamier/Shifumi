package com.example.model;

import java.util.List;

public class Game {

    private int numberOfPlayer;
    private List<Player> players;
    private boolean isRunning;
    private boolean isGameOver;

    public Game(List<Player> players) {
        this.players = players;
        this.numberOfPlayer = players.size();
        isRunning = true;
        isGameOver = false;
    }
}
