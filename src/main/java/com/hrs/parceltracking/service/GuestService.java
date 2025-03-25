package com.hrs.parceltracking.service;

import com.hrs.parceltracking.entity.Guest;

import java.util.List;

public interface GuestService {
    Guest checkIn(Guest guest);
    void checkOut(Long guestId);
    List<Guest> getCheckedInGuests();
}
