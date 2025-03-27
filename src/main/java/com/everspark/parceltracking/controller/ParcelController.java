package com.everspark.parceltracking.controller;

import com.everspark.parceltracking.constant.ApiConstant;
import com.everspark.parceltracking.constant.PaginationConstant;
import com.everspark.parceltracking.constant.SwaggerConstant;
import com.everspark.parceltracking.dto.request.ParcelRequest;
import com.everspark.parceltracking.constant.MessageConstant;
import com.everspark.parceltracking.dto.response.StandardResponse;
import com.everspark.parceltracking.entity.Parcel;
import com.everspark.parceltracking.service.ParcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.PARCEL_API_PREFIX)
@RequiredArgsConstructor
@Api(tags = SwaggerConstant.PARCEL_API_TAGS, description = SwaggerConstant.PARCEL_API_DESCRIPTION)
public class ParcelController {

    private final ParcelService parcelService;

    @PostMapping(ApiConstant.RECEIVE_PARCEL_API_ENDPOINT)
    @ApiOperation(value = SwaggerConstant.RECEIVE_A_PARCEL_VALUE, notes = SwaggerConstant.RECEIVE_A_PARCEL_NOTES)
    public ResponseEntity<StandardResponse<Void>> receiveParcel(@RequestBody ParcelRequest parcelRequest) {
        parcelService.receiveParcel(parcelRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                StandardResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(MessageConstant.PARCEL_RECEIVED_SUCCESS)
                        .build()
        );
    }

    @GetMapping(ApiConstant.AVAILABLE_PARCELS_API_ENDPOINT)
    @ApiOperation(value = SwaggerConstant.AVAILABLE_PARCELS_VALUE, notes = SwaggerConstant.AVAILABLE_PARCELS_NOTES)
    public ResponseEntity<StandardResponse<Page<Parcel>>> getParcelsForGuest(
            @PathVariable Long guestId,
            @RequestParam(defaultValue = PaginationConstant.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = PaginationConstant.DEFAULT_SIZE) int size,
            @RequestParam(defaultValue = PaginationConstant.SORT_BY_ID_ASC) String sortBy) {

        Page<Parcel> parcels = parcelService.getParcelsForGuest(guestId, page, size, sortBy);
        return ResponseEntity.ok(StandardResponse.<Page<Parcel>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(MessageConstant.SUCCESS_RETRIEVED_AVAILABLE_PARCELS)
                .data(parcels)
                .build());
    }

    @PostMapping(ApiConstant.MARK_PARCEL_PICKED_UP_API_ENDPOINT)
    @ApiOperation(value = SwaggerConstant.PARCEL_AS_PICKED_UP_VALUE, notes = SwaggerConstant.PARCEL_AS_PICKED_UP_NOTES)
    public ResponseEntity<StandardResponse<Void>> markParcelAsPickedUp(@PathVariable Long parcelId) {
        parcelService.markParcelAsPickedUp(parcelId);
        return ResponseEntity.ok(StandardResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message(MessageConstant.PARCEL_MARKED_AS_PICKED_UP)
                .build());
    }
}

