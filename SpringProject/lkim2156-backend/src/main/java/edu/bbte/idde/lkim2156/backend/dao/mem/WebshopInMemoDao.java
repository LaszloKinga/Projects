package edu.bbte.idde.lkim2156.backend.dao.mem;


import edu.bbte.idde.lkim2156.backend.dao.NotFoundException;
import edu.bbte.idde.lkim2156.backend.dao.WebshopDao;
import edu.bbte.idde.lkim2156.backend.model.Webshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WebshopInMemoDao implements WebshopDao {
    private static final AtomicInteger ID_COUNTER = new AtomicInteger();

    private final Map<Integer, Webshop> webshopStorage = new ConcurrentHashMap<>();

    @Override
    public List<Webshop> findAll() throws NotFoundException {
        if (webshopStorage == null || webshopStorage.isEmpty()) {
            throw new NotFoundException("Failed to retrieve all Webshop data.");
        }
        return new ArrayList<>(webshopStorage.values());
    }

    @Override
    public Webshop findById(Integer id) throws NotFoundException {

        Webshop webshop = webshopStorage.get(id);
        if (webshop == null) {
            throw new NotFoundException("Failed to retrieve Webshop data by Id.");
        }

        return webshop;

    }


    @Override
    public Webshop updateById(Integer id, Webshop webshop) throws NotFoundException {

        Webshop webshop1 = findById(id);
        webshop1.setAddress(webshop.getAddress());
        webshop1.setOrderDate(webshop.getOrderDate());
        webshop1.setPaymentMethod(webshop.getPaymentMethod());
        webshop1.setTotalAmount(webshop.getTotalAmount());
        webshop1.setShipped(webshop.isShipped());
        return webshop1;

    }

    @Override
    public Webshop create(Webshop webshop) throws NotFoundException {
        if (webshop == null) {
            throw new IllegalArgumentException("Webshop cannot be null.");
        }

        Integer newID = ID_COUNTER.getAndIncrement();
        webshop.setId(newID);
        webshopStorage.put(newID, webshop);
        return webshop;

    }

    @Override
    public void deleteById(Integer id) throws NotFoundException {
        if (webshopStorage.remove(id) == null) {
            throw new NotFoundException("Failed to delete Webshop. ID not found.");

        }
    }
}