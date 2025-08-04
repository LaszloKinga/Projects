package edu.bbte.idde.lkim2156.spring.init;

import edu.bbte.idde.lkim2156.spring.model.Game;
import edu.bbte.idde.lkim2156.spring.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameInitializer implements CommandLineRunner {

    private final GameRepository gameRepository;

    @Override
    public void run(String... args) {
        createIfNotExists("WORD_COUNT");
        createIfNotExists("LETTER_COUNT");
    }

    private void createIfNotExists(String name) {
        if (gameRepository.findByName(name).isEmpty()) {
            gameRepository.save(new Game(name, null));
        }
    }
}
