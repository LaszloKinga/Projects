package edu.bbte.idde.lkim2156.backend.dao.mem;

import edu.bbte.idde.lkim2156.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.lkim2156.backend.dao.WebshopDao;

public class InMemDaoFactory extends AbstractDaoFactory {
    private WebshopDao webshopDao;

    @Override
    public synchronized WebshopDao getWebshopDao() {
        if (webshopDao == null) {
            webshopDao = new WebshopInMemoDao();
        }
        return webshopDao;
    }
}
