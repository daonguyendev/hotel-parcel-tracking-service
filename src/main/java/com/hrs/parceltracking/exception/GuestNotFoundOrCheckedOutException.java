package com.hrs.parceltracking.exception;

public class GuestNotFoundOrCheckedOutException extends RuntimeException {
    public GuestNotFoundOrCheckedOutException(String message) {
        super(message);
    }
}
