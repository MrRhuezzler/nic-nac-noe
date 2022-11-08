package Matcher.Packet;

import java.io.Serializable;

public class ClientInfo implements Serializable {
    private String username;

    public ClientInfo(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
