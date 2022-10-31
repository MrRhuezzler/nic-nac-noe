package Game;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        int choice = Integer.parseInt(JOptionPane.showInputDialog("1 for Server | 2 for client"));
        if(choice == 1) {
            new Server();
        } else {
            new Client();
        }
    }

}
