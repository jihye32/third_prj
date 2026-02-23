package kr.co.sist;

import java.io.IOException;

import org.apache.logging.log4j.core.config.Order;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RedirectTraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        HttpServletResponseWrapper wrapper =
                new HttpServletResponseWrapper(res) {
                    @Override
                    public void sendRedirect(String location) throws IOException {
                        System.out.println("### sendRedirect called! from: " 
                            + req.getRequestURI() + " -> " + location);
                        new Exception("Redirect stack").printStackTrace();
                        super.sendRedirect(location);
                    }
                };

        chain.doFilter(req, wrapper);
    }
}