package com.dh.Clinica.exception;

public class BadRequestException extends RuntimeException  {
    public BadRequestException(String message) {
        super(message);
    }

}
