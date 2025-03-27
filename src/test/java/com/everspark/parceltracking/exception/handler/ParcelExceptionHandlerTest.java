package com.everspark.parceltracking.exception.handler;

import com.everspark.parceltracking.constant.MessageConstant;
import com.everspark.parceltracking.dto.response.StandardResponse;
import com.everspark.parceltracking.exception.ParcelNotFoundException;
import com.everspark.parceltracking.exception.AllParcelPickedUpException;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(MessageConstant.PARCEL_NOT_FOUND, response.getBody().getMessage());
        assertEquals(errorMessage, response.getBody().getError());
    }

    @Test
    void handleParcelAlreadyPickedUp_ShouldReturnConflictResponse() {
        // Arrange
        String errorMessage = "All parcel already picked up!";
        AllParcelPickedUpException exception = new AllParcelPickedUpException(errorMessage);

        // Act
        ResponseEntity<StandardResponse<Void>> response = parcelExceptionHandler.handleAllParcelAlreadyPickedUp(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatusCode());
        assertEquals(MessageConstant.ALL_PARCEL_ALREADY_PICKED_UP, response.getBody().getMessage());
        assertEquals(errorMessage, response.getBody().getError());
    }
}
