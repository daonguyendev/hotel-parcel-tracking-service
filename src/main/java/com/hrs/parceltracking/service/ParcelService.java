package com.hrs.parceltracking.service;

import com.hrs.parceltracking.dto.request.ParcelRequest;
import com.hrs.parceltracking.entity.Parcel;
import org.springframework.data.domain.Page;

public interface ParcelService {
    String receiveParcel(ParcelRequest parcelRequest);
    Page<Parcel> getParcelsForGuest(Long guestId, int page, int size, String sortBy);
    String markParcelAsPickedUp(Long parcelId);
}
