package edu.bbte.idde.lkim2156.spring.service;

import edu.bbte.idde.lkim2156.spring.model.Game;
import edu.bbte.idde.lkim2156.spring.model.Score;
import edu.bbte.idde.lkim2156.spring.model.UserEntity;
import edu.bbte.idde.lkim2156.spring.repository.GameRepository;
import edu.bbte.idde.lkim2156.spring.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final GameRepository gameRepository;

    public void saveScore(UserEntity user, int score, String gameMode) {
        Game game = gameRepository.findByName(gameMode)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        Score s = new Score();
        s.setUser(user);
        s.setGame(game);
        s.setScore(score);
        s.setPlayedAt(LocalDateTime.now());

        scoreRepository.save(s);
    }

    public Page<Score> findByUserIdPaginated(Long userId, Pageable pageable) {
        return scoreRepository.findByUserId(userId, pageable);
    }

    public List<Score> findTopScores(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "score"));
        return scoreRepository.findAll(pageable).getContent();
    }

    public List<Score> findTopScoresByGame(String gameName, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return scoreRepository.findByGame_NameOrderByScoreDesc(gameName, pageable);
    }

}
