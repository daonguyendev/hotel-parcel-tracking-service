package com.hrs.parceltracking.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StandardResponseTest {

    @Test
    void testNoArgsConstructor_ShouldCreateDefaultObject() {
        // Act
        StandardResponse<String> response = new StandardResponse<>();

        // Assert
        assertNotNull(response);
        assertEquals(0, response.getStatusCode());
        assertNull(response.getMessage());
        assertNull(response.getData());
        assertNull(response.getError());
    }

    @Test
    void testAllArgsConstructor_ShouldCreateObjectWithValues() {
        // Arrange
        int statusCode = 200;
        String message = "Success";
        String data = "Test Data";
        String error = null;

        // Act
        StandardResponse<String> response = new StandardResponse<>(statusCode, message, data, error);

        // Assert
        assertEquals(statusCode, response.getStatusCode());
        assertEquals(message, response.getMessage());
        assertEquals(data, response.getData());
        assertNull(response.getError());
    }

    @Test
    void testSettersAndGetters_ShouldUpdateAndRetrieveValues() {
        // Arrange
        StandardResponse<Integer> response = new StandardResponse<>();

        // Act
        response.setStatusCode(400);
        response.setMessage("Bad Request");
        response.setData(123);
        response.setError("Invalid Input");

        // Assert
        assertEquals(400, response.getStatusCode());
        assertEquals("Bad Request", response.getMessage());
        assertEquals(123, response.getData());
        assertEquals("Invalid Input", response.getError());
    }

    @Test
    void testBuilder_ShouldCreateObjectWithValues() {
        // Act
        StandardResponse<Double> response = StandardResponse.<Double>builder()
                .statusCode(201)
                .message("Created")
                .data(99.99)
                .build();

        // Assert
        assertEquals(201, response.getStatusCode());
        assertEquals("Created", response.getMessage());
        assertEquals(99.99, response.getData());
        assertNull(response.getError()); // default is null
    }

    @Test
    void testBuilderPattern() {
        // Act
        StandardResponse<String> response = StandardResponse.<String>builder()
                .statusCode(200)
                .message("Success")
                .data("Test Data")
                .error("No error")
                .build();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals("Success", response.getMessage());
        assertEquals("Test Data", response.getData());
        assertEquals("No error", response.getError());
    }

    @Test
    void testBuilderDefaultValues() {
        // Act
        StandardResponse<String> response = StandardResponse.<String>builder().build();

        // Assert
        assertNotNull(response);
        assertEquals(0, response.getStatusCode());  // Giá trị mặc định của int là 0
        assertNull(response.getMessage());
        assertNull(response.getData());
        assertNull(response.getError());
    }

    @Test
    void testToString_ShouldReturnProperStringFormat() {
        // Arrange
        StandardResponse<String> response = new StandardResponse<>(
                200, "OK", "Test Data", "Some Error");

        // Act
        String result = response.toString(); // invoked toString()

        // Assert
        assertNotNull(result);
        assertEquals("StandardResponse{statusCode=200, message='OK', data=Test Data, error='Some Error'}", result);
    }

    @Test
    void testErrorSetter_ShouldSetAndRetrieveError() {
        // Arrange
        StandardResponse<String> response = new StandardResponse<>();

        // Act
        response.setError("Internal Server Error"); // invoked error()

        // Assert
        assertEquals("Internal Server Error", response.getError());
    }
}
