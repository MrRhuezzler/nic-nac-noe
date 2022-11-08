package Matcher.Packet;

import java.io.Serializable;

public class GameSteps implements Serializable {
    private int x;
    private int y;
    private int nextPlayer;
    private String matchId;

    public GameSteps(String matchId, int x, int y, int nextPlayer) {
        this.x = x;
        this.y = y;
        this.nextPlayer = nextPlayer;
        this.matchId = matchId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public String getMatchId() {
        return matchId;
    }
}
