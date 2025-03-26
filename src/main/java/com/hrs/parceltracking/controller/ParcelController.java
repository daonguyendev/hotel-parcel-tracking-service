package com.hrs.parceltracking.controller;

import com.hrs.parceltracking.constant.ApiConstant;
import com.hrs.parceltracking.dto.request.ParcelRequest;
import com.hrs.parceltracking.entity.Parcel;
import com.hrs.parceltracking.service.ParcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiConstant.PARCEL_API_PREFIX)
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @PostMapping(ApiConstant.RECEIVE_PARCEL_API_ENDPOINT)
    public ResponseEntity<?> receiveParcel(@RequestBody ParcelRequest parcelRequest) {
        return ResponseEntity.ok(parcelService.receiveParcel(parcelRequest));
    }

    @GetMapping(ApiConstant.AVAILABLE_PARCELS_API_ENDPOINT)
    public ResponseEntity<List<Parcel>> getParcelsForGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(parcelService.getParcelsForGuest(guestId));
    }

    @PostMapping(ApiConstant.MARK_PARCEL_PICKED_UP_API_ENDPOINT)
    public ResponseEntity<String> markParcelAsPickedUp(@PathVariable Long parcelId) {
        return ResponseEntity.ok(parcelService.markParcelAsPickedUp(parcelId));
    }
}

