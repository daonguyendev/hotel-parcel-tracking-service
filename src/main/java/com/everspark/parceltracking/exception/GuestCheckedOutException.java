package com.everspark.parceltracking.exception;

public class GuestCheckedOutException extends RuntimeException {
    public GuestCheckedOutException(String message) {
        super(message);
    }
}
