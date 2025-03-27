package com.everspark.parceltracking.service;

import com.everspark.parceltracking.dto.request.ParcelRequest;
import com.everspark.parceltracking.entity.Guest;
import com.everspark.parceltracking.entity.Parcel;
import com.everspark.parceltracking.exception.AllParcelPickedUpException;
import com.everspark.parceltracking.exception.GuestCheckedOutException;
import com.everspark.parceltracking.exception.GuestNotFoundException;
import com.everspark.parceltracking.exception.InvalidGuestInfoException;
import com.everspark.parceltracking.exception.ParcelNotFoundException;
import com.everspark.parceltracking.exception.ParcelPickedUpException;
import com.everspark.parceltracking.repository.GuestRepository;
import com.everspark.parceltracking.repository.ParcelRepository;
import com.everspark.parceltracking.service.impl.ParcelServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        ParcelRequest request = new ParcelRequest("PKG001", "John Doe", "101");
        Guest guest = new Guest(1L, "John Doe", "101", false);
        Parcel parcel = new Parcel(1L, "PKG001", "John Doe", guest, false);

        when(guestRepository.findByNameAndRoomNumber("John Doe", "101"))
                .thenReturn(Optional.of(guest));
        when(parcelRepository.findByTrackingNumber("PKG001"))
                .thenReturn(Optional.of(parcel));

        parcelService.receiveParcel(request);

        assertTrue(parcel.isPickedUp());
        verify(parcelRepository, times(1)).save(parcel);
    }

    @Test
    void receiveParcel_ShouldThrowInvalidGuestInfoException_WhenGuestNotFound() {
        ParcelRequest request = new ParcelRequest("PKG001", "John Doe", "101");
        when(guestRepository.findByNameAndRoomNumber(any(), any())).thenReturn(Optional.empty());

        assertThrows(InvalidGuestInfoException.class, () -> parcelService.receiveParcel(request));
        verify(parcelRepository, never()).findByTrackingNumber(anyString());
    }

    @Test
    void receiveParcel_ShouldThrowParcelNotFoundException_WhenParcelNotFound() {
        ParcelRequest request = new ParcelRequest("PKG001", "John Doe", "101");
        Guest guest = new Guest(1L, "John Doe", "101", false);

        when(guestRepository.findByNameAndRoomNumber("John Doe", "101"))
                .thenReturn(Optional.of(guest));
        when(parcelRepository.findByTrackingNumber("PKG001"))
                .thenReturn(Optional.empty());

        assertThrows(ParcelNotFoundException.class, () -> parcelService.receiveParcel(request));
    }

    @Test
    void getParcelsForGuest_ShouldReturnParcels_WhenGuestHasParcels() {
        Long guestId = 1L;
        Guest guest = new Guest(guestId, "John Doe", "101", false);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        List<Parcel> parcelList = List.of(
                new Parcel(1L, "PKG001", "John Doe", guest, false),
                new Parcel(2L, "PKG002", "John Doe", guest, false)
        );
        Page<Parcel> parcelsPage = new PageImpl<>(parcelList);

        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));
        when(parcelRepository.findByGuestIdAndIsPickedUpFalse(guestId, pageable)).thenReturn(parcelsPage);

        Page<Parcel> result = parcelService.getParcelsForGuest(guestId, 0, 10, "id,asc");

        assertFalse(result.isEmpty());
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void receiveParcel_ShouldThrowParcelPickedUpException_WhenParcelIsAlreadyPickedUp() {
        ParcelRequest request = new ParcelRequest("PKG001", "John Doe", "101");
        Guest guest = new Guest(1L, "John Doe", "101", false);
        Parcel parcel = new Parcel(1L, "PKG001", "John Doe", guest, true);

        when(guestRepository.findByNameAndRoomNumber("John Doe", "101"))
                .thenReturn(Optional.of(guest));
        when(parcelRepository.findByTrackingNumber("PKG001"))
                .thenReturn(Optional.of(parcel));

        assertThrows(ParcelPickedUpException.class, () -> parcelService.receiveParcel(request));
    }

    @Test
    void getParcelsForGuest_ShouldThrowGuestNotFoundException_WhenGuestDoesNotExist() {
        Long guestId = 5L;
        when(guestRepository.findById(guestId)).thenReturn(Optional.empty());

        assertThrows(GuestNotFoundException.class, () -> parcelService.getParcelsForGuest(guestId, 0, 10, "id,asc"));
    }

    @Test
    void getParcelsForGuest_ShouldThrowGuestCheckedOutException_WhenGuestIsCheckedOut() {
        Long guestId = 3L;
        Guest guest = new Guest(guestId, "Jane Doe", "102", true);

        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));

        assertThrows(GuestCheckedOutException.class, () -> parcelService.getParcelsForGuest(guestId, 0, 10, "id,asc"));
        verify(parcelRepository, never()).findByGuestIdAndIsPickedUpFalse(anyLong(), any());
    }

    @Test
    void getParcelsForGuest_ShouldThrowAllParcelPickedUpException_WhenNoParcelsLeft() {
        Long guestId = 4L;
        Guest guest = new Guest(guestId, "Mark Smith", "103", false);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<Parcel> emptyPage = Page.empty();

        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));
        when(parcelRepository.findByGuestIdAndIsPickedUpFalse(guestId, pageable)).thenReturn(emptyPage);

        assertThrows(AllParcelPickedUpException.class, () -> parcelService.getParcelsForGuest(guestId, 0, 10, "id,asc"));
    }

    @Test
    void markParcelAsPickedUp_Success() {
        Long parcelId = 2L;
        Guest guest = new Guest(1L, "John Doe", "101", false);
        Parcel parcel = new Parcel(parcelId, "PKG001", "John Doe", guest, false);

        when(parcelRepository.findById(parcelId)).thenReturn(Optional.of(parcel));

        parcelService.markParcelAsPickedUp(parcelId);

        assertTrue(parcel.isPickedUp());
        verify(parcelRepository, times(1)).save(parcel);
    }

    @Test
    void markParcelAsPickedUp_ShouldThrowParcelPickedUpException_WhenParcelIsAlreadyPickedUp() {
        Long parcelId = 2L;
        Guest guest = new Guest(1L, "John Doe", "101", false);
        Parcel parcel = new Parcel(parcelId, "PKG001", "John Doe", guest, true);

        when(parcelRepository.findById(parcelId)).thenReturn(Optional.of(parcel));

        assertThrows(ParcelPickedUpException.class, () -> parcelService.markParcelAsPickedUp(parcelId));
        verify(parcelRepository, never()).save(any());
    }

    @Test
    void markParcelAsPickedUp_ShouldThrowParcelNotFoundException_WhenParcelDoesNotExist() {
        Long parcelId = 10L;
        when(parcelRepository.findById(parcelId)).thenReturn(Optional.empty());

        assertThrows(ParcelNotFoundException.class, () -> parcelService.markParcelAsPickedUp(parcelId));
    }
}

