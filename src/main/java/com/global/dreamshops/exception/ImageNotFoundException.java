package com.global.dreamshops.exception;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String imageNotFound) {
        super(imageNotFound);
    }
}
