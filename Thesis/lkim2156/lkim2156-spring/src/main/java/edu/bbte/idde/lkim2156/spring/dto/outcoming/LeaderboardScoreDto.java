package edu.bbte.idde.lkim2156.spring.dto.outcoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderboardScoreDto {
    private Long id;
    private String userName;
    private String gameName;
    private int score;
}

