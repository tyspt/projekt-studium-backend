package de.oth.regensburg.projektstudium.backend.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Class cls) {
        super("Could not find " + cls.getSimpleName());
    }
}
