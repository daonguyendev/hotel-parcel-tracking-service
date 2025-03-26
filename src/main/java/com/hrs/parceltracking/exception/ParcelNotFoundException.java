package com.hrs.parceltracking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ParcelNotFoundException extends RuntimeException {
    public ParcelNotFoundException(String message) {
        super(message);
    }
}
