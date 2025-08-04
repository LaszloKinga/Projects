package edu.bbte.idde.lkim2156.spring.dao.jpa;

import edu.bbte.idde.lkim2156.spring.dao.StoreDao;
import edu.bbte.idde.lkim2156.spring.model.Store;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface StoreJpaDao extends StoreDao, JpaRepository<Store, Integer> {

}
