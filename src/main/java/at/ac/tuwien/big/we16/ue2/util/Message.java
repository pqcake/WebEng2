package at.ac.tuwien.big.we16.ue2.util;

import com.google.gson.Gson;

/**
 * Created by david on 21.04.2016.
 */
public abstract class Message {

    //helper class to send messages to clients
    enum MsgType {
        AUCTION_EXPIRED,
        NEW_BID,
        OUTBIDDEN,
        HIGHLIGHT
    };

    public String toJson()
    {
        Gson gs = new Gson();
        return gs.toJson(this,Message.class);
    }

}
