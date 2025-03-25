package com.hrs.parceltracking.service.impl;

import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.dto.request.ParcelRequest;
import com.hrs.parceltracking.entity.Parcel;
import com.hrs.parceltracking.repository.GuestRepository;
import com.hrs.parceltracking.repository.ParcelRepository;
import com.hrs.parceltracking.service.ParcelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelServiceImpl implements ParcelService {
    private final ParcelRepository parcelRepository;
    private final GuestRepository guestRepository;

    public ParcelServiceImpl(ParcelRepository parcelRepository, GuestRepository guestRepository) {
        this.parcelRepository = parcelRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public String receiveParcel(ParcelRequest parcelRequest) {
        return guestRepository.findByNameAndIsCheckedOutFalse(parcelRequest.getRecipientName())
                .map(guest -> {
                    Parcel parcel = parcelRepository.findByTrackingNumber(parcelRequest.getTrackingNumber())
                            .orElseThrow(() -> new RuntimeException(MessageConstant.PARCEL_NOT_FOUND));

                    parcel.setPickedUp(true);
                    parcelRepository.save(parcel);

                    return MessageConstant.PARCEL_ACCEPTED;
                })
                .orElse(MessageConstant.GUEST_NOT_FOUND);
    }

    @Override
    public List<Parcel> getParcelsForGuest(Long guestId) {
        return parcelRepository.findByGuestIdAndIsPickedUpFalse(guestId);
    }
}
