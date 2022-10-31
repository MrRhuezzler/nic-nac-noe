package Game;

import Game.packet.ClientPlayPacket;
import Game.packet.UpdatePacket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Game {

    private ServerSocket serverSocket;
    private Socket socket;

    private Connection connection;

    public Server() {
        super(Game.PLAYER_ONE);
        try {
            serverSocket = new ServerSocket(Game.PORT);
            socket = serverSocket.accept();
            connection = new Connection(this, socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateField(int x, int y) {
        if(fields[x][y] == Game.FREE) {
            fields[x][y] = currentPlayer;
            if(currentPlayer == Game.PLAYER_ONE) currentPlayer = Game.PLAYER_TWO;
            else if(currentPlayer == Game.PLAYER_TWO) currentPlayer = Game.PLAYER_ONE;

            connection.setPacket(new UpdatePacket(fields, currentPlayer));
            gamePanel.repaint();
        }
    }

    @Override
    public void inputReceived(int x, int y) {
        if(isMyTurn()) {
            updateField(x, y);
        }
    }

    @Override
    public void packetReceived(Object object) {
        if(object instanceof ClientPlayPacket) {
            ClientPlayPacket packet = (ClientPlayPacket) object;
            updateField(packet.getX(), packet.getY());
        }
        gamePanel.repaint();
    }

    @Override
    public void close() {
        try {
            connection.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
