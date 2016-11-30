package com.netcracker.paladin.infrastructure.repositories.exceptions;

/**
 * Created by ivan on 30.11.16.
 */
public class NoSavedConfigPropertiesException extends Exception {
    public NoSavedConfigPropertiesException() {
    }

    public NoSavedConfigPropertiesException(String s) {
        super(s);
    }

    public NoSavedConfigPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSavedConfigPropertiesException(Throwable cause) {
        super(cause);
    }
}
