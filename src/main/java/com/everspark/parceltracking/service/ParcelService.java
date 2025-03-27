package com.everspark.parceltracking.service;

import com.everspark.parceltracking.dto.request.ParcelRequest;
import com.everspark.parceltracking.entity.Parcel;
import org.springframework.data.domain.Page;

public interface ParcelService {
    void receiveParcel(ParcelRequest parcelRequest);
    Page<Parcel> getParcelsForGuest(Long guestId, int page, int size, String sortBy);
    void markParcelAsPickedUp(Long parcelId);
}
