package edu.bbte.idde.lkim2156.spring.repository;


import edu.bbte.idde.lkim2156.spring.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}

