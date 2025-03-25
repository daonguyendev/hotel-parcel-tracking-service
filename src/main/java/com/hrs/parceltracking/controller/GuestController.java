package com.hrs.parceltracking.controller;

import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.constant.ApiConstant;
import com.hrs.parceltracking.entity.Guest;
import com.hrs.parceltracking.service.GuestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiConstant.GUEST_API_PREFIX)
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping(ApiConstant.CHECK_IN_API_ENDPOINT)
    public ResponseEntity<Guest> checkIn(@RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.checkIn(guest));
    }

    @PostMapping(ApiConstant.CHECKED_OUT_BY_ID_API_ENDPOINT)
    public ResponseEntity<String> checkOut(@PathVariable Long id) {
        guestService.checkOut(id);
        return ResponseEntity.ok(MessageConstant.GUEST_CHECKOUT_SUCCESS);
    }

    @GetMapping(ApiConstant.CHECKED_IN_GUESTS_API_ENDPOINT)
    public ResponseEntity<List<Guest>> getCheckedInGuests() {
        return ResponseEntity.ok(guestService.getCheckedInGuests());
    }
}
