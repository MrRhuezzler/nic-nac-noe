package Matcher.Packet;

import java.io.Serializable;

public class GameDone implements Serializable {
    private String matchId;
    private int winner;

    public GameDone(String matchId, int winner) {
        this.matchId = matchId;
        this.winner = winner;
    }

    public String getMatchId() {
        return matchId;
    }

    public int getWinner() {
        return winner;
    }
}
