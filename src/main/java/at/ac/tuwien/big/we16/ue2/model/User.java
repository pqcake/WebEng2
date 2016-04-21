package at.ac.tuwien.big.we16.ue2.model;

import at.ac.tuwien.big.we16.ue2.util.CurrencyFormatter;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String username = "";
    private String password = "";
    private BigDecimal balance= new BigDecimal(1500);
    private int auctions_running,auctions_won,auctions_lost;


    public User(){        
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getFormattedBalance(){
        return CurrencyFormatter.format(balance);
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getAuctions_running() {
        return auctions_running;
    }

    public void setAuctions_running(int auctions_running) {
        this.auctions_running = auctions_running;
    }

    public int getAuctions_won() {
        return auctions_won;
    }

    public void setAuctions_won(int auctions_won) {
        this.auctions_won = auctions_won;
    }

    public int getAuctions_lost() {
        return auctions_lost;
    }

    public void setAuctions_lost(int auctions_lost) {
        this.auctions_lost = auctions_lost;
    }

    @Override
    public String toString() {
        return username;
    }

    public String toJason() {
        Gson gs = new Gson();

        return gs.toJson(this,User.class);
    }
}
