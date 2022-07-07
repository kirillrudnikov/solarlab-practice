package com.rudnikov.solarlab.exception.advert;

public class AdvertAlreadyExistsException extends RuntimeException {

    public AdvertAlreadyExistsException() {
        super();
    }

    public AdvertAlreadyExistsException(String message) {
        super(message);
    }

    public AdvertAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdvertAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected AdvertAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}