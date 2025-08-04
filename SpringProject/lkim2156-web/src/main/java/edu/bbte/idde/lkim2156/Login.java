package edu.bbte.idde.lkim2156;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static String username = "user";
    private static String password = "user";
    private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (password.equals(req.getParameter("ps")) && username.equals(req.getParameter("us"))) {
            req.getSession().setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/webshops.hbs");
        } else {
            LOGGER.info("Jelszohiba");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}