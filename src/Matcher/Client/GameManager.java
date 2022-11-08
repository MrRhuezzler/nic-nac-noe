package Matcher.Client;

import Game.Game;
import Game.packet.UpdatePacket;
import Matcher.Packet.GameSteps;

public class GameManager extends Game {

    private Client client;
    public GameManager(Client client) {
        super();
        this.client = client;
    }

    private void updateField(int x, int y) {
        if(fields[x][y] == Game.FREE) {
            fields[x][y] = currentPlayer;
            if(currentPlayer == Game.PLAYER_ONE) currentPlayer = Game.PLAYER_TWO;
            else if(currentPlayer == Game.PLAYER_TWO) currentPlayer = Game.PLAYER_ONE;
            gamePanel.repaint();
        }
    }

    @Override
    public void inputReceived(int x, int y) {
        if(isMyTurn()) {
            updateField(x, y);
            client.handleStep(x, y);
        }
    }

    @Override
    public void packetReceived(Object object) {
        if(object instanceof GameSteps gs) {
            updateField(gs.getX(), gs.getY());
        }
    }

    @Override
    public void close() {

    }
}
