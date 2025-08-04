package edu.bbte.idde.lkim2156.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WeshopNotFoundException extends RuntimeException {
    public WeshopNotFoundException(String message) {
        super(message);
    }
}

