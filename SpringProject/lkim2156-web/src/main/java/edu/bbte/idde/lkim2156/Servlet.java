package edu.bbte.idde.lkim2156;

import com.github.jknack.handlebars.Template;
import edu.bbte.idde.lkim2156.backend.model.Webshop;
import edu.bbte.idde.lkim2156.backend.service.WebshopService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/webshops.hbs")
public class Servlet extends HttpServlet {
    public static final WebshopService WEBSHOP_SERVICE = new WebshopService();
    private static final Logger LOGGER = LoggerFactory.getLogger(Servlet.class);


    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        Template template = WebshopTemplateServlet.getTemplate("webshops");
        List<Webshop> webshops = WEBSHOP_SERVICE.findAll();
        List<Map<String, Object>> modifiedWebshops = new ArrayList<>();

        for (Webshop webshop : webshops) {
            Map<String, Object> webshopData = new ConcurrentHashMap<>();

            webshopData.put("id", webshop.getId());
            webshopData.put("orderDate", webshop.getOrderDate());
            webshopData.put("address", webshop.getAddress());
            webshopData.put("totalAmount", webshop.getTotalAmount());
            webshopData.put("paymentMethod", webshop.getPaymentMethod());
            webshopData.put("isShipped", webshop.isShipped());

            webshopData.put("isShippedText", webshop.isShipped() ? "Shipped" : "Not Shipped");

            modifiedWebshops.add(webshopData);
        }

        template.apply(modifiedWebshops, resp.getWriter());

        LOGGER.info("GET /webshops.hbs");
    }


    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        LOGGER.info("Torolve a session");
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}

