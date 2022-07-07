package com.rudnikov.solarlab.exception.confirmationtoken;

public class ConfirmationTokenAlreadyUsedException extends RuntimeException {

    public ConfirmationTokenAlreadyUsedException() {
        super();
    }

    public ConfirmationTokenAlreadyUsedException(String message) {
        super(message);
    }

    public ConfirmationTokenAlreadyUsedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfirmationTokenAlreadyUsedException(Throwable cause) {
        super(cause);
    }

    protected ConfirmationTokenAlreadyUsedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}