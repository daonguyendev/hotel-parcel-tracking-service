package com.everspark.parceltracking.service;

import com.everspark.parceltracking.entity.Guest;
import org.springframework.data.domain.Page;

public interface GuestService {
    Guest checkIn(Guest guest);
    void checkOut(Long guestId);
    Page<Guest> getCheckedInGuests(int page, int size, String sortBy);
}
