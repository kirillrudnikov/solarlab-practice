package com.rudnikov.solarlab.exception.comment;

public class CommentAlreadyExistsException extends RuntimeException {

    public CommentAlreadyExistsException() {
        super();
    }

    public CommentAlreadyExistsException(String message) {
        super(message);
    }

    public CommentAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected CommentAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}