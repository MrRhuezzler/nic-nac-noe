package Game;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.ServerException;

public class Connection implements Runnable {

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private boolean running;

    private Game game;

    public Connection(Game game, Socket socket) {
        try {
            this.game = game;
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            new Thread(this).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Object object = inputStream.readObject();
                game.packetReceived(object);
            } catch (IOException | ClassNotFoundException e) {
                running = false;
                throw new RuntimeException(e);
            }
        }

    }

    public void setPacket(Object object) {
        try {
            outputStream.reset();
            outputStream.writeObject(object);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws IOException {
        running = false;
        inputStream.close();
        outputStream.close();
    }

}
