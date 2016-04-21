package at.ac.tuwien.big.we16.ue2.model;

import at.ac.tuwien.big.we16.ue2.util.CurrencyFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String username = "";
    private String password = "";
    private BigDecimal balance= new BigDecimal(1500);
    private List<Product> auctions_running,aouctions_won,auctions_lost;


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

    public List<Product> getAuctions_running() {
        return auctions_running;
    }

    public List<Product> getAouctions_won() {
        return aouctions_won;
    }

    public List<Product> getAuctions_lost() {
        return auctions_lost;
    }

    public BigDecimal getBalance() {return balance;}

    public String getFormattedBalance(){
        return CurrencyFormatter.format(balance);
    }

    public void addFunds(BigDecimal amount) {
        synchronized (balance) {
            balance.add(amount);
        }
    }

    public void removeFunds(BigDecimal amount) {
        synchronized (balance) {
            balance.subtract(amount);
        }
    }

    @Override
    public String toString() {
        return username;
    }
}
