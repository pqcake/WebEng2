package at.ac.tuwien.big.we16.ue2.service;

import at.ac.tuwien.big.we16.ue2.model.Product;
import at.ac.tuwien.big.we16.ue2.model.User;
import at.ac.tuwien.big.we16.ue2.util.HighestBidderExcpetion;
import at.ac.tuwien.big.we16.ue2.util.InsufficientAmountException;
import at.ac.tuwien.big.we16.ue2.util.InsufficientFundsException;
import at.ac.tuwien.big.we16.ue2.util.InvalidProductException;

import java.math.BigDecimal;

/**
 * @author Frank Maximilian
 */
public interface IBiddingService {
    void bid(User user, Product product, BigDecimal amount) throws InsufficientAmountException, InsufficientFundsException, HighestBidderExcpetion, InvalidProductException;
}
