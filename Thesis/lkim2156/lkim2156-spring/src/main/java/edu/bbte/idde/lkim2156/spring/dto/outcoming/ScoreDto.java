package edu.bbte.idde.lkim2156.spring.dto.outcoming;


import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {
    private Long id;
    private String game;
    private int score;
    private LocalDateTime playedAt;
}