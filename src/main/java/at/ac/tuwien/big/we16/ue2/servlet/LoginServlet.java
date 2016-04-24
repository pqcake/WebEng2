package at.ac.tuwien.big.we16.ue2.servlet;

import at.ac.tuwien.big.we16.ue2.model.User;
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

public class LoginServlet extends HttpServlet {

	private UserPool userpool;
    private static final Logger LOGGER= LogManager.getLogger(LoginServlet.class);
    
    @Override
    public void init() throws ServletException {
        //super.init(); //not necessary for init(), only for init (ServletConfig config)
        LOGGER.debug("init() called");
        userpool = (UserPool) getServletContext().getAttribute("userPool");
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
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/login.jsp");
            dispatcher.forward(request, response);
        }
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
        user.setUsername(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        userpool.registerUser(user);

        session.setAttribute("user", user);
        LOGGER.debug("added {} to session", user);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/overview.jsp");
        dispatcher.forward(request, response);
    }
    @Override public void destroy() {
    }
}
