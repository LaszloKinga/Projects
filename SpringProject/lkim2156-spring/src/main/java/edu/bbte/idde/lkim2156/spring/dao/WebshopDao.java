package edu.bbte.idde.lkim2156.spring.dao;

import edu.bbte.idde.lkim2156.spring.model.Store;
import edu.bbte.idde.lkim2156.spring.model.Webshop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface WebshopDao extends Dao<Webshop>, JpaSpecificationExecutor<Webshop> {

    Page<Webshop> findByPaymentMethod(String paymentMethod, Pageable pageable) throws NotFoundException;

    Page<Webshop> findByStoreId(Store store, Pageable pageable) throws NotFoundException;

    void clearStoreReference(@Param("id") Integer id);

    void deleteWebshopById(@Param("id") Integer id);
}