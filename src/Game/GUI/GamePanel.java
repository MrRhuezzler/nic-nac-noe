package Game.GUI;

import Game.Game;
import Game.res.Resource;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    Game game;
    public GamePanel(Game game) {
        this.game = game;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        // Borders
        g2d.setStroke(new BasicStroke(2));
        for(int i = 0; i < 4; i++) {
            g2d.drawLine(Game.BOARD_X_START + (Game.X_SPACING * i), Game.BOARD_Y_START, Game.BOARD_X_START + (Game.X_SPACING * i), Game.BOARD_HEIGHT);
        }

        for(int i = 0; i < 4; i++) {
            g2d.drawLine(Game.BOARD_X_START, Game.BOARD_Y_START  + (Game.Y_SPACING * i), Game.BOARD_WIDTH , Game.BOARD_Y_START + (Game.X_SPACING * i));
        }

        // 200, 200 == 400, 200   ===> (300, 300)
        //  ||           ||
        //  ||           ||
        // 200, 400 == 400, 400

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++) {
                int field = game.getFields()[y][x];
                if(field != 0) {
                    g2d.drawImage(Resource.letters[field - 1], Game.BOX_X_START + (x * Game.X_SPACING), Game.BOX_Y_START + (y * Game.Y_SPACING), Game.BOX_WIDTH, Game.BOX_HEIGHT, null);
                }
            }
        }

    }
}
