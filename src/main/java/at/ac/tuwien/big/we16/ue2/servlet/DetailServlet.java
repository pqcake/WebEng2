package at.ac.tuwien.big.we16.ue2.servlet;

import at.ac.tuwien.big.we16.ue2.model.Product;
import at.ac.tuwien.big.we16.ue2.model.User;
import at.ac.tuwien.big.we16.ue2.model.UserPool;
import at.ac.tuwien.big.we16.ue2.productdata.JSONDataLoader;
import at.ac.tuwien.big.we16.ue2.service.BiddingService;
import at.ac.tuwien.big.we16.ue2.service.IBiddingService;
import at.ac.tuwien.big.we16.ue2.service.ServiceFactory;
import at.ac.tuwien.big.we16.ue2.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Philipp on 21.04.2016.
 */
public class DetailServlet extends HttpServlet {
    private Logger LOGGER = LogManager.getLogger(DetailServlet.class);
    private IBiddingService biddingService;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        biddingService = (BiddingService)getServletContext().getAttribute("biddingService");
        Map<String,String> answer = new HashMap<>();
        boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        if(ajax) {
            LOGGER.debug("got ajax request!");
            String newPrice = request.getParameter("new-price");
            String productIDS = request.getParameter("product-id");
            BigDecimal newBid = new BigDecimal(newPrice);
            int productID = Integer.parseInt(productIDS);

            User u =  (User)request.getSession().getAttribute("user");

            if (u != null) {
                LOGGER.debug("User found in detail page: " + u);

                LOGGER.debug("getting products from context!");
                List<Product> products = (List<Product>) getServletContext().getAttribute("products");

                if (products != null) {
                    LOGGER.debug("products from context size: " + products.size());
                    for (Product p : products) {
                        if (p.getId() == productID) {
                            try {
                                biddingService.bid(u, p, newBid);
                                //sendResponse("Ok",response);
                                NewBidMessage msg = new NewBidMessage(u.getUsername(), newBid, productID);
                                ServiceFactory.getNotifierService().notifyClients(msg);

                                answer.put("status","ok");
                                answer.put("runningAuctions",String.valueOf(u.getAuctions_running().size()));
                                answer.put("balance", String.valueOf(u.getBalance()));
                                Gson gs = new Gson();
                                sendResponse(gs.toJson(answer),response);

                            } catch (Exception e) {
                                LOGGER.debug("exception: " + e);
                                //respond with exception message
                                try {
                                    answer.put("status","error");
                                    answer.put("text",e.getMessage());
                                    sendResponse(new Gson().toJson(answer), response);
                                } catch (IOException eIO) {
                                    LOGGER.debug("could not write to client: " + e);
                                }

                            }

                        }
                    }

                }
            }
            else
            {
                LOGGER.debug("no user in session found!");
            }

            //LOGGER.debug("result: " + foo + ":" + bar + ":" + baz);
        }
        else
        {
            LOGGER.debug("NO AJAX!");
        }
    }

    private void sendResponse(String text,HttpServletResponse response) throws IOException
    {
        response.setHeader("Content-Type", "text/plain");
        try {
            PrintWriter writer = response.getWriter();
            writer.write(text);
            writer.close();
        }
        catch (IOException e)
        {
           throw new IOException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONDataLoader.getProducts().keySet().forEach(item -> LOGGER.debug("contains {} {}", item, JSONDataLoader.getProducts().get(item)));
        Product p=JSONDataLoader.getProducts().get(Long.parseLong(request.getParameter("id")));
        LOGGER.debug("setting product {} {}",p.getId(),p.getName());
        request.setAttribute("product",p);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/details.jsp");
        dispatcher.forward(request, response);
    }
}
