package edu.bbte.idde.lkim2156.backend.dao;

import edu.bbte.idde.lkim2156.backend.dao.jdbc.JdbcDaoFactory;
import edu.bbte.idde.lkim2156.backend.dao.mem.InMemDaoFactory;
import edu.bbte.idde.lkim2156.backend.util.PropertyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractDaoFactory {
    private static AbstractDaoFactory INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDaoFactory.class);

    public abstract WebshopDao getWebshopDao();

    public static synchronized AbstractDaoFactory getInstance() {
        if (INSTANCE == null) {
            String profile = PropertyProvider.getProperty("profile");
            LOGGER.info("Using profile: {}", profile);
            if ("jdbc".equals(profile)) {
                INSTANCE = new JdbcDaoFactory();
            } else {
                INSTANCE = new InMemDaoFactory();
            }
        }
        return INSTANCE;
    }

}

