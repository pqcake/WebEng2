package at.ac.tuwien.big.we16.ue2.service;

import at.ac.tuwien.big.we16.ue2.model.Product;
import at.ac.tuwien.big.we16.ue2.model.User;
import at.ac.tuwien.big.we16.ue2.util.HighestBidderExcpetion;
import at.ac.tuwien.big.we16.ue2.util.InsufficientAmountException;
import at.ac.tuwien.big.we16.ue2.util.InsufficientFundsException;
import at.ac.tuwien.big.we16.ue2.util.InvalidProductException;

import java.math.BigDecimal;

/**
 * Created by pqpies on 4/21/16.
 */

public class BiddingService  implements IBiddingService{

    @Override
    public void bid(User user, Product product, BigDecimal amount) throws InsufficientAmountException, InsufficientFundsException, HighestBidderExcpetion, InvalidProductException {
        // product and user objects are globally used with the same reference therefore we lock using them to avoid users bidding on a product at the same time
        // or users bidding on multiple products using the same funds (creating money from thin air)
        synchronized (product) {
            synchronized (user){
                // can't bid on expired product
                if(!product.isExpired()) {
                    // user can't out bid himself
                    if (user == null || !user.equals(product.getHighest_bidder())) {
                        // bid < amount
                        if ((product.getCurrent_bid().compareTo(amount)) == -1) {
                            // amount <= balance (not (amount > balance))
                            if (!(amount.compareTo(user.getBalance()) == 1)) {
                                returnFunds(product.getHighest_bidder(), product, product.getCurrent_bid());
                                product.setCurrent_bid(amount);
                                product.setHighest_bidder(user);
                                user.removeFunds(amount);
                            } else {
                                throw new InsufficientFundsException();
                            }

                        } else {
                            throw new InsufficientAmountException();

                        }
                    } else {
                        throw new HighestBidderExcpetion();
                    }
                } else {
                  throw new InvalidProductException();
                }


            }

        }
    }

    private void returnFunds(User user,Product product,BigDecimal amount){
        if(user!=null) {
            user.addFunds(amount);
            //ToDo inform user (call notifierservice)
        }
    }

}
