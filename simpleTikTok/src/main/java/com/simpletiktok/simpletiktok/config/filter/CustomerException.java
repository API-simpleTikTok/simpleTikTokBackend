package com.simpletiktok.simpletiktok.config.filter;

public class CustomerException extends RuntimeException {
    public CustomerException(String message) {
        super(message);
        System.out.println(message);
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}

