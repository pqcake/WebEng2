package at.ac.tuwien.big.we16.ue2.service;

import at.ac.tuwien.big.we16.ue2.model.Product;
import at.ac.tuwien.big.we16.ue2.model.User;
import at.ac.tuwien.big.we16.ue2.model.UserPool;
import at.ac.tuwien.big.we16.ue2.util.AuctionMessage;
import at.ac.tuwien.big.we16.ue2.util.Message;
import at.ac.tuwien.big.we16.ue2.util.NewBidMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotifierService {
    private static Map<Session, HttpSession> clients = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executor;
    private static final Logger LOGGER= LogManager.getLogger(NotifierService.class);
    private UserPool userPool;
    private List<Product> products;

    public NotifierService() {
        // Use the scheduled executor to regularly check for recently expired auctions
        // and send a notification to all relevant users.
        this.executor = Executors.newSingleThreadScheduledExecutor();

        Runnable notifyUsers = new Runnable() {
            @Override
            public void run() {
               // LOGGER.debug("running every second!");
                boolean hasExpired = false;
                List<Product> productsExp = new ArrayList<>();

               if(products != null)
               {
                    for (Product p : products) {
                        if (p.isExpired() && (!p.isAllReadySent())) {
                            productsExp.add(p);
                            p.setAllReadySent(true);
                            hasExpired = true;
                        }
                    }
                   // LOGGER.debug("has expired: " + hasExpired);

                    if (clients != null) {
                        if (hasExpired) {
                            for (Map.Entry<Session, HttpSession> entry : clients.entrySet()) {
                                try {
                                    for(Product p:productsExp)
                                    {
                                        //for every product send a message to all clients
                                        User u = userPool.getUser(entry.getValue().getAttribute("user").toString());
                                        List<Product> running=u.getAuctions_running();
                                        if(running.contains(p)){
                                            if(p.getHighest_bidder().equals(u)){
                                                u.getAuctions_won().add(p);
                                            }else{
                                                u.getAuctions_lost().add(p);
                                            }
                                            running.remove(p);
                                        }
                                        Message msg = new AuctionMessage(u,p.getId());
                                        //send with json
                                        LOGGER.debug("Sending json!");
                                        send(entry.getKey(),msg);
                                        //entry.getKey().getBasicRemote().sendText(msg.toJson());
                                    }
                                } catch (Exception e) {
                                    LOGGER.debug("error: " + e);
                                }
                            }
                        }
                    }
                }


            }
        };

        executor.scheduleAtFixedRate(notifyUsers,0, 1000, TimeUnit.MILLISECONDS);

    }
    public void notifyClients(Message msg)
    {
        for (Map.Entry<Session, HttpSession> entry : clients.entrySet()) {
            try {
                //send with json
                //entry.getKey().getBasicRemote().sendText(msg.toJson());
                send(entry.getKey(),msg);
            } catch (Exception e) {
                LOGGER.debug("error: " + e);
            }
        }
    }

    public void notifyClient(User user,Message msg) {
        LOGGER.debug("NotifyClient method entered!");
        for (Map.Entry<Session, HttpSession> entry : clients.entrySet()) {
            if (entry.getValue().getAttribute("user") != null) {
                if (entry.getValue().getAttribute("user").toString().equals(user.getUsername())) {
                    //send message to that user
                    try {
                        //send with json
                        LOGGER.debug("Sending json to user: " + user.getUsername());
                        LOGGER.debug("is open:" + entry.getKey().isOpen());
                        send(entry.getKey(),msg);
                        //entry.getKey().getBasicRemote().sendText(msg.toJson());
                        return;

                    } catch (Exception e) {
                        LOGGER.debug("error: " + e);
                    }
                }
            } else {
                LOGGER.debug("user has been null!");
            }
        }
    }

    private synchronized void send(Session user, Message msg) throws IOException
    {
        try {
            user.getBasicRemote().sendText(msg.toJson());
        }
        catch (IOException e)
        {
            throw new IOException(e);
        }
    }

    /**
     * This method is used by the WebSocket endpoint to save a reference to all
     * connected users. A list of connections is needed so that the users can be
     * notified about events like new bids and expired auctions (see
     * assignment). We need the socket session so that we can push data to the
     * client. We need the HTTP session to find out which user is currently
     * logged in in the browser that opened the socket connection.
     */
    public void register(Session socketSession, HttpSession httpSession) {
        clients.put(socketSession, httpSession);
    }

    public void unregister(Session userSession) {
        clients.remove(userSession);
    }

    /**
     * Call this method from your servlet's shutdown listener to cleanly
     * shutdown the thread when the application stops.
     * 
     * See http://www.deadcoderising.com/execute-code-on-webapp-startup-and-shutdown-using-servletcontextlistener/
     */
    public void stop() {
        this.executor.shutdown();
    }

    public void setUserPool(UserPool pool)
    {
        this.userPool = pool;
    }
    public void setProducts(List<Product> product)
    {
        this.products = product;
    }

    public ScheduledExecutorService getExecutor() {
        return executor;
    }
}
