package com.everspark.parceltracking.exception.handler;

import com.everspark.parceltracking.constant.MessageConstant;
import com.everspark.parceltracking.dto.response.StandardResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GeneralExceptionHandlerTest {

    @InjectMocks
    private GeneralExceptionHandler generalExceptionHandler;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleValidationExceptions_ShouldReturnBadRequestResponse() {
        // Arrange
        String errorMessage = "Invalid input data";
        ObjectError objectError = new ObjectError("field", errorMessage);

        // Mock BindingResult return error list
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(objectError));

        // Create actual instance of MethodArgumentNotValidException
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<StandardResponse<Void>> response =
                generalExceptionHandler.handleValidationExceptions(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatusCode());
        Assertions.assertEquals(MessageConstant.VALIDATION_ERROR, response.getBody().getMessage());
        assertEquals(errorMessage, response.getBody().getError());

        // Verify method calls
        verify(bindingResult, times(1)).getAllErrors();
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerErrorResponse() {
        // Arrange
        String errorMessage = "Unexpected error occurred";
        Exception exception = new Exception(errorMessage);

        // Act
        ResponseEntity<StandardResponse<Void>> response = generalExceptionHandler.handleGenericException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatusCode());
        assertEquals(MessageConstant.INTERNAL_SERVER_ERROR, response.getBody().getMessage());
        assertEquals(errorMessage, response.getBody().getError());
    }
}
