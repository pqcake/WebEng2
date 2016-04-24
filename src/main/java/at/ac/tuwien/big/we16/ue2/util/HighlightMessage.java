package at.ac.tuwien.big.we16.ue2.util;

import com.google.gson.Gson;

/**
 * Created by pqpies on 4/24/16.
 */
public class HighlightMessage extends Message {
    private final MsgType type = MsgType.HIGHLIGHT;
    private long productID;

    public HighlightMessage(long productID) {
        this.productID = productID;
    }

    public MsgType getType() {
        return type;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String toJson()
    {
        Gson gs = new Gson();
        return gs.toJson(this,HighlightMessage.class);
    }
}
