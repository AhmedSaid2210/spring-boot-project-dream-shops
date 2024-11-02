package com.global.dreamshops.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String categoryNotFound) {
        super(categoryNotFound);
    }
}
