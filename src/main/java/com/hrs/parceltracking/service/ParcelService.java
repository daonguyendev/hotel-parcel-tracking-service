package com.hrs.parceltracking.service;

import com.hrs.parceltracking.dto.request.ParcelRequest;
import com.hrs.parceltracking.entity.Parcel;
import org.springframework.data.domain.Page;

public interface ParcelService {
    void receiveParcel(ParcelRequest parcelRequest);
    Page<Parcel> getParcelsForGuest(Long guestId, int page, int size, String sortBy);
    void markParcelAsPickedUp(Long parcelId);
}
