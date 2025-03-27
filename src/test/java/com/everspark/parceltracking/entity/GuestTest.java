package com.everspark.parceltracking.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuestTest {

    @Test
    void testNoArgsConstructor_ShouldCreateDefaultGuest() {
        // Act
        Guest guest = new Guest();

        // Assert
        assertNotNull(guest);
        assertNull(guest.getId());
        assertNull(guest.getName());
        assertNull(guest.getRoomNumber());
        assertFalse(guest.isCheckedOut());
    }

    @Test
    void testAllArgsConstructor_ShouldCreateGuestWithValues() {
        // Arrange
        Long id = 1L;
        String name = "John Doe";
        String roomNumber = "101";
        boolean isCheckedOut = true;

        // Act
        Guest guest = new Guest(id, name, roomNumber, isCheckedOut);

        // Assert
        assertEquals(id, guest.getId());
        assertEquals(name, guest.getName());
        assertEquals(roomNumber, guest.getRoomNumber());
        assertTrue(guest.isCheckedOut());
    }

    @Test
    void testSettersAndGetters_ShouldUpdateAndRetrieveValues() {
        // Arrange
        Guest guest = new Guest();

        // Act
        guest.setId(2L);
        guest.setName("Alice");
        guest.setRoomNumber("202");
        guest.setCheckedOut(true);

        // Assert
        assertEquals(2L, guest.getId());
        assertEquals("Alice", guest.getName());
        assertEquals("202", guest.getRoomNumber());
        assertTrue(guest.isCheckedOut());
    }
}

