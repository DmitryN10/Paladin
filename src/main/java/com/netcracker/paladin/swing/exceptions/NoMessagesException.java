package com.netcracker.paladin.swing.exceptions;

/**
 * Created by ivan on 29.11.16.
 */
public class NoMessagesException extends IllegalStateException {
    public NoMessagesException() {
    }

    public NoMessagesException(String s) {
        super(s);
    }

    public NoMessagesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMessagesException(Throwable cause) {
        super(cause);
    }
}
