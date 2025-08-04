package edu.bbte.idde.lkim2156.spring.repository;

import edu.bbte.idde.lkim2156.spring.model.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    Page<Score> findByUserId(Long userId, Pageable pageable);

    List<Score> findByGame_NameOrderByScoreDesc(String gameName, Pageable pageable);

}
