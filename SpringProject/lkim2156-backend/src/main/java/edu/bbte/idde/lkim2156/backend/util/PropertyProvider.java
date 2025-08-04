package edu.bbte.idde.lkim2156.backend.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class PropertyProvider {
    private static final Logger LOG = LoggerFactory.getLogger(PropertyProvider.class);
    private static final String CONFIG_FILE_NAME = System.getenv("IDDE_PROPERTIES");
    private static final JsonNode configRoot;

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = null;

        LOG.info("Attempting to load configuration from {}", CONFIG_FILE_NAME);

        try (InputStream inputStream = PropertyProvider.class.getResourceAsStream(CONFIG_FILE_NAME)) {
            if (inputStream != null) {
                root = objectMapper.readTree(inputStream);
            } else {
                LOG.error("Configuration file {} not found", CONFIG_FILE_NAME);
            }
        } catch (IOException e) {
            LOG.error("Error loading configuration", e);
        }

        configRoot = root;
    }


    public static String getProperty(final String key) {
        if (configRoot != null) {
            JsonNode node = configRoot.path(key);
            if (node.isMissingNode()) {
                return null;
            } else {
                return node.asText();
            }
        }
        return null;
    }
}
