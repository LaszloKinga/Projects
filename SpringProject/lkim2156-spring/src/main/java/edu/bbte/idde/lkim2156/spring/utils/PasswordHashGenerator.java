package edu.bbte.idde.lkim2156.spring.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

// teszteleshez
// automatikus kommand ami lefut és kiírja az "admin" jelszót encodeolva

@Component
@Slf4j
public class PasswordHashGenerator implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("admin");

        log.info("Generated Hash: {}", hashedPassword);
    }
}

