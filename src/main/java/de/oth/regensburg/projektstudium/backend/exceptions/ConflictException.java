package de.oth.regensburg.projektstudium.backend.exceptions;

public class ConflictException extends RuntimeException {

    public ConflictException() {
    }

    public ConflictException(String message) {
        super(message);
    }
}