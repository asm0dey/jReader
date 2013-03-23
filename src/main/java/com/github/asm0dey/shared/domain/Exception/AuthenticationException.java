package com.github.asm0dey.shared.domain.Exception;

/**
 * User: finkel
 * <p/>
 * Date: 22.03.13
 * <p/>
 * Time: 15:17
 */
public class AuthenticationException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public AuthenticationException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public AuthenticationException() {
        super();
    }
}
