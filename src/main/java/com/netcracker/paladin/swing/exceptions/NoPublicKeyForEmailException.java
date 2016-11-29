package com.netcracker.paladin.swing.exceptions;

/**
 * Created by ivan on 29.11.16.
 */
public class NoPublicKeyForEmailException extends IllegalStateException {
    public NoPublicKeyForEmailException() {
    }

    public NoPublicKeyForEmailException(String message) {
        super(message);
    }

    public NoPublicKeyForEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPublicKeyForEmailException(Throwable cause) {
        super(cause);
    }
}
