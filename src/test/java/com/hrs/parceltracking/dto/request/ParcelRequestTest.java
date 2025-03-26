package com.hrs.parceltracking.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParcelRequestTest {

    @Test
    void parcelRequest_NoArgsConstructor_ShouldCreateEmptyObject() {
        ParcelRequest parcelRequest = new ParcelRequest();
        assertNotNull(parcelRequest);
    }

    @Test
    void parcelRequest_AllArgsConstructor_ShouldSetAllFieldsCorrectly() {
        ParcelRequest parcelRequest = new ParcelRequest("PKG123", "John Doe", "101");

        assertEquals("PKG123", parcelRequest.getTrackingNumber());
        assertEquals("John Doe", parcelRequest.getRecipientName());
        assertEquals("101", parcelRequest.getRoomNumber());
    }

    @Test
    void parcelRequest_Setters_ShouldUpdateFieldsCorrectly() {
        ParcelRequest parcelRequest = new ParcelRequest();
        parcelRequest.setTrackingNumber("PKG456");
        parcelRequest.setRecipientName("Alice Smith");
        parcelRequest.setRoomNumber("202");

        assertEquals("PKG456", parcelRequest.getTrackingNumber());
        assertEquals("Alice Smith", parcelRequest.getRecipientName());
        assertEquals("202", parcelRequest.getRoomNumber());
    }
}
