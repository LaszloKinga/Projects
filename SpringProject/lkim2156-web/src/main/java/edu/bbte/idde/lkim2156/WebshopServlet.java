package edu.bbte.idde.lkim2156;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.bbte.idde.lkim2156.backend.dao.NotFoundException;
import edu.bbte.idde.lkim2156.backend.model.Webshop;
import edu.bbte.idde.lkim2156.backend.service.NotFoundExceptionService;
import edu.bbte.idde.lkim2156.backend.service.WebshopService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/webshops")
public class WebshopServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebshopServlet.class);
    public static final WebshopService WEBSHOP_SERVICE = new WebshopService();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public WebshopServlet() {
        super();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        resp.setContentType("application/json");

        try (PrintWriter writer = resp.getWriter()) {
            if (id == null) {
                List<Webshop> webshops = WEBSHOP_SERVICE.findAll();
                objectMapper.writeValue(writer, webshops);
                LOGGER.info("GET /webshops");
            } else {
                try {
                    Webshop webshop = WEBSHOP_SERVICE.findById(Integer.parseInt(id));
                    if (webshop == null) {
                        LOGGER.info("GET /webshops?id={} - nem talalhato peldany", id);
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        ErrorResponse errorResponse = new ErrorResponse("ResourceNotFound",
                                "Resource not found for the given ID.");
                        objectMapper.writeValue(writer, errorResponse);
                    } else {
                        objectMapper.writeValue(writer, webshop);
                        LOGGER.info("GET /webshops?id={}", id);
                    }
                } catch (NumberFormatException e) {
                    LOGGER.info("Error id = {}", id, e);
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    ErrorResponse errorResponse = new ErrorResponse("BadRequest",
                            "Invalid numeric input format.");
                    objectMapper.writeValue(resp.getWriter(), errorResponse);
                } catch (NotFoundExceptionService e) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    ErrorResponse errorResponse = new ErrorResponse("NotFoundService",
                            "Resource not found in the service layer.");
                    objectMapper.writeValue(resp.getWriter(), errorResponse);
                } catch (NotFoundException e) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    ErrorResponse errorResponse = new ErrorResponse("NotFound",
                            "Resource not found.");
                    objectMapper.writeValue(resp.getWriter(), errorResponse);
                }
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            Webshop newWebshop = objectMapper.readValue(req.getReader(), Webshop.class);

            if (newWebshop.getOrderDate() == null
                    || newWebshop.getAddress() == null
                    || newWebshop.getPaymentMethod() == null
                    || newWebshop.getTotalAmount() <= 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                LOGGER.info("Error POST /webshops");
            } else {
                WEBSHOP_SERVICE.create(newWebshop);
                LOGGER.info("POST /webshops");
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("{\"status\": \"success\", \"message\": \"Webshop added successfully\"}");
            }
        } catch (JsonParseException | JsonMappingException e) {
            LOGGER.info("JSON error. Exception: {}", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorResponse errorResponse = new ErrorResponse("BadRequest",
                    "Malformed JSON: Unable to parse or map the input.");
            objectMapper.writeValue(resp.getWriter(), errorResponse);

        } catch (IOException e) {
            LOGGER.info("Exception {}", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorResponse errorResponse = new ErrorResponse("IOException",
                    "IO exception");
            objectMapper.writeValue(resp.getWriter(), errorResponse);

        } catch (NotFoundExceptionService e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ErrorResponse errorResponse = new ErrorResponse("NotFound",
                    "NOT FOUND SERVICE.");
            objectMapper.writeValue(resp.getWriter(), errorResponse);
        }

    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LOGGER.info("DELETE /webshops");
            String id = req.getParameter("id");

            if (id == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                LOGGER.info("Delete Error /webshops");
            } else {
                LOGGER.info("{} ID Delete /webshops", id);

                if (WEBSHOP_SERVICE.findById(Integer.parseInt(id)) == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.setContentType("application/json");
                    resp.getWriter().write("{\"error\": \"Invalid input format or resource not found.\"}");
                } else {
                    WEBSHOP_SERVICE.deleteById(Integer.valueOf(id));
                    LOGGER.info("DELETE /webshops");
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOGGER.error("Exception {}", e);
            ErrorResponse errorResponse = new ErrorResponse("BadRequest",
                    "Invalid ID. Please provide a valid numeric ID.");
            objectMapper.writeValue(resp.getWriter(), errorResponse);
        } catch (NotFoundExceptionService e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ErrorResponse errorResponse = new ErrorResponse("NotFound",
                    "NOT FOUND SERVICE.");
            objectMapper.writeValue(resp.getWriter(), errorResponse);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            String id = req.getParameter("id");
            LOGGER.info("ID: {}", id);

            if (id == null || id.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ErrorResponse errorResponse = new ErrorResponse("InvalidRequest",
                        "ID cannot be null or empty.");
                objectMapper.writeValue(resp.getWriter(), errorResponse);
                LOGGER.info("Bad request in PUT /webshops");
            } else {

                Webshop updatedWebshop = objectMapper.readValue(req.getReader(), Webshop.class);
                LOGGER.info("Received updated webshop: {}", updatedWebshop);

                Webshop existingWebshop = WEBSHOP_SERVICE.findById(Integer.valueOf(id));
                if (existingWebshop == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    ErrorResponse errorResponse = new ErrorResponse("ResourceNotFound",
                            "Webshop not found for the given ID.");
                    objectMapper.writeValue(resp.getWriter(), errorResponse);
                } else {

                    existingWebshop.setOrderDate(updatedWebshop.getOrderDate());
                    existingWebshop.setAddress(updatedWebshop.getAddress());
                    existingWebshop.setTotalAmount(updatedWebshop.getTotalAmount());
                    existingWebshop.setPaymentMethod(updatedWebshop.getPaymentMethod());
                    existingWebshop.setShipped(updatedWebshop.isShipped());

                    WEBSHOP_SERVICE.updateById(Integer.valueOf(id), existingWebshop);

                    LOGGER.info("PUT /webshops");
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.info("Exception: {}", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorResponse errorResponse = new ErrorResponse("BadRequest",
                    "Invalid input format..");
            objectMapper.writeValue(resp.getWriter(), errorResponse);
        } catch (NotFoundExceptionService e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ErrorResponse errorResponse = new ErrorResponse("NotFound",
                    "NOT FOUND SERVICE");
            objectMapper.writeValue(resp.getWriter(), errorResponse);
        } catch (NotFoundException e) {
            LOGGER.info("Exception: {}", e);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ErrorResponse errorResponse = new ErrorResponse("NotFound",
                    "Resource not found");
            objectMapper.writeValue(resp.getWriter(), errorResponse);
        } catch (JsonParseException | JsonMappingException e) {
            LOGGER.info("Error parsing JSON. Exception: {}", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorResponse errorResponse = new ErrorResponse("BadRequest",
                    "Malformed JSON: Unable to parse or map the input.");
            objectMapper.writeValue(resp.getWriter(), errorResponse);
        }
    }
}
