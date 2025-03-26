package com.hrs.parceltracking.service.impl;

import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.entity.Guest;
import com.hrs.parceltracking.repository.GuestRepository;
import com.hrs.parceltracking.service.GuestService;
import com.hrs.parceltracking.utility.PaginationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;

    @Override
    public Guest checkIn(Guest guest) {
        guest.setCheckedOut(false);
        return guestRepository.save(guest);
    }

    @Override
    public void checkOut(Long guestId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(()-> new RuntimeException(MessageConstant.GUEST_NOT_FOUND_OR_CHECKED_OUT));
        guest.setCheckedOut(true);
        guestRepository.save(guest);
    }

    @Override
    public Page<Guest> getCheckedInGuests(int page, int size, String sortBy) {
        Pageable pageable = PaginationUtility.createPageable(page, size, sortBy);
        return guestRepository.findByIsCheckedOutFalse(pageable);
    }
}
