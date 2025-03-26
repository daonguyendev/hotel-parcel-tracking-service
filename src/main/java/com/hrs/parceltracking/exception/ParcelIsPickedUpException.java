package com.hrs.parceltracking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ParcelIsPickedUpException extends RuntimeException {
    public ParcelIsPickedUpException(String message) {
        super(message);
    }
}
