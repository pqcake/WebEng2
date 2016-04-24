package at.ac.tuwien.big.we16.ue2.websocket;

import at.ac.tuwien.big.we16.ue2.service.NotifierService;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * This endpoint listens on the /socket URL.
 */
@ServerEndpoint(value="/socket", configurator = BigBidConfigurator.class)
public class BigBidEndpoint {
    private final NotifierService notifierService;
    private static final Logger LOGGER = LogManager.getLogger(BigBidEndpoint.class);
    public BigBidEndpoint(NotifierService notifierService) {
        this.notifierService = notifierService;
    }

    /**
     * When a new WebSocket connection is established, we register both the
     * socket session and the associated HTTP session with the notifier service.
     */
    @OnOpen
    public void onOpen(Session socketSession, EndpointConfig config) {
        this.notifierService.register(socketSession, (HttpSession) config.getUserProperties().get(HttpSession.class.getName()));

    }

    @OnMessage
    public void onMessage(String  message, Session session)
    {

    }
    /**
     * When a socket connection is closed, we remove its session from the
     * notifier service.
     */
    @OnClose
    public void onClose(Session socketSession) {
        LOGGER.debug("closing session!");
        this.notifierService.unregister(socketSession);
    }
}