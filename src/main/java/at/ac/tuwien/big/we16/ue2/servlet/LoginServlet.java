package at.ac.tuwien.big.we16.ue2.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import at.ac.tuwien.big.we16.ue2.model.User;
import at.ac.tuwien.big.we16.ue2.model.User.Interest;
import at.ac.tuwien.big.we16.ue2.model.UserPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Mayerhofer
 */
public class LoginServlet extends HttpServlet {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private UserPool userpool;
    private static final Logger LOGGER= LogManager.getLogger(LoginServlet.class);
    
    @Override
    public void init() throws ServletException {
        super.init();
        userpool = new UserPool();
    }
  
    /**
     * Responsible for login
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.debug("doGet");

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setHeader("Expires", "0"); // Proxies.

        if(request.getSession(false)!=null){
            LOGGER.debug("Session found, forward to overview!");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/overview.jsp");
            dispatcher.forward(request, response);
        }else{
            LOGGER.debug("No session found, forward to login!");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/login.html");
            dispatcher.forward(request, response);
        }

        /*String action = request.getParameter("action");
        if(action==null) {
            return;
        }
        if(action.equals("login")) {                           
            HttpSession session = request.getSession(true);
            User user = userpool.getUser(request.getParameter("username"), request.getParameter("password"));
            if(user == null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/loginfail.html");
                dispatcher.forward(request, response);
            } else {
                session.setAttribute("user", user);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/userpage.jsp");
                dispatcher.forward(request, response);
            }               
        } else if (action.equals("logout")){
            request.getSession(true).invalidate();
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.html");
            dispatcher.forward(request, response);
        } else if (action.equals("userdata")) {
            User user =(User)request.getSession().getAttribute("user");
            
            if(user!=null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/userdata.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/userdatafail.html");
                dispatcher.forward(request, response);
            }
        }*/
    }

    /**
     * Responsible for updating a user and creating new users.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.debug("doPost");

        HttpSession session=request.getSession(true);

        User user=new User();
        user.setFirstname(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        userpool.registerUser(user);

        session.setAttribute("user", user);
        LOGGER.debug("added {} to session", user);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/overview.jsp");
        dispatcher.forward(request, response);
        //response.sendRedirect("views/overview.jsp");

        /*
        HttpSession session = request.getSession(true);
        User user =(User)session.getAttribute("user");
        boolean newuser = false;
        if(user == null) {
            user = new User();            
            newuser = true;
            user.setUsername(request.getParameter("username"));
        } else {
            user = userpool.getUser(user.getUsername());
        }
        user.setFirstname(request.getParameter("firstname"));
        user.setLastname(request.getParameter("lastname"));        
        user.setPassword(request.getParameter("password"));

        String[] inter = request.getParameterValues("interests");
        if(!newuser) {
            user.clearInterests();
        }
        if(inter != null ) {
            List<String> interests = Arrays.asList(inter);
            if(interests.contains("webEngineering")) {
                user.addInterest(Interest.WEBENINEERING);                
            }
            if(interests.contains("modelEngineering")) {
                user.addInterest(Interest.MODELENGINEERING);                
            }
            if(interests.contains("semanticWeb")) {
                user.addInterest(Interest.SEMANTICWEB);                
            }
            if(interests.contains("objectOrientedModeling")) {
                user.addInterest(Interest.OBJECTORIENTEDMODELING);                
            }
            if(interests.contains("businessInformatics")) {
                user.addInterest(Interest.BUSINESSINFORMATICS);
            }         
        } 
        
        session.setAttribute("user", user);
        
        if(newuser) {
            userpool.registerUser(user);
        }           
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/userpage.jsp");
        dispatcher.forward(request, response);
        */
    }
    
    @Override
    public String getServletInfo() {
        return "Servlet for registration";
    }
}
