package com.hrs.parceltracking.controller;

import com.hrs.parceltracking.constant.ApiConstant;
import com.hrs.parceltracking.constant.SwaggerConstant;
import com.hrs.parceltracking.dto.request.ParcelRequest;
import com.hrs.parceltracking.entity.Parcel;
import com.hrs.parceltracking.service.ParcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = SwaggerConstant.PARCEL_API_TAGS, description = SwaggerConstant.PARCEL_API_DESCRIPTION)
public class ParcelController {

    private final ParcelService parcelService;

    @PostMapping(ApiConstant.RECEIVE_PARCEL_API_ENDPOINT)
    @ApiOperation(value = SwaggerConstant.RECEIVE_A_PARCEL_VALUE, notes = SwaggerConstant.RECEIVE_A_PARCEL_NOTES)
    public ResponseEntity<?> receiveParcel(@RequestBody ParcelRequest parcelRequest) {
        return ResponseEntity.ok(parcelService.receiveParcel(parcelRequest));
    }

    @GetMapping(ApiConstant.AVAILABLE_PARCELS_API_ENDPOINT)
    @ApiOperation(value = SwaggerConstant.AVAILABLE_PARCELS_VALUE, notes = SwaggerConstant.AVAILABLE_PARCELS_NOTES)
    public ResponseEntity<List<Parcel>> getParcelsForGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(parcelService.getParcelsForGuest(guestId));
    }

    @PostMapping(ApiConstant.MARK_PARCEL_PICKED_UP_API_ENDPOINT)
    @ApiOperation(value = SwaggerConstant.PARCEL_AS_PICKED_UP_VALUE, notes = SwaggerConstant.PARCEL_AS_PICKED_UP_NOTES)
    public ResponseEntity<String> markParcelAsPickedUp(@PathVariable Long parcelId) {
        return ResponseEntity.ok(parcelService.markParcelAsPickedUp(parcelId));
    }
}

