package at.ac.tuwien.big.we16.ue2.model;

import at.ac.tuwien.big.we16.ue2.util.CurrencyFormatter;
import org.h2.mvstore.ConcurrentArrayList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class User {

    private String username = "";
    private String password = "";
    private BigDecimal balance= new BigDecimal(1500);
    private List<Product> auctions_running,aouctions_won,auctions_lost;


    public User(){

        auctions_running= Collections.synchronizedList(new LinkedList<>());
        aouctions_won= Collections.synchronizedList(new LinkedList<>());
        auctions_lost= Collections.synchronizedList(new LinkedList<>());
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
        return CurrencyFormatter.format(getBalance());
    }

    public void addFunds(BigDecimal amount) {
        synchronized (balance) {
            balance=balance.add(amount);
        }
    }

    public void newBid(BigDecimal amount,Product p) {
        //substract amount from balance and increase running auctions
        synchronized (balance) {
            balance=balance.subtract(amount);
        }
        synchronized (auctions_running)
        {
            if(!auctions_running.contains(p)) {
                auctions_running.add(p);
            }
        }
    }

    @Override
    public String toString() {
        return username;
    }
}
