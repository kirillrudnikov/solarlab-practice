package com.rudnikov.solarlab.exception.confirmationtoken;

public class ConfirmationTokenAlreadyExistsException extends RuntimeException {

    public ConfirmationTokenAlreadyExistsException() {
        super();
    }

    public ConfirmationTokenAlreadyExistsException(String message) {
        super(message);
    }

    public ConfirmationTokenAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfirmationTokenAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected ConfirmationTokenAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}