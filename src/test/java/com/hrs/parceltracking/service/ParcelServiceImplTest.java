package com.hrs.parceltracking.service;

import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.dto.request.ParcelRequest;
import com.hrs.parceltracking.entity.Guest;
import com.hrs.parceltracking.entity.Parcel;
import com.hrs.parceltracking.exception.GuestCheckedInException;
import com.hrs.parceltracking.exception.GuestNotFoundException;
import com.hrs.parceltracking.exception.InvalidGuestInfoException;
import com.hrs.parceltracking.exception.ParcelIsPickedUpException;
import com.hrs.parceltracking.exception.ParcelNotFoundException;
import com.hrs.parceltracking.exception.ParcelPickedUpException;
import com.hrs.parceltracking.repository.GuestRepository;
import com.hrs.parceltracking.repository.ParcelRepository;
import com.hrs.parceltracking.service.impl.ParcelServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParcelServiceImplTest {

    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private ParcelServiceImpl parcelService;

    @Test
    void receiveParcel_Success() {
        // Given
        ParcelRequest request = new ParcelRequest("PKG001", "John Doe", "101");
        Guest guest = new Guest(1L, "John Doe", "101", false);
        Parcel parcel = new Parcel(1L, "PKG001", "John Doe", guest, false);

        // When
        when(guestRepository.findByNameAndRoomNumberAndIsCheckedOutFalse("John Doe", "101"))
                .thenReturn(Optional.of(guest));
        when(parcelRepository.findByTrackingNumber("PKG001"))
                .thenReturn(Optional.of(parcel));
        parcelService.receiveParcel(request);

        // Then
        assertTrue(parcel.isPickedUp());
        verify(parcelRepository, times(1)).save(parcel);
    }

    @Test
    void receiveParcel_ShouldThrowInvalidGuestInfoException_WhenGuestNotFound() {
        // Given
        ParcelRequest request = new ParcelRequest("PKG001", "John Doe", "101");
        when(guestRepository.findByNameAndRoomNumberAndIsCheckedOutFalse(any(), any()))
                            .thenReturn(Optional.empty());

        // When
        InvalidGuestInfoException thrown = assertThrows(InvalidGuestInfoException.class, () -> {
            parcelService.receiveParcel(request);
        });

        // Then
        assertEquals(MessageConstant.INVALID_GUEST_INFO, thrown.getMessage());
        verify(guestRepository, times(1))
                .findByNameAndRoomNumberAndIsCheckedOutFalse(any(), any());
        verify(parcelRepository, never()).findByTrackingNumber(anyString());
        verify(parcelRepository, never()).save(any());
    }

    @Test
    void receiveParcel_ShouldThrowParcelNotFoundException_WhenParcelNotFound() {
        // Given
        ParcelRequest request = new ParcelRequest("PKG001", "John Doe", "101");
        Guest guest = new Guest(1L, "John Doe", "101", false);

        when(guestRepository.findByNameAndRoomNumberAndIsCheckedOutFalse("John Doe", "101"))
                .thenReturn(Optional.of(guest));
        when(parcelRepository.findByTrackingNumber("PKG001"))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(ParcelNotFoundException.class, () -> parcelService.receiveParcel(request));
        verify(parcelRepository, never()).save(any());
    }

    @Test
    void receiveParcel_ShouldThrowParcelIsPickedUpException_WhenParcelAlreadyPickedUp() {
        // Given
        ParcelRequest request = new ParcelRequest("PKG001", "John Doe", "101");
        Guest guest = new Guest(1L, "John Doe", "101", false);
        Parcel parcel = new Parcel(1L, "PKG001", "John Doe", guest, true);

        // When
        when(guestRepository.findByNameAndRoomNumberAndIsCheckedOutFalse("John Doe", "101"))
                .thenReturn(Optional.of(guest));
        when(parcelRepository.findByTrackingNumber("PKG001"))
                .thenReturn(Optional.of(parcel));

        // Then
        assertThrows(ParcelIsPickedUpException.class, () -> parcelService.receiveParcel(request));
        verify(parcelRepository, never()).save(any());
    }

    @Test
    void getParcelsForGuest_ShouldReturnParcels_WhenGuestExistsAndCheckedOut() {
        // Given
        Long guestId = 1L;
        int page = 0, size = 10;
        String sortBy = "id,asc";
        Guest guest = new Guest(guestId, "John Doe", "101", true);
        Parcel parcel = new Parcel(1L, "PKG001", "John Doe", guest, false);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        List<Parcel> parcels = List.of(parcel);
        Page<Parcel> parcelPage = new PageImpl<>(parcels);

        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));
        when(parcelRepository.findByGuestIdAndIsPickedUpFalse(guestId, pageable)).thenReturn(parcelPage);

        // When
        Page<Parcel> result = parcelService.getParcelsForGuest(guestId, page, size, sortBy);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(parcelRepository).findByGuestIdAndIsPickedUpFalse(guestId, pageable);
    }

    @Test
    void getParcelsForGuest_ShouldThrowGuestNotFoundException_WhenGuestDoesNotExist() {
        // Given
        Long guestId = 2L;
        int page = 0, size = 10;
        String sortBy = "id,asc";

        when(guestRepository.findById(guestId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(GuestNotFoundException.class, () -> parcelService.getParcelsForGuest(guestId, page, size, sortBy));
        verify(parcelRepository, never()).findByGuestIdAndIsPickedUpFalse(anyLong(), any());
    }

    @Test
    void getParcelsForGuest_ShouldThrowGuestCheckedInException_WhenGuestIsNotCheckedOut() {
        // Given
        Long guestId = 3L;
        int page = 0, size = 10;
        String sortBy = "id,asc";
        Guest guest = new Guest(guestId, "Jane Doe", "102", false);

        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));

        // When & Then
        assertThrows(GuestCheckedInException.class, () -> parcelService.getParcelsForGuest(guestId, page, size, sortBy));
        verify(parcelRepository, never()).findByGuestIdAndIsPickedUpFalse(anyLong(), any());
    }

    @Test
    void getParcelsForGuest_ShouldReturnEmptyPage_WhenGuestHasNoParcels() {
        // Given
        Long guestId = 4L;
        int page = 0, size = 10;
        String sortBy = "id,asc";
        Guest guest = new Guest(guestId, "Mark Smith", "103", true);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Parcel> emptyPage = Page.empty();

        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));
        when(parcelRepository.findByGuestIdAndIsPickedUpFalse(guestId, pageable)).thenReturn(emptyPage);

        // When
        Page<Parcel> result = parcelService.getParcelsForGuest(guestId, page, size, sortBy);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        verify(parcelRepository).findByGuestIdAndIsPickedUpFalse(guestId, pageable);
    }

    @Test
    void markParcelAsPickedUp_ShouldMarkParcelAsPickedUp_WhenParcelExistsAndNotPickedUp() {
        // Given
        Long parcelId = 1L;
        Guest guest = new Guest(1L, "John Doe", "101", true);
        Parcel parcel = new Parcel(1L, "PKG001", "John Doe", guest, false);

        when(parcelRepository.findById(parcelId)).thenReturn(Optional.of(parcel));

        // When
        parcelService.markParcelAsPickedUp(parcelId);

        // Then
        assertTrue(parcel.isPickedUp());
        verify(parcelRepository).save(parcel);
    }

    @Test
    void markParcelAsPickedUp_ShouldThrowParcelNotFoundException_WhenParcelDoesNotExist() {
        // Given
        Long parcelId = 99L;
        when(parcelRepository.findById(parcelId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ParcelNotFoundException.class, () -> parcelService.markParcelAsPickedUp(parcelId));
        verify(parcelRepository, never()).save(any());
    }

    @Test
    void markParcelAsPickedUp_ShouldThrowParcelPickedUpException_WhenParcelIsAlreadyPickedUp() {
        // Given
        Long parcelId = 2L;
        Guest guest = new Guest(1L, "John Doe", "101", true);
        Parcel parcel = new Parcel(1L, "PKG001", "John Doe", guest, true);

        when(parcelRepository.findById(parcelId)).thenReturn(Optional.of(parcel));

        // When & Then
        assertThrows(ParcelPickedUpException.class, () -> parcelService.markParcelAsPickedUp(parcelId));
        verify(parcelRepository, never()).save(any());
    }
}
