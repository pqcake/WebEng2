package at.ac.tuwien.big.we16.ue2.util;

import com.google.gson.Gson;

import java.math.BigDecimal;

/**
 * Created by david on 21.04.2016.
 */
public class OutbiddenMessage extends Message {
    private final MsgType type = MsgType.OUTBIDDEN;
    private BigDecimal newBalance;
    private long productID;

    public OutbiddenMessage(BigDecimal newBalance, long productID) {
        this.newBalance = newBalance;
        this.productID = productID;
    }

    public MsgType getType() {
        return type;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
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
        return gs.toJson(this,OutbiddenMessage.class);
    }
}
