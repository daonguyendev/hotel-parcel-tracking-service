package com.hrs.parceltracking.service.impl;

import com.hrs.parceltracking.constant.MessageConstant;
import com.hrs.parceltracking.entity.Guest;
import com.hrs.parceltracking.exception.GuestCheckedInException;
import com.hrs.parceltracking.exception.GuestCheckedOutException;
import com.hrs.parceltracking.exception.GuestNotFoundException;
import com.hrs.parceltracking.exception.InvalidGuestInfoException;
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
        Guest existingGuest = guestRepository.findByNameAndRoomNumberAndIsCheckedOutFalse(
                                                guest.getName(), guest.getRoomNumber())
                                             .orElseThrow(() -> new InvalidGuestInfoException(
                                                MessageConstant.INVALID_GUEST_INFO));
        if (!existingGuest.isCheckedOut()) {
            throw new GuestCheckedInException(MessageConstant.GUEST_ALREADY_CHECKED_IN);
        }
        existingGuest.setCheckedOut(false);
        return guestRepository.save(existingGuest);
    }

    @Override
    public void checkOut(Long guestId) {
        Guest guest = guestRepository.findById(guestId)
                                     .orElseThrow(() -> new GuestNotFoundException(MessageConstant.GUEST_NOT_FOUND));
        if (guest.isCheckedOut()) {
            throw new GuestCheckedOutException(MessageConstant.GUEST_ALREADY_CHECKED_OUT);
        }
        guest.setCheckedOut(true);
        guestRepository.save(guest);
    }

    @Override
    public Page<Guest> getCheckedInGuests(int page, int size, String sortBy) {
        Pageable pageable = PaginationUtility.createPageable(page, size, sortBy);
        return guestRepository.findByIsCheckedOutFalse(pageable);
    }
}
