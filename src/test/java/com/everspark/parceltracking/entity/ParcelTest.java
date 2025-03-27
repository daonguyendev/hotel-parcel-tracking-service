package com.everspark.parceltracking.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParcelTest {

    @Test
    void testNoArgsConstructor_ShouldCreateDefaultParcel() {
        // Act
        Parcel parcel = new Parcel();

        // Assert
        assertNotNull(parcel);
        assertNull(parcel.getId());
        assertNull(parcel.getTrackingNumber());
        assertNull(parcel.getRecipientName());
        assertNull(parcel.getGuest());
        assertFalse(parcel.isPickedUp());
    }

    @Test
    void testAllArgsConstructor_ShouldCreateParcelWithValues() {
        // Arrange
        Long id = 1L;
        String trackingNumber = "TN123456";
        String recipientName = "John Doe";
        Guest guest = new Guest(2L, "Alice", "202", false);
        boolean isPickedUp = true;

        // Act
        Parcel parcel = new Parcel(id, trackingNumber, recipientName, guest, isPickedUp);

        // Assert
        assertEquals(id, parcel.getId());
        assertEquals(trackingNumber, parcel.getTrackingNumber());
        assertEquals(recipientName, parcel.getRecipientName());
        assertEquals(guest, parcel.getGuest());
        assertTrue(parcel.isPickedUp());
    }

    @Test
    void testSettersAndGetters_ShouldUpdateAndRetrieveValues() {
        // Arrange
        Parcel parcel = new Parcel();
        Guest guest = new Guest(3L, "Bob", "303", true);

        // Act
        parcel.setId(5L);
        parcel.setTrackingNumber("TN987654");
        parcel.setRecipientName("Charlie");
        parcel.setGuest(guest);
        parcel.setPickedUp(true);

        // Assert
        assertEquals(5L, parcel.getId());
        assertEquals("TN987654", parcel.getTrackingNumber());
        assertEquals("Charlie", parcel.getRecipientName());
        assertEquals(guest, parcel.getGuest());
        assertTrue(parcel.isPickedUp());
    }
}

