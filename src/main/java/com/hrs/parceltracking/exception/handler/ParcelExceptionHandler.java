package com.hrs.parceltracking.exception.handler;

import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.dto.response.StandardResponse;
import com.hrs.parceltracking.exception.ParcelNotFoundException;
import com.hrs.parceltracking.exception.ParcelPickedUpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ParcelExceptionHandler {

    @ExceptionHandler(ParcelNotFoundException.class)
    public ResponseEntity<StandardResponse<Void>> handleParcelNotFound(ParcelNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                StandardResponse.<Void>builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message(MessageConstant.PARCEL_NOT_FOUND)
                        .error(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ParcelPickedUpException.class)
    public ResponseEntity<StandardResponse<Void>> handleParcelAlreadyPickedUp(ParcelPickedUpException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                StandardResponse.<Void>builder()
                        .statusCode(HttpStatus.CONFLICT.value())
                        .message(MessageConstant.PARCEL_ALREADY_PICKED_UP)
                        .error(ex.getMessage())
                        .build()
        );
    }
}
