package com.global.dreamshops.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String categoryNotFound) {
        super(categoryNotFound);
    }
}
