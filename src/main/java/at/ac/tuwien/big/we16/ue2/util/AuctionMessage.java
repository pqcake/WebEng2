package at.ac.tuwien.big.we16.ue2.util;

import at.ac.tuwien.big.we16.ue2.model.User;
import com.google.gson.Gson;

import java.math.BigDecimal;

/**
 * Created by david on 21.04.2016.
 */
public class AuctionMessage extends Message {

    private final MsgType type = MsgType.AUCTION_EXPIRED;
    private long productID;
    private BigDecimal currentBalance;
    private int runningBids;
    private int wonAuctions;
    private int lostAuctions;
    public AuctionMessage(User u,long productID)
    {
        this.currentBalance = u.getBalance();
        this.lostAuctions = u.getAuctions_lost().size();
        this.wonAuctions = u.getAouctions_won().size();
        this.runningBids = 10;//u.getAuctions_running().size();
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

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getRunningBids() {
        return runningBids;
    }

    public void setRunningBids(int runningBids) {
        this.runningBids = runningBids;
    }

    public int getWonAuctions() {
        return wonAuctions;
    }

    public void setWonAuctions(int wonAuctions) {
        this.wonAuctions = wonAuctions;
    }

    public int getLostAuctions() {
        return lostAuctions;
    }

    public void setLostAuctions(int lostAuctions) {
        this.lostAuctions = lostAuctions;
    }

    public String toJson()
    {
        Gson gs = new Gson();
        return gs.toJson(this,AuctionMessage.class);
    }
}
