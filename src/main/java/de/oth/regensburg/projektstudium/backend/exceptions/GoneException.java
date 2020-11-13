package de.oth.regensburg.projektstudium.backend.exceptions;

public class GoneException extends RuntimeException {

    public GoneException() {
    }

    public GoneException(String message) {
        super(message);
    }
}