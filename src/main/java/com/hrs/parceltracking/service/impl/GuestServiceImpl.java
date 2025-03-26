package com.hrs.parceltracking.service.impl;

import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.entity.Guest;
import com.hrs.parceltracking.repository.GuestRepository;
import com.hrs.parceltracking.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(()-> new RuntimeException(MessageConstant.GUEST_NOT_FOUND));
        guest.setCheckedOut(true);
        guestRepository.save(guest);
    }

    @Override
    public List<Guest> getCheckedInGuests() {
        return guestRepository.findByIsCheckedOutFalse();
    }
}
