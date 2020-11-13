package de.oth.regensburg.projektstudium.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GoneAdvice {
    @ResponseBody
    @ExceptionHandler(GoneException.class)
    @ResponseStatus(HttpStatus.GONE)
    String invalidRequestHandler(GoneException ex) {
        return ex.getMessage();
    }
}
