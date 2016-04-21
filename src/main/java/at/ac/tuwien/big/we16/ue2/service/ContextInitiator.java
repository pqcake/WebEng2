package at.ac.tuwien.big.we16.ue2.service;

import at.ac.tuwien.big.we16.ue2.model.Product;
import at.ac.tuwien.big.we16.ue2.productdata.JSONDataLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class ContextInitiator
        implements ServletContextListener{
    private static final Logger LOGGER = LogManager.getLogger(ContextInitiator.class);
    private ServletContext context;
    private BiddingService biddingService;
    private BiddingAI biddingAI;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        biddingAI.stop();
        LOGGER.debug("ServletContextListener destroyed");
    }

    //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        context=arg0.getServletContext();
        biddingService=new BiddingService();
        List<Product> products= JSONDataLoader.getProducts();
        context.setAttribute("products", products);
        biddingAI=new BiddingAI(biddingService,products);
        LOGGER.debug("products {} loaded and set as attribute",products);

    }
}