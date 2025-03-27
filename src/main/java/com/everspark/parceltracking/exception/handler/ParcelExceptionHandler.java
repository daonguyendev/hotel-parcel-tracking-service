package com.everspark.parceltracking.exception.handler;

import com.everspark.parceltracking.constant.MessageConstant;
import com.everspark.parceltracking.dto.response.StandardResponse;
import com.everspark.parceltracking.exception.ParcelNotFoundException;
import com.everspark.parceltracking.exception.AllParcelPickedUpException;
import com.everspark.parceltracking.exception.ParcelPickedUpException;
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
    public ResponseEntity<StandardResponse<Void>> handleAParcelAlreadyPickedUp(ParcelPickedUpException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                StandardResponse.<Void>builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(MessageConstant.PARCEL_ALREADY_PICKED_UP)
                        .error(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AllParcelPickedUpException.class)
    public ResponseEntity<StandardResponse<Void>> handleAllParcelAlreadyPickedUp(AllParcelPickedUpException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                StandardResponse.<Void>builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(MessageConstant.ALL_PARCEL_ALREADY_PICKED_UP)
                        .error(ex.getMessage())
                        .build()
        );
    }
}
