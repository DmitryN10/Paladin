package com.netcracker.paladin.swing.exceptions;

/**
 * Created by ivan on 30.11.16.
 */
public class NoFileSelectedException extends IllegalStateException {
    public NoFileSelectedException() {
    }

    public NoFileSelectedException(String s) {
        super(s);
    }

    public NoFileSelectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFileSelectedException(Throwable cause) {
        super(cause);
    }
}
