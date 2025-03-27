package com.everspark.parceltracking.service.impl;

import com.everspark.parceltracking.constant.MessageConstant;
import com.everspark.parceltracking.entity.Guest;
import com.everspark.parceltracking.exception.GuestCheckedInException;
import com.everspark.parceltracking.exception.GuestCheckedOutException;
import com.everspark.parceltracking.exception.GuestNotFoundException;
import com.everspark.parceltracking.exception.InvalidGuestInfoException;
import com.everspark.parceltracking.repository.GuestRepository;
import com.everspark.parceltracking.service.GuestService;
import com.everspark.parceltracking.utility.PaginationUtility;
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
        Guest existingGuest = guestRepository.findByNameAndRoomNumber(
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
