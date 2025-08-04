package edu.bbte.idde.lkim2156.spring.controller;

import edu.bbte.idde.lkim2156.spring.config.UserAuthenticationProvider;
import edu.bbte.idde.lkim2156.spring.dto.incoming.CredentialsDto;
import edu.bbte.idde.lkim2156.spring.dto.incoming.SignUpDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.UserDto;
import edu.bbte.idde.lkim2156.spring.service.UserEntityService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthController {

    private final UserEntityService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        log.info("/me endpoint access");
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("/me error: User is not authenticated.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        }

        Object principal = authentication.getPrincipal();
        String username = (String) authentication.getPrincipal();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        log.info("Authenticated user: {}", principal);
        log.info("User roles: {}", roles);
        return ResponseEntity.ok(Map.of(
                "username", username,
                "roles", roles
        ));
    }


    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto,
                                         HttpServletResponse response) {
        log.info("Login request received: " + credentialsDto);
        UserDto userDto = userService.login(credentialsDto);

        String token = userAuthenticationProvider.createToken(userDto);
        log.info("Generated JWT token: " + token);
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // fejlesztés alatt false, élesben true
        cookie.setPath("/");
        cookie.setMaxAge(86400); // 1 nap

        response.addCookie(cookie);

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user, HttpServletResponse response) {
        UserDto createdUser = userService.register(user);

        String token = userAuthenticationProvider.createToken(createdUser);

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(3600);

        response.addCookie(cookie);

        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, HttpServletRequest request) {
        log.info("Logout request received.");


        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                log.info("Received cookie: " + cookie.getName() + " = " + cookie.getValue());
            }
        } else {
            log.info("No cookies found in request.");
        }

        // Töröljük a sütit
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // fejlesztés alatt false, élesben true
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        log.info("JWT cookie deleted.");

        return ResponseEntity.ok().body("Logged out successfully");
    }
}