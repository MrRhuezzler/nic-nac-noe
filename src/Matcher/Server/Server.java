package Matcher.Server;

import Game.Game;
import Matcher.Packet.GameDone;
import Matcher.Packet.GameSteps;
import Matcher.Packet.Response;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class Moderator extends Thread {

    private String id;
    ClientThread player1 = null, player2 = null;
    private boolean isRunning = true;

    public Moderator(String id, ClientThread player1, ClientThread player2) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void run() {

        player1.sendPacket(new Response(1, Game.PLAYER_ONE, id));
        player2.sendPacket(new Response(1, Game.PLAYER_TWO, id));

        while(!isRunning) {
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void handlePackets(Object object, ClientThread playedPlayer) {
        if(object instanceof GameSteps gs) {
            if(playedPlayer == player1) {
                player2.sendPacket(gs);
            } else {
                player1.sendPacket(gs);
            }
        } else if(object instanceof GameDone gd) {
            if(playedPlayer == player1) {
                player2.sendPacket(gd);
            } else {
                player1.sendPacket(gd);
            }
            isRunning = false;
            synchronized (this) {
                this.notify();
            }
        }
    }

}

class Matcher extends Thread {

    public void run() {
        try {

            ClientThread player1 = null, player2 = null;
            while (true) {
                while (Server.lobby.size() < 2) {
                    synchronized (this) {
                        this.wait();
                    }
                }

                player1 = Server.lobby.poll();
                player2 = Server.lobby.poll();

                String id = player1.getUsername() + player2.getUsername();
                Moderator moderator = new Moderator(id, player1, player2);
                Server.games.put(id, moderator);
                moderator.start();

            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

public class Server {

    public static int PORT = 8080;
    private ServerSocket serverSocket;
    private Socket socket;

    static Queue<ClientThread> lobby;
    static HashMap<String, Moderator> games;
    static Matcher matcher;

    public Server() {
        games = new HashMap<>();
        lobby = new LinkedList<>();
        matcher = new Matcher();
        matcher.start();
    }

    public void runForever() {
        try {

            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started @ " + PORT);

            while(true) {
                socket = serverSocket.accept();
                System.out.println("Client connected");
                new ClientThread(socket).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.runForever();
    }

}
