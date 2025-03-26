package com.hrs.parceltracking.exception.handler;

import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.exception.GuestNotFoundOrCheckedOutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GuestExceptionHandler {

    @ExceptionHandler(GuestNotFoundOrCheckedOutException.class)
    public ResponseEntity<Map<String, String>> handleGuestNotFoundException(GuestNotFoundOrCheckedOutException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(MessageConstant.EXCEPTION_MESSAGE_KEY, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
