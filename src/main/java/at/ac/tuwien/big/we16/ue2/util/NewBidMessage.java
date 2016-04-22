package at.ac.tuwien.big.we16.ue2.util;

import at.ac.tuwien.big.we16.ue2.model.User;
import com.google.gson.Gson;

import java.math.BigDecimal;

/**
 * Created by david on 21.04.2016.
 */
public class NewBidMessage extends Message {

    private final MsgType type = MsgType.NEW_BID;
    private long productID;
    private String highestBidName;
    private BigDecimal bid;

    public NewBidMessage(String highestBidName, BigDecimal bid, long productID)
    {
        this.highestBidName = highestBidName;
        this.bid = bid;
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

    public String getHighestBidName() {
        return highestBidName;
    }

    public void setHighestBidName(String highestBidName) {
        this.highestBidName = highestBidName;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public String toJson()
    {
        Gson gs = new Gson();
        return gs.toJson(this,AuctionMessage.class);
    }
}
