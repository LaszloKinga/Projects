package edu.bbte.idde.lkim2156.spring.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FastApiRequest {
    @JsonProperty("input_word")
    private String inputWord;

    @JsonProperty("previous_word")
    private String previousWord;

}
