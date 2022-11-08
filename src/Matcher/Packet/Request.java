package Matcher.Packet;

import java.io.Serializable;

public class Request implements Serializable {
    private int code;
    // 1 -> MatchMake request

    public Request(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
