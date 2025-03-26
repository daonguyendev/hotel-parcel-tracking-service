package com.hrs.parceltracking.service.impl;

import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.dto.request.ParcelRequest;
import com.hrs.parceltracking.entity.Guest;
import com.hrs.parceltracking.entity.Parcel;
import com.hrs.parceltracking.exception.GuestNotFoundOrCheckedOutException;
import com.hrs.parceltracking.exception.ParcelAlreadyPickedUpException;
import com.hrs.parceltracking.exception.ParcelIsPickedUpException;
import com.hrs.parceltracking.exception.ParcelNotFoundException;
import com.hrs.parceltracking.repository.GuestRepository;
import com.hrs.parceltracking.repository.ParcelRepository;
import com.hrs.parceltracking.service.ParcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final GuestRepository guestRepository;

    @Override
    public String receiveParcel(ParcelRequest parcelRequest) {
        Optional<Guest> guest = guestRepository.findByNameAndRoomNumberAndIsCheckedOutFalse(
                                    parcelRequest.getRecipientName(), parcelRequest.getRoomNumber());
        if (guest.isEmpty()) {
            throw new GuestNotFoundOrCheckedOutException(MessageConstant.GUEST_NOT_FOUND_OR_CHECKED_OUT);
        }

        Parcel parcel = parcelRepository.findByTrackingNumber(parcelRequest.getTrackingNumber())
                .orElseThrow(() -> new ParcelNotFoundException(MessageConstant.PARCEL_NOT_FOUND));

        if (parcel.isPickedUp()) {
            throw new ParcelIsPickedUpException(MessageConstant.PARCEL_ALREADY_PICKED_UP);
        }

        parcel.setPickedUp(true);
        parcelRepository.save(parcel);

        return MessageConstant.PARCEL_ACCEPTED;
    }

    @Override
    public List<Parcel> getParcelsForGuest(Long guestId) {
        return parcelRepository.findByGuestIdAndIsPickedUpFalse(guestId);
    }

    @Override
    public String markParcelAsPickedUp(Long parcelId) {
        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ParcelNotFoundException(MessageConstant.PARCEL_NOT_FOUND));

        if (parcel.isPickedUp()) {
            throw new ParcelAlreadyPickedUpException(MessageConstant.PARCEL_ALREADY_PICKED_UP);
        }

        parcel.setPickedUp(true);
        parcelRepository.save(parcel);

        return MessageConstant.PARCEL_MARKED_AS_PICKED_UP;
    }
}
