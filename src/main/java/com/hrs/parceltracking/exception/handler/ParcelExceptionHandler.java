package com.hrs.parceltracking.exception.handler;

import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.exception.ParcelAlreadyPickedUpException;
import com.hrs.parceltracking.exception.ParcelIsPickedUpException;
import com.hrs.parceltracking.exception.ParcelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ParcelExceptionHandler {

    @ExceptionHandler(ParcelAlreadyPickedUpException.class)
    public ResponseEntity<Map<String, String>> handleParcelAlreadyPickedUpException(
            ParcelAlreadyPickedUpException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(MessageConstant.EXCEPTION_MESSAGE_KEY, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ParcelNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleParcelNotFoundException(ParcelNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(MessageConstant.EXCEPTION_MESSAGE_KEY, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ParcelIsPickedUpException.class)
    public ResponseEntity<Map<String, String>> handleParcelIsPickedUpException(ParcelIsPickedUpException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(MessageConstant.EXCEPTION_MESSAGE_KEY, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
