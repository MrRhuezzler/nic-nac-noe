package Matcher.Client;

import Game.Game;
import Matcher.Packet.ClientInfo;
import Matcher.Packet.GameSteps;
import Matcher.Packet.Request;
import Matcher.Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private String username;
    private Socket socket;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private Game game;
    private String matchId = null;
    private int playerNumber = Game.FREE;

    public Client() {
        game = new GameManager(this);
    }

    public void runForever() {
        try {

            socket = new Socket("localhost", Server.PORT);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            username = scanner.nextLine();

            sendPacket(new ClientInfo(username));
            Thread.sleep(1000);
            sendPacket(new Request(1));

            while (true) {
                Object object = inputStream.readObject();
                if (object instanceof Matcher.Packet.Response r) {
                    if (r.getCode() == 1) {
                        System.out.println("Match found!");
                        playerNumber = r.getPlayerNumber();
                        game.setPlayer(playerNumber);
                        matchId = r.getMatchId();
                    } else if (r.getCode() == 2) {
                        System.out.println("Matching in progress...");
                    } else if(r.getCode() == 3) {
                        System.out.println("Matching making failed!");
                    }
                } else if(object instanceof Matcher.Packet.GameSteps gs) {
                    game.packetReceived(gs);
                }
            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected void handleStep(int x, int y) {
        if(playerNumber == Game.FREE) {
            System.out.println("You are not in a match!");
        } else if(playerNumber == Game.PLAYER_ONE) {
            sendPacket(new GameSteps(matchId,x, y, Game.PLAYER_TWO));
        } else if(playerNumber == Game.PLAYER_TWO) {
            sendPacket(new GameSteps(matchId,x, y, Game.PLAYER_ONE));
        }
    }

    protected void sendPacket(Object object) {
        try {
            outputStream.reset();
            outputStream.writeObject(object);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.runForever();
    }

}
