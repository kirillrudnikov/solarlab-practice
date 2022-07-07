package com.rudnikov.solarlab.exception.confirmationtoken;

public class ConfirmationTokenExpiredException extends RuntimeException {

    public ConfirmationTokenExpiredException() {
        super();
    }

    public ConfirmationTokenExpiredException(String message) {
        super(message);
    }

    public ConfirmationTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfirmationTokenExpiredException(Throwable cause) {
        super(cause);
    }

    protected ConfirmationTokenExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}