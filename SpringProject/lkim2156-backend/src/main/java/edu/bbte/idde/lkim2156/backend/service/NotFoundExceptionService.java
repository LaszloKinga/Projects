package edu.bbte.idde.lkim2156.backend.service;

import edu.bbte.idde.lkim2156.backend.dao.NotFoundException;

public class NotFoundExceptionService extends NotFoundException {
    public NotFoundExceptionService() {
        super();
    }

    public NotFoundExceptionService(String message) {
        super(message);
    }

    public NotFoundExceptionService(String message, Throwable cause) {
        super(message, cause);
    }
}
