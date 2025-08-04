package edu.bbte.idde.lkim2156.spring.controller;

import edu.bbte.idde.lkim2156.spring.dto.outcoming.UserOutDto;
import edu.bbte.idde.lkim2156.spring.model.UserEntity;
import edu.bbte.idde.lkim2156.spring.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserEntityService userEntityService;

    @GetMapping("/users")
    public ResponseEntity<List<UserOutDto>> getAllUsers() {

        return new ResponseEntity<>(userEntityService.getAll(), HttpStatus.OK);

    }


    @GetMapping("/hello")
    public String hello() {
        return "Hello, user!";
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserOutDto> registerUser(@RequestBody UserEntity user) {

        return new ResponseEntity<>(userEntityService.createUser(user), HttpStatus.CREATED);
    }

}
