package edu.bbte.idde.lkim2156.spring.repository;

import edu.bbte.idde.lkim2156.spring.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByName(String name);
}
