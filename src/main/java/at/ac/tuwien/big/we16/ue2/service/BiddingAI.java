package at.ac.tuwien.big.we16.ue2.service;

import at.ac.tuwien.big.we16.ue2.model.AIUser;
import at.ac.tuwien.big.we16.ue2.model.Product;
import at.ac.tuwien.big.we16.ue2.model.User;
import at.ac.tuwien.big.we16.ue2.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Frank Maximilian
 */
public class BiddingAI implements Runnable {
    private final ScheduledExecutorService executor;
    private static final Logger LOGGER= LogManager.getLogger(BiddingAI.class);
    private Random rnd;
    private User ai;
    private List<Product> productList;
    private BiddingService biddingService;

    public BiddingAI(BiddingService biddingService, List<Product> products){
        this.biddingService=biddingService;
        this.productList=products;
        this.executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this,0,10, TimeUnit.SECONDS);
        rnd=new Random(System.currentTimeMillis());
        ai=new AIUser();
        ai.setUsername("arnie@comma.ai");
        ai.setPassword("Mistviech");
    }
    @Override
    public void run() {
        try {
            LOGGER.info("Bidding AI run");
            for (Product p : productList) {
                if ((!p.isExpired()) && !ai.equals(p.getHighest_bidder()) && dealOrNoDeal()) {
                    BigDecimal bid = new BigDecimal(10);
                    bid = bid.add(p.getCurrent_bid());
                    LOGGER.info("Bidding on product:{} {} amount: {}", p.getId(), p.getName(), CurrencyFormatter.format(bid));
                    try {
                        biddingService.bid(ai, p, bid);
                        NewBidMessage msg = new NewBidMessage(ai.getUsername(),bid,p.getId());
                        ServiceFactory.getNotifierService().notifyClients(msg);
                    } catch (InsufficientAmountException e) {
                        LOGGER.error("AI did not bid high enough something is weird!");
                    } catch (InsufficientFundsException e) {
                        LOGGER.error("AI did not have enough funds to bid something is weird!");
                    } catch (HighestBidderExcpetion | InvalidProductException e) {
                        LOGGER.error("AI should not have bid on this product!");
                    }
                } else {
                    LOGGER.info("Skipping product:{} {}", p.getId(), p.getName());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stop(){
        this.executor.shutdown();
    }

    private boolean dealOrNoDeal(){
        return rnd.nextDouble() < 0.3;
    }
}
