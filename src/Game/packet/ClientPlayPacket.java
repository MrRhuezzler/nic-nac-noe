package Game.packet;

import java.io.Serializable;

public class ClientPlayPacket implements Serializable {

    private int x;
    private int y;

    public ClientPlayPacket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
