package com.cavosh.cafebackend.global.domain.exception;

public class AlreadyExistsException extends DomainException {

    public AlreadyExistsException(String message) {
        super(message);
    }
}