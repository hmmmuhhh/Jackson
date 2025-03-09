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

    public ConfigServlet() {

    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            InputStream configStream = getClass().getClassLoader().getResourceAsStream("cfg.json");
            if (configStream == null) {
                throw new IOException("cfg.json not found!");
            }

            config = objectMapper.readValue(configStream, Configuration.class);
            getServletContext().setAttribute("config", config);

        } catch (IOException e) {
            throw new ServletException("Failed to load configuration", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Configuration config = (Configuration) getServletContext().getAttribute("config");

        if (config == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Configuration not loaded");
            return;
        }

        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), config);
    }
}