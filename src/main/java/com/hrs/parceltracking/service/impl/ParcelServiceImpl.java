package com.hrs.parceltracking.service.impl;

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
import com.hrs.parceltracking.service.ParcelService;
import com.hrs.parceltracking.utility.PaginationUtility;
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
        guestRepository.findByNameAndRoomNumberAndIsCheckedOutFalse(
                                        parcelRequest.getRecipientName(), parcelRequest.getRoomNumber())
                                 .orElseThrow(() -> new InvalidGuestInfoException(
                                        MessageConstant.INVALID_GUEST_INFO));

        Parcel parcel = parcelRepository.findByTrackingNumber(parcelRequest.getTrackingNumber())
                .orElseThrow(() -> new ParcelNotFoundException(MessageConstant.PARCEL_NOT_FOUND));

        if (parcel.isPickedUp()) {
            throw new ParcelIsPickedUpException(MessageConstant.PARCEL_ALREADY_PICKED_UP);
        }

        parcel.setPickedUp(true);
        parcelRepository.save(parcel);
    }

    @Override
    public Page<Parcel> getParcelsForGuest(Long guestId, int page, int size, String sortBy) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException(MessageConstant.GUEST_NOT_FOUND));

        if (!guest.isCheckedOut()) {
            throw new GuestCheckedInException(MessageConstant.GUEST_ALREADY_CHECKED_IN);
        }

        Pageable pageable = PaginationUtility.createPageable(page, size, sortBy);
        return parcelRepository.findByGuestIdAndIsPickedUpFalse(guestId, pageable);
    }

    @Override
    public void markParcelAsPickedUp(Long parcelId) {
        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ParcelNotFoundException(MessageConstant.PARCEL_NOT_FOUND));

        if (parcel.isPickedUp()) {
            throw new ParcelPickedUpException(MessageConstant.PARCEL_ALREADY_PICKED_UP);
        }

        parcel.setPickedUp(true);
        parcelRepository.save(parcel);
    }
}
