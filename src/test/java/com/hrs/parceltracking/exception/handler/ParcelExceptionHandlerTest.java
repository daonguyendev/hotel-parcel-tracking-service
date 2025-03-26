package com.hrs.parceltracking.exception.handler;

import com.hrs.parceltracking.dto.response.StandardResponse;
import com.hrs.parceltracking.exception.ParcelNotFoundException;
import com.hrs.parceltracking.exception.ParcelPickedUpException;
import com.hrs.parceltracking.constant.MessageConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParcelExceptionHandlerTest {

    @InjectMocks
    private ParcelExceptionHandler parcelExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleParcelNotFound_ShouldReturnNotFoundResponse() {
        // Arrange
        String errorMessage = "Parcel not found!";
        ParcelNotFoundException exception = new ParcelNotFoundException(errorMessage);

        // Act
        ResponseEntity<StandardResponse<Void>> response = parcelExceptionHandler.handleParcelNotFound(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatusCode());
        assertEquals(MessageConstant.PARCEL_NOT_FOUND, response.getBody().getMessage());
        assertEquals(errorMessage, response.getBody().getError());
    }

    @Test
    void handleParcelAlreadyPickedUp_ShouldReturnConflictResponse() {
        // Arrange
        String errorMessage = "Parcel already picked up!";
        ParcelPickedUpException exception = new ParcelPickedUpException(errorMessage);

        // Act
        ResponseEntity<StandardResponse<Void>> response = parcelExceptionHandler.handleParcelAlreadyPickedUp(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatusCode());
        assertEquals(MessageConstant.PARCEL_ALREADY_PICKED_UP, response.getBody().getMessage());
        assertEquals(errorMessage, response.getBody().getError());
    }
}
