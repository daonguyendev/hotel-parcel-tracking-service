package com.hrs.parceltracking.repository;

import com.hrs.parceltracking.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    List<Parcel> findByGuestIdAndIsPickedUpFalse(Long guestId);
    Optional<Parcel> findByTrackingNumber(String trackingNumber);
}
