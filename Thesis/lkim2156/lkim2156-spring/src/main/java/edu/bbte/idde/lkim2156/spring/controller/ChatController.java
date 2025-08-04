package edu.bbte.idde.lkim2156.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.lkim2156.spring.dto.common.FastApiRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/chat")
public class ChatController {


    @PostMapping("/message")
    @SuppressWarnings("PMD.LooseCoupling")
    public ResponseEntity<String> receiveMessage(@RequestBody Map<String, String> request,
                                                 HttpSession session) throws JsonProcessingException {
        String message = request.get("message");
        log.info("Received message: {}", message);

        String lastGeneratedWord = (String) session.getAttribute("lastGeneratedWord");
        if (lastGeneratedWord == null) {
            lastGeneratedWord = "";
        }


        List<String> usedWords = (List<String>) session.getAttribute("usedWords");
        if (usedWords == null) {
            usedWords = new ArrayList<>();
            session.setAttribute("usedWords", usedWords);
        }

        String normalizedMessage = message.toLowerCase(Locale.ENGLISH);

        if (usedWords.contains(normalizedMessage)) {
            session.setAttribute("lastGeneratedWord", null);
            session.setAttribute("usedWords", null);
            return ResponseEntity.badRequest().body("The word has already been used! Game over.");
        }

        usedWords.add(normalizedMessage);


        RestTemplate restTemplate = new RestTemplate();
        String fastApiUrl = "http://localhost:8000/generate";

        FastApiRequest requestPayload = new FastApiRequest(message, lastGeneratedWord);
        ObjectMapper mapper = new ObjectMapper();
        log.debug("Request payload: {}", mapper.writeValueAsString(requestPayload));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FastApiRequest> requestEntity = new HttpEntity<>(requestPayload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    fastApiUrl,
                    requestEntity,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();

            if ((Boolean) responseBody.get("valid")) {
                String generatedWord = (String) responseBody.get("generated_word");
                session.setAttribute("lastGeneratedWord", generatedWord);
                return ResponseEntity.ok(generatedWord);
            } else {
                session.setAttribute("lastGeneratedWord", null);
                return ResponseEntity.badRequest().body((String) responseBody.get("message"));
            }

        } catch (RestClientException e) {
            log.error("FastAPI communication failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while calling FastAPI.");
        }

    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetGame(HttpSession session) {
        log.info("Game reset");
        session.setAttribute("lastGeneratedWord", null);
        session.setAttribute("usedWords", null);
        return ResponseEntity.ok().build();
    }
}
