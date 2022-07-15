package com.rudnikov.solarlab.exception;

public class UnAuthorizedActionException extends RuntimeException {

    public UnAuthorizedActionException() {
        super();
    }

    public UnAuthorizedActionException(String message) {
        super(message);
    }

    public UnAuthorizedActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorizedActionException(Throwable cause) {
        super(cause);
    }

    protected UnAuthorizedActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}