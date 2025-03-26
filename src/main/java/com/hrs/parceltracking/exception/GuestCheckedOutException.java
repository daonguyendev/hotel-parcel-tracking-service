package com.hrs.parceltracking.exception;

public class GuestCheckedOutException extends RuntimeException {
    public GuestCheckedOutException(String message) {
        super(message);
    }
}
