package com.hrs.parceltracking.exception.handler;

import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.dto.response.StandardResponse;
import com.hrs.parceltracking.exception.GuestCheckedInException;
import com.hrs.parceltracking.exception.GuestCheckedOutException;
import com.hrs.parceltracking.exception.GuestNotFoundException;
import com.hrs.parceltracking.exception.InvalidGuestInfoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GuestExceptionHandlerTest {

    @InjectMocks
    private GuestExceptionHandler guestExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleGuestNotFoundException_ShouldReturnNotFoundResponse() {
        // Arrange
        String errorMessage = "Guest not found!";
        GuestNotFoundException exception = new GuestNotFoundException(errorMessage);

        // Act
        ResponseEntity<StandardResponse<Void>> response = guestExceptionHandler.handleGuestNotFoundException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatusCode());
        assertEquals(MessageConstant.GUEST_NOT_FOUND, response.getBody().getMessage());
        assertEquals(errorMessage, response.getBody().getError());
    }

    @Test
    void handleInvalidGuestInfoException_ShouldReturnBadRequestResponse() {
        // Arrange
        String errorMessage = "Invalid guest information!";
        InvalidGuestInfoException exception = new InvalidGuestInfoException(errorMessage);

        // Act
        ResponseEntity<StandardResponse<Void>> response = guestExceptionHandler.handleGuestNotFoundException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatusCode());
        assertEquals(MessageConstant.INVALID_GUEST_INFO, response.getBody().getMessage());
        assertEquals(errorMessage, response.getBody().getError());
    }

    @Test
    void handleGuestAlreadyCheckedOutException_ShouldReturnConflictResponse() {
        // Arrange
        String errorMessage = "Guest already checked out!";
        GuestCheckedOutException exception = new GuestCheckedOutException(errorMessage);

        // Act
        ResponseEntity<StandardResponse<Void>> response = guestExceptionHandler.handleGuestAlreadyCheckedOutException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatusCode());
        assertEquals(MessageConstant.GUEST_ALREADY_CHECKED_OUT, response.getBody().getMessage());
        assertEquals(errorMessage, response.getBody().getError());
    }

    @Test
    void handleGuestAlreadyCheckedInException_ShouldReturnConflictResponse() {
        // Arrange
        String errorMessage = "Guest already checked in!";
        GuestCheckedInException exception = new GuestCheckedInException(errorMessage);

        // Act
        ResponseEntity<StandardResponse<Void>> response = guestExceptionHandler.handleGuestAlreadyCheckedInException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatusCode());
        assertEquals(MessageConstant.GUEST_ALREADY_CHECKED_IN, response.getBody().getMessage());
        assertEquals(errorMessage, response.getBody().getError());
    }
}
