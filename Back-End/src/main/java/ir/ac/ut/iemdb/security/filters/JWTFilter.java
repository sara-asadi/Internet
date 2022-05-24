package ir.ac.ut.iemdb.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ir.ac.ut.iemdb.security.jwt.JWTAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        try {
            String uri = ((HttpServletRequest)servletRequest).getRequestURI();
            if(uri.contains("signup") || uri.contains("login") || uri.contains("callback")) {
                chain.doFilter(servletRequest, servletResponse);
                return;
            }
            JWTVerifier verifier = JWT.require(JWTAuthentication.algorithm)
                    .withIssuer("iemdb.ut.ac.ir")
                    .build();
            String header = ((HttpServletRequest)servletRequest).getHeader("Authorization");
            if(header != null) {
                header = header.substring(7).replace("\"","");
                System.out.println(header);
                DecodedJWT jwt;
                jwt = verifier.verify(header);
                String id =  jwt.getClaim("id").asString();
                servletRequest.setAttribute("id", id);
                chain.doFilter(servletRequest, servletResponse);
            }
            else
                ((HttpServletResponse)servletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        catch (JWTVerificationException exception){
            ((HttpServletResponse)servletResponse).setStatus(HttpStatus.FORBIDDEN.value());
        }

    }
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

}