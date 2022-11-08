package Matcher.Packet;

import Game.Game;

import java.io.Serializable;

public class Response implements Serializable {
    private int code;
    private int playerNumber = Game.FREE;
    private String matchId = null;
    // 1 -> Match found
    // 2 -> Matchmaking in-progress
    // 3 -> Error

    public Response(int code) {
        this.code = code;
    }

    public Response(int code, int playerNumber, String matchId) {
        this.code = code;
        this.playerNumber = playerNumber;
        this.matchId = matchId;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getCode() {
        return code;
    }

    public String getMatchId() {
        return matchId;
    }
}
