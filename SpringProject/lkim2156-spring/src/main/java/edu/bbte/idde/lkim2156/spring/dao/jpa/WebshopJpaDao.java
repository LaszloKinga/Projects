package edu.bbte.idde.lkim2156.spring.dao.jpa;

import edu.bbte.idde.lkim2156.spring.dao.WebshopDao;
import edu.bbte.idde.lkim2156.spring.model.Webshop;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Profile("jpa")
public interface WebshopJpaDao extends WebshopDao, JpaRepository<Webshop, Integer>, JpaSpecificationExecutor<Webshop> {
    @Modifying
    @Transactional
    @Query("UPDATE Webshop w SET w.isShipped = true WHERE w.orderDate < :date AND w.isShipped = false")
    int updateShippedOrders(LocalDate date);

    @Modifying
    @Transactional
    @Query("DELETE FROM Webshop w WHERE w.orderDate < :date")
    int deleteOldOrders(LocalDate date);

    @Query("SELECT w.storeId.id ,w.storeId.storeName, COUNT(w) FROM Webshop w "
            + "GROUP BY w.storeId.id ORDER BY COUNT(w) DESC")
    List<Object[]> getStoreStatistics();

    @Override
    @Modifying
    @Transactional
    @Query("UPDATE Webshop w SET w.storeId = NULL WHERE w.id = :id")
    void clearStoreReference(@Param("id") Integer id);

    @Override
    @Modifying
    @Transactional
    @Query("DELETE FROM Webshop w WHERE w.id = :id")
    void deleteWebshopById(@Param("id") Integer id);
}
