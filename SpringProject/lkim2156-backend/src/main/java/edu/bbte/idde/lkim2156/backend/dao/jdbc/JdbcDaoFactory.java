package edu.bbte.idde.lkim2156.backend.dao.jdbc;

import edu.bbte.idde.lkim2156.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.lkim2156.backend.dao.WebshopDao;

public class JdbcDaoFactory extends AbstractDaoFactory {
    private WebshopDao webshopDao;

    @Override
    public synchronized WebshopDao getWebshopDao() {
        if (webshopDao == null) {
            webshopDao = new WebshopJdbcDao();
        }
        return webshopDao;
    }

}
