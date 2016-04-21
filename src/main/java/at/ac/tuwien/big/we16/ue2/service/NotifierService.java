package at.ac.tuwien.big.we16.ue2.service;

import at.ac.tuwien.big.we16.ue2.model.Product;
import at.ac.tuwien.big.we16.ue2.model.UserPool;
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
                LOGGER.debug("running every second!");
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
                    LOGGER.debug("has expired: " + hasExpired);

                    if (clients != null) {
                        if (hasExpired) {
                            for (Map.Entry<Session, HttpSession> entry : clients.entrySet()) {
                                try {
                                    String expString = "Expired";
                                    for (Product p : productsExp) {
                                        expString += ":" + p.getId();
                                    }
                                    if (entry.getValue() != null) {
                                      // Object o = entry.getValue().getAttribute("user");

                                    }
                                    //  String s = (String) entry.getValue().getAttribute("user");
                                    //  expString += ":" + s;
                                    entry.getKey().getBasicRemote().sendText(expString);

                                } catch (IOException e) {
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
