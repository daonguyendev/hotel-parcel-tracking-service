package com.everspark.parceltracking.service;

import com.everspark.parceltracking.entity.Guest;
import com.everspark.parceltracking.exception.GuestCheckedInException;
import com.everspark.parceltracking.exception.GuestCheckedOutException;
import com.everspark.parceltracking.exception.GuestNotFoundException;
import com.everspark.parceltracking.exception.InvalidGuestInfoException;
import com.everspark.parceltracking.repository.GuestRepository;
import com.everspark.parceltracking.service.impl.GuestServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GuestServiceImplTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestServiceImpl guestService;

    @Test
    void checkIn_ShouldSucceed_WhenGuestHasCheckedOutBefore() {
        // Given
        Guest guest = new Guest(1L, "John Doe", "101", true);

        when(guestRepository.findByNameAndRoomNumber("John Doe", "101"))
                .thenReturn(Optional.of(guest));
        when(guestRepository.save(any(Guest.class))).thenReturn(guest);

        // When
        Guest checkedInGuest = guestService.checkIn(guest);

        // Then
        assertFalse(checkedInGuest.isCheckedOut());
        verify(guestRepository).save(guest);
    }

    @Test
    void checkIn_ShouldThrowInvalidGuestInfoException_WhenGuestDoesNotExist() {
        // Given
        Guest guest = new Guest(2L, "Jane Doe", "102", true);

        when(guestRepository.findByNameAndRoomNumber("Jane Doe", "102"))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(InvalidGuestInfoException.class, () -> guestService.checkIn(guest));
        verify(guestRepository, never()).save(any());
    }

    @Test
    void checkIn_ShouldThrowGuestCheckedInException_WhenGuestIsAlreadyCheckedIn() {
        // Given
        Guest guest = new Guest(3L, "Alice Brown", "103", false);

        when(guestRepository.findByNameAndRoomNumber("Alice Brown", "103"))
                .thenReturn(Optional.of(guest));

        // When & Then
        assertThrows(GuestCheckedInException.class, () -> guestService.checkIn(guest));
        verify(guestRepository, never()).save(any());
    }

    @Test
    void checkOut_ShouldSucceed_WhenGuestIsCheckedIn() {
        // Given
        Long guestId = 4L;
        Guest guest = new Guest(guestId, "Mark Smith", "104", false);

        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));

        // When
        guestService.checkOut(guestId);

        // Then
        assertTrue(guest.isCheckedOut());
        verify(guestRepository).save(guest);
    }

    @Test
    void checkOut_ShouldThrowGuestNotFoundException_WhenGuestDoesNotExist() {
        // Given
        Long guestId = 99L;
        when(guestRepository.findById(guestId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(GuestNotFoundException.class, () -> guestService.checkOut(guestId));
        verify(guestRepository, never()).save(any());
    }

    @Test
    void checkOut_ShouldThrowGuestCheckedOutException_WhenGuestIsAlreadyCheckedOut() {
        // Given
        Long guestId = 5L;
        Guest guest = new Guest(guestId, "David Johnson", "105", true);

        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));

        // When & Then
        assertThrows(GuestCheckedOutException.class, () -> guestService.checkOut(guestId));
        verify(guestRepository, never()).save(any());
    }

    @Test
    void getCheckedInGuests_ShouldReturnEmptyPage_WhenNoGuestsAreCheckedIn() {
        // Given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        Page<Guest> emptyPage = Page.empty();

        when(guestRepository.findByIsCheckedOutFalse(pageable)).thenReturn(emptyPage);

        // When
        Page<Guest> result = guestService.getCheckedInGuests(0, 10, "name,asc");

        // Then
        assertTrue(result.isEmpty());
        verify(guestRepository).findByIsCheckedOutFalse(pageable);
    }
}