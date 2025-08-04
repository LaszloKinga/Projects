package edu.bbte.idde.lkim2156.backend.service;

import edu.bbte.idde.lkim2156.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.lkim2156.backend.dao.NotFoundException;
import edu.bbte.idde.lkim2156.backend.dao.WebshopDao;
import edu.bbte.idde.lkim2156.backend.model.Webshop;

import java.util.List;

public class WebshopService {
    private final WebshopDao webshopDao = AbstractDaoFactory.getInstance().getWebshopDao();

    public void updateById(Integer id, Webshop webshop) throws NotFoundExceptionService {
        try {
            webshopDao.updateById(id, webshop);
        } catch (NotFoundException e) {
            throw new NotFoundExceptionService("data access error", e);
        }
    }

    public Webshop findById(Integer id) throws NotFoundExceptionService {
        try {
            return webshopDao.findById(id);
        } catch (NotFoundException e) {
            throw new NotFoundExceptionService("data access error", e);
        }
    }


    public synchronized void create(Webshop webshop) throws NotFoundExceptionService {
        try {
            webshopDao.create(webshop);
        } catch (NotFoundException e) {
            throw new NotFoundExceptionService("data access error", e);
        }
    }

    public void deleteById(Integer id) throws NotFoundExceptionService {
        try {
            webshopDao.deleteById(id);
        } catch (NotFoundException e) {
            throw new NotFoundExceptionService("data access error", e);
        }
    }


    public List<Webshop> findAll() throws NotFoundExceptionService {
        try {
            return webshopDao.findAll();
        } catch (NotFoundException e) {
            throw new NotFoundExceptionService("No Webshop data found in data access layer.", e);
        }
    }


}
