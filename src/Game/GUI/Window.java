package Game.GUI;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window(String title, int width, int height){
        super(title);
        setResizable(false);
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
