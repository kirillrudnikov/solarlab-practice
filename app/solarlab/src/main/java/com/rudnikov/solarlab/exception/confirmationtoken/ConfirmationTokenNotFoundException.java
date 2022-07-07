package com.rudnikov.solarlab.exception.confirmationtoken;

public class ConfirmationTokenNotFoundException extends RuntimeException {

    public ConfirmationTokenNotFoundException() {
        super();
    }

    public ConfirmationTokenNotFoundException(String message) {
        super(message);
    }

    public ConfirmationTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfirmationTokenNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ConfirmationTokenNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}