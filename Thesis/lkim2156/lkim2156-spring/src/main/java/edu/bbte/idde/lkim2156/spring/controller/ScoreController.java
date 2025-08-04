package edu.bbte.idde.lkim2156.spring.controller;

import edu.bbte.idde.lkim2156.spring.dto.incoming.ScoreRequest;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.LeaderboardScoreDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.ScoreDto;
import edu.bbte.idde.lkim2156.spring.model.Score;
import edu.bbte.idde.lkim2156.spring.model.UserEntity;
import edu.bbte.idde.lkim2156.spring.service.ScoreService;
import edu.bbte.idde.lkim2156.spring.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;
    private final UserEntityService userEntityService;

    @PostMapping
    public ResponseEntity<Void> saveScore(
            @RequestBody ScoreRequest scoreRequest,
            Authentication authentication) {


        String email = (String) authentication.getPrincipal();

        Optional<UserEntity> optionalUser = Optional.ofNullable(userEntityService.findByEmailUserEntity(email));

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UserEntity user = optionalUser.get();
        scoreService.saveScore(user, scoreRequest.getScore(), scoreRequest.getGameMode());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public Page<ScoreDto> getUserScoresPaginated(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Score> scorePage = scoreService.findByUserIdPaginated(userId, PageRequest.of(page, size));

        return scorePage.map(score -> new ScoreDto(
                score.getId(),
                score.getGame().getName(),
                score.getScore(),
                score.getPlayedAt()
        ));
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardScoreDto> getLeaderboard(@RequestParam(defaultValue = "5") int limit,
                                                    @RequestParam(required = false) String gameName) {
        List<Score> topScores;
        if (gameName != null) {
            topScores = scoreService.findTopScoresByGame(gameName, limit);
        } else {
            topScores = scoreService.findTopScores(limit);
        }

        return topScores.stream()
                .map(score -> new LeaderboardScoreDto(
                        score.getId(),
                        score.getUser().getUserName(),
                        score.getGame().getName(),
                        score.getScore()
                ))
                .collect(Collectors.toList());
    }


}
