package Matcher.Server;

import Matcher.Packet.ClientInfo;
import Matcher.Packet.GameSteps;
import Matcher.Packet.Request;
import Matcher.Packet.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private Object request;
    private String username;

    public ClientThread(Socket s) {
        socket = s;
    }

    public void run() {

        try {

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            while (true) {
                request = inputStream.readObject();
                if(request instanceof ClientInfo ci) {
                    username = ci.getUsername();
                } else if(request instanceof Request r) {
                    if(r.getCode() == 1) {
                        if(username != null) {
                            sendPacket(new Response(2));
                            Server.lobby.add(this);
                            synchronized (Server.matcher) {
                                Server.matcher.notify();
                            }
                        } else {
                            sendPacket(new Response(3));
                        }
                    }
                } else if(request instanceof GameSteps gs) {
                    Server.games.get(gs.getMatchId()).handlePackets(gs, this);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    void sendPacket(Object object) {
        try {
            outputStream.reset();
            outputStream.writeObject(object);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getUsername() {
        return username;
    }
}
