package com.hrs.parceltracking.service;

import com.hrs.parceltracking.dto.request.ParcelRequest;
import com.hrs.parceltracking.entity.Parcel;

import java.util.List;

public interface ParcelService {
    String receiveParcel(ParcelRequest parcelRequest);
    List<Parcel> getParcelsForGuest(Long guestId);
    String markParcelAsPickedUp(Long parcelId);
}
