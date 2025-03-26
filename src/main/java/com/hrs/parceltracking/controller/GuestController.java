package com.hrs.parceltracking.controller;

import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.constant.ApiConstant;
import com.hrs.parceltracking.constant.PaginationConstant;
import com.hrs.parceltracking.constant.SwaggerConstant;
import com.hrs.parceltracking.entity.Guest;
import com.hrs.parceltracking.service.GuestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.GUEST_API_PREFIX)
@RequiredArgsConstructor
@Api(tags = SwaggerConstant.GUEST_API_TAGS, description = SwaggerConstant.GUEST_API_DESCRIPTION)
public class GuestController {

    private final GuestService guestService;

    @PostMapping(ApiConstant.CHECK_IN_API_ENDPOINT)
    @ApiOperation(value = SwaggerConstant.CHECK_IN_A_GUEST_VALUE, notes = SwaggerConstant.CHECK_IN_A_GUEST_NOTES)
    public ResponseEntity<Guest> checkIn(@RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.checkIn(guest));
    }

    @PostMapping(ApiConstant.CHECKED_OUT_BY_ID_API_ENDPOINT)
    @ApiOperation(value = SwaggerConstant.CHECK_OUT_A_GUEST_VALUE, notes = SwaggerConstant.CHECK_OUT_A_GUEST_NOTES)
    public ResponseEntity<String> checkOut(@PathVariable Long id) {
        guestService.checkOut(id);
        return ResponseEntity.ok(MessageConstant.GUEST_CHECKOUT_SUCCESS);
    }

    @GetMapping(ApiConstant.CHECKED_IN_GUESTS_API_ENDPOINT)
    @ApiOperation(value = SwaggerConstant.CHECKED_IN_GUESTS_VALUE, notes = SwaggerConstant.CHECKED_IN_GUESTS_NOTES)
    public ResponseEntity<Page<Guest>> getCheckedInGuests(
            @RequestParam(defaultValue = PaginationConstant.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = PaginationConstant.DEFAULT_SIZE) int size,
            @RequestParam(defaultValue = PaginationConstant.DEFAULT_SORT_BY) String sortBy) {
        return ResponseEntity.ok(guestService.getCheckedInGuests(page, size, sortBy));
    }
}
