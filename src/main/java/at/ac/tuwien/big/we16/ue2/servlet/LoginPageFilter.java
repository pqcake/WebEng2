package at.ac.tuwien.big.we16.ue2.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Philipp on 19.04.2016.
 */
public class LoginPageFilter implements Filter {

    private static final Logger LOGGER= LogManager.getLogger(LoginPageFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,   FilterChain filterChain) throws IOException, ServletException
    {
        LOGGER.debug("doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //if(request.getUserPrincipal() != null){ //If user is already authenticated
        if(request.getSession().getAttribute("user") != null){ //If user is already authenticated
            LOGGER.debug("session has user "+request.getSession().getAttribute("user")+" redirecting to overview");
            response.sendRedirect("/views/overview.jsp");// or, forward using RequestDispatcher
        } else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy()
    {

    }

}
