package com.everspark.parceltracking.service.impl;

import com.everspark.parceltracking.constant.MessageConstant;
import com.everspark.parceltracking.dto.request.ParcelRequest;
import com.everspark.parceltracking.entity.Guest;
import com.everspark.parceltracking.entity.Parcel;
import com.everspark.parceltracking.exception.GuestCheckedOutException;
import com.everspark.parceltracking.exception.GuestNotFoundException;
import com.everspark.parceltracking.exception.InvalidGuestInfoException;
import com.everspark.parceltracking.exception.ParcelPickedUpException;
import com.everspark.parceltracking.exception.ParcelNotFoundException;
import com.everspark.parceltracking.exception.AllParcelPickedUpException;
import com.everspark.parceltracking.repository.GuestRepository;
import com.everspark.parceltracking.repository.ParcelRepository;
import com.everspark.parceltracking.service.ParcelService;
import com.everspark.parceltracking.utility.PaginationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final GuestRepository guestRepository;

    @Override
    public void receiveParcel(ParcelRequest parcelRequest) {
        guestRepository.findByNameAndRoomNumber(parcelRequest.getRecipientName(), parcelRequest.getRoomNumber())
                                 .orElseThrow(() -> new InvalidGuestInfoException(
                                        MessageConstant.INVALID_GUEST_INFO));

        Parcel parcel = parcelRepository.findByTrackingNumber(parcelRequest.getTrackingNumber())
                .orElseThrow(() -> new ParcelNotFoundException(MessageConstant.PARCEL_NOT_FOUND));

        if (parcel.isPickedUp()) {
            throw new ParcelPickedUpException(MessageConstant.PARCEL_ALREADY_PICKED_UP);
        }

        parcel.setPickedUp(true);
        parcelRepository.save(parcel);
    }

    @Override
    public Page<Parcel> getParcelsForGuest(Long guestId, int page, int size, String sortBy) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException(MessageConstant.GUEST_NOT_FOUND));

        if (guest.isCheckedOut()) {
            throw new GuestCheckedOutException(MessageConstant.GUEST_ALREADY_CHECKED_OUT);
        }

        Pageable pageable = PaginationUtility.createPageable(page, size, sortBy);
        Page<Parcel> parcels = parcelRepository.findByGuestIdAndIsPickedUpFalse(guestId, pageable);

        if (parcels.getContent().size() == 0) {
            throw new AllParcelPickedUpException(MessageConstant.ALL_PARCEL_ALREADY_PICKED_UP);
        }

        return parcels;
    }

    @Override
    public void markParcelAsPickedUp(Long parcelId) {
        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ParcelNotFoundException(MessageConstant.PARCEL_NOT_FOUND));

        if (parcel.isPickedUp()) {
            throw new ParcelPickedUpException(MessageConstant.PARCEL_ALREADY_PICKED_UP);
        } else {
            parcel.setPickedUp(true);
            parcelRepository.save(parcel);
        }
    }
}
