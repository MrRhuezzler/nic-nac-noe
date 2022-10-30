package Game.res;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resource {

    public static BufferedImage[] letters;

    static {
        letters = new BufferedImage[2];
        letters[0] = loadImage("res/x.png");
        letters[1] = loadImage("res/o.png");
    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
