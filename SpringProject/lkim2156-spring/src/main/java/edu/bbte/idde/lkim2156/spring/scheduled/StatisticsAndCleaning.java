package edu.bbte.idde.lkim2156.spring.scheduled;

import edu.bbte.idde.lkim2156.spring.dao.jpa.WebshopJpaDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
public class StatisticsAndCleaning {
    @Autowired
    WebshopJpaDao webshopJpaDao;


    @Scheduled(fixedRate = 60000)
    @Transactional
    public void orderShipmentUpdater() {
        int updatedRows = webshopJpaDao.updateShippedOrders(LocalDate.now().minusDays(7));
        log.info("markOrdersAsShipped(): {} order is shipped.", updatedRows);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void deleteOldOrdersScheduler() {
        int deletedRows = webshopJpaDao.deleteOldOrders(LocalDate.now().minusYears(5));
        log.info("deleteOldOrders(): {} old order deleted.", deletedRows);
    }

    @Scheduled(fixedRate = 60000)
    public void orderStatisticsGenerator() {
        List<Object[]> storeStats = webshopJpaDao.getStoreStatistics();

        if (storeStats.isEmpty()) {
            log.info("stat: No order.");

        } else {
            Object[] topStore = storeStats.get(0);
            log.info("stat: The most popular store: Store ID:  {} Store Name: {} - {} order",
                    topStore[0], topStore[1], topStore[2]);
        }
    }
}
