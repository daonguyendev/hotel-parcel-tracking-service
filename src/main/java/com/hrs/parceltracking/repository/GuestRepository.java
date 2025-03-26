package com.hrs.parceltracking.repository;

import com.hrs.parceltracking.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Page<Guest> findByIsCheckedOutFalse(Pageable pageable);
    Optional<Guest> findByNameAndRoomNumberAndIsCheckedOutFalse(String recipientName, String roomNumber);
}
