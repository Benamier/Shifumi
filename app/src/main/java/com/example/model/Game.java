package com.example.model;;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Game implements Serializable {

    private final static int NOMBRE_MANCHES = 3;
    private int numberOfPlayers;
    private List<Player> players;
    private boolean isRunning;
    private boolean isFinished;
    private String serverAnswer;
    private String clientAnswer;
    private HashMap<Integer, String> map;
    private int manche = 0;


    public Game(List<Player> players) {
        this.players = players;
        this.numberOfPlayers = players.size();
        map = new HashMap<Integer, String>();
        for(Player player: players) {
            map.put(player.getId(), null);
        }
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public boolean hasPlayerPlayed(int playerId) {
       return map.get(playerId) != null;
    }

    public boolean isFinished() {
        return manche == NOMBRE_MANCHES;
    }

    public boolean play(int playerId, String answer) {
        if(hasPlayerPlayed(playerId))
            return false;
        map.put(playerId, answer);
        return true;
    }

    public String getAnswer(int playerId) {
        return map.get(playerId);
    }

    public void compare(String serverAnswer, String clientAnswer) {
        if(serverAnswer != null && clientAnswer != null) {
            if(serverAnswer.equals(clientAnswer)) {
                return;
            }
            if((serverAnswer.equals('P') && clientAnswer.equals('C')) ||
                    (serverAnswer.equals('F') && clientAnswer.equals('P')) ||
                    (serverAnswer.equals('C') && clientAnswer.equals('F'))) {
                players.get(0).incrementScore();
            }
            players.get(1).incrementScore();
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

}
