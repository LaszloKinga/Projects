package edu.bbte.idde.lkim2156;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/webshops.hbs")
@Slf4j
public class AuthenticationFilter extends HttpFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {
        LOGGER.info("StatusCode: {}, Method: {}, URL: {}", res.getStatus(), req.getMethod(), req.getRequestURL());
        if (req.getSession().getAttribute("username") == null) {
            res.sendRedirect("/" + getServletContext().getContextPath() + "/login.html");
        } else {
            chain.doFilter(req, res);
        }
    }

}
