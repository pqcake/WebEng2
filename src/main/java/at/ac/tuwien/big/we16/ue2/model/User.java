package at.ac.tuwien.big.we16.ue2.model;

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

    @Override
    public String toString() {
        return username;
    }
}
