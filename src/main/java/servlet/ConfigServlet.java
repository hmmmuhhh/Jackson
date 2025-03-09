package servlet;

import model.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class ConfigServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Configuration config;
    public ConfigServlet(){

    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Load cfg.json from resources
            InputStream configStream = getClass().getClassLoader().getResourceAsStream("cfg.json");
            if (configStream == null) {
                throw new IOException("cfg.json not found!");
            }

            // Parse JSON into Configuration object
            config = objectMapper.readValue(configStream, Configuration.class);

            // Store Configuration as a servlet context attribute
            getServletContext().setAttribute("config", config);

        } catch (IOException e) {
            // Log the error (e.g., using a logging framework)
            throw new ServletException("Failed to load configuration", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Retrieve Configuration from the servlet context
        Configuration config = (Configuration) getServletContext().getAttribute("config");

        if (config == null) {
            // Configuration not loaded → return 403 Forbidden
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Configuration not loaded");
            return;
        }

        // Write Configuration as JSON to the response
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), config);
    }
}