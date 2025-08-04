package edu.bbte.idde.lkim2156.spring.controller;

import edu.bbte.idde.lkim2156.spring.dto.incoming.UserEntityInDto;
import edu.bbte.idde.lkim2156.spring.dto.incoming.UserEntityUpdateInDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.UserDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.UserEntityOutDto;
import edu.bbte.idde.lkim2156.spring.service.UserEntityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Collection<UserEntityOutDto>> getUserEntities() {
        log.info("Here the endpoint: GET /api/users");
        return new ResponseEntity<>(userEntityService.getUserEntities(), HttpStatus.OK);

    }

    private String extractAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof String email)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid authentication principal");
        }

        return email;
    }



    @GetMapping("/me")
    public UserEntityOutDto getAuthenticatedUser() {
        log.info("Here the endpoint: GET /api/users/me");

        String email = extractAuthenticatedEmail();
        log.info("Authenticated user's email: {}", email);

        UserDto userDto = userEntityService.findByEmail(email);
        log.info("userDto: {}", userDto);

        if (userDto == null) {
            log.error("User entity not found: {}", email);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User entity not found");
        }

        UserEntityOutDto userEntityOutDto = userEntityService.getUserEntityById(userDto.getId());
        log.info("userEntityOutDto: {}", userEntityOutDto);

        if (userEntityOutDto == null) {
            log.error("User entity not found for ID: {}", userDto.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User entity not found");
        }

        return userEntityOutDto;
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<UserEntityOutDto> getUserEntityById(@PathVariable Long id, @CookieValue("jwt") String jwt) {
        log.info("Here the endpoint: GET localhost:8080/api/users/" + id);
        log.info("GET request received for user ID: " + id);
        return new ResponseEntity<>(userEntityService.getUserEntityById(id), HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserEntityOutDto> createUserEntity(@RequestBody @Valid UserEntityInDto userEntityInDto) {

        log.info("Here the endpoint: POST localhost:8080/api/users");
        log.info("POST request received for user: " + userEntityInDto);
        UserEntityOutDto savedUserEntity = userEntityService.createUserEntity(userEntityInDto);
        return new ResponseEntity<>(savedUserEntity, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserEntityOutDto> updateUserEntity(@PathVariable Long id,
                                                             @RequestBody UserEntityUpdateInDto userRequestDTO) {
        log.info("Here the endpoint: PUT localhost:8080/api/users/" + id);
        log.info("PUT request received for user ID: {}", userRequestDTO);
        return new ResponseEntity<>(userEntityService.updateUserEntity(id, userRequestDTO), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserEntity(@PathVariable Long id) {
        log.info("Here the endpoint: DELETE localhost:8080/api/users/" + id);
        userEntityService.deleteUserEntity(id);
    }
}
