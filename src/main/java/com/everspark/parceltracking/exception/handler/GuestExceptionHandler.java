package com.everspark.parceltracking.exception.handler;

import com.everspark.parceltracking.constant.MessageConstant;
import com.everspark.parceltracking.dto.response.StandardResponse;
import com.everspark.parceltracking.exception.GuestCheckedInException;
import com.everspark.parceltracking.exception.GuestCheckedOutException;
import com.everspark.parceltracking.exception.InvalidGuestInfoException;
import com.everspark.parceltracking.exception.GuestNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GuestExceptionHandler {

    @ExceptionHandler(GuestNotFoundException.class)
    public ResponseEntity<StandardResponse<Void>> handleGuestNotFoundException(
            GuestNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                StandardResponse.<Void>builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message(MessageConstant.GUEST_NOT_FOUND)
                        .error(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(InvalidGuestInfoException.class)
    public ResponseEntity<StandardResponse<Void>> handleGuestNotFoundException(
            InvalidGuestInfoException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                StandardResponse.<Void>builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(MessageConstant.INVALID_GUEST_INFO)
                        .error(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(GuestCheckedOutException.class)
    public ResponseEntity<StandardResponse<Void>> handleGuestAlreadyCheckedOutException(
            GuestCheckedOutException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                StandardResponse.<Void>builder()
                        .statusCode(HttpStatus.CONFLICT.value())
                        .message(MessageConstant.GUEST_ALREADY_CHECKED_OUT)
                        .error(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(GuestCheckedInException.class)
    public ResponseEntity<StandardResponse<Void>> handleGuestAlreadyCheckedInException(
            GuestCheckedInException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                StandardResponse.<Void>builder()
                        .statusCode(HttpStatus.CONFLICT.value())
                        .message(MessageConstant.GUEST_ALREADY_CHECKED_IN)
                        .error(ex.getMessage())
                        .build()
        );
    }
}
