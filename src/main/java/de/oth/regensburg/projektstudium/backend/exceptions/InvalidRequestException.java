package de.oth.regensburg.projektstudium.backend.exceptions;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException() {
        super("Invalid Request Body");
    }

    public InvalidRequestException(String extraMsg) {
        super("Invalid Request Body: " + extraMsg);
    }
}
