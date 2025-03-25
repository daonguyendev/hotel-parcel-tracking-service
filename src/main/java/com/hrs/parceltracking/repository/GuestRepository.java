package com.hrs.parceltracking.repository;

import com.hrs.parceltracking.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByIsCheckedOutFalse();
    Optional<Guest> findByNameAndIsCheckedOutFalse(String recipientName);
}
