package ir.ac.ut.iemdb.tools.JWT;

import ir.ac.ut.iemdb.model.User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Component
public class JWTAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI();
        System.out.println("jwt filter url " + url);
        String method = request.getMethod();

        if(url.equals("/auth/login/") || url.equals("/auth/signup/"))
            chain.doFilter(request, response);
        else {
            System.out.println("hiii");
            String token = request.getHeader("Authorization");
            System.out.println("token:"+token);
            if(token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("You have not authorized yet!");
            }
            else {
                String email = JWTUtils.verifyJWT(token);
                if(email == null) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().println("The JWT token is invalidated!");
                }
                else {
                    request.setAttribute("user", email);
                    chain.doFilter(request, response);
                }
            }
        }
    }
}
