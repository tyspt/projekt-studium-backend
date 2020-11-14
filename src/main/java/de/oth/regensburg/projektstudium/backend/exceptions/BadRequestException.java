package de.oth.regensburg.projektstudium.backend.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super("Invalid Request Body");
    }

    public BadRequestException(String extraMsg) {
        super("Invalid Request Body: " + extraMsg);
    }
}
