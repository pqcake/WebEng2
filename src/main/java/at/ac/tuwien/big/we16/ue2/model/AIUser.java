package at.ac.tuwien.big.we16.ue2.model;

import java.math.BigDecimal;

/**
 * Created by pqpies on 4/21/16.
 */
public class AIUser extends User {
    @Override
    public void addFunds(BigDecimal funds){}
    @Override
    public void removeFunds(BigDecimal funds){}
    @Override
    public BigDecimal getBalance() {
        return new BigDecimal(Double.MAX_VALUE);
    }
}
