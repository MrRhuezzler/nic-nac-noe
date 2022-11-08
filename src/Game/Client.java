package Game;

import Game.packet.ClientPlayPacket;
import Game.packet.UpdatePacket;

import java.io.IOException;
import java.net.Socket;

public class Client extends Game{

    private Socket socket;

    private Connection connection;

    public Client() {
        super();
        try {
            socket = new Socket("localhost", Game.PORT);
            connection = new Connection(this, socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void inputReceived(int x, int y) {
        if(isMyTurn()) {
            connection.setPacket(new ClientPlayPacket(x, y));
        }
    }

    @Override
    public void packetReceived(Object object) {

        if(object instanceof UpdatePacket) {
            UpdatePacket packet = (UpdatePacket) object;
            fields = packet.getFields();
            currentPlayer = packet.getCurrentPlayer();
        }

        gamePanel.repaint();

    }

    @Override
    public void close() {
        try {
            connection.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
