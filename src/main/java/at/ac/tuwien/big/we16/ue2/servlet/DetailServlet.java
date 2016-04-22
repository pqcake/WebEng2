package at.ac.tuwien.big.we16.ue2.servlet;

import at.ac.tuwien.big.we16.ue2.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Philipp on 21.04.2016.
 */
public class DetailServlet extends HttpServlet {

    private Logger LOGGER = LogManager.getLogger(DetailServlet.class);
    private Map<Long,Product> productMap;

    public void init() throws ServletException {
        productMap= (Map<Long, Product>) getServletContext().getAttribute("productMap");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //JSONDataLoader.getProducts().keySet().forEach(item -> LOGGER.debug("contains {} {}", item, JSONDataLoader.getProducts().get(item)));
        //Product p=JSONDataLoader.getProducts().get(Long.parseLong(request.getParameter("id")));
        Product p=productMap.get(Long.parseLong(request.getParameter("id")));
        LOGGER.debug("setting product {} {}",p.getId(),p.getName());
        request.setAttribute("product",p);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/details.jsp");
        dispatcher.forward(request, response);
    }
}
