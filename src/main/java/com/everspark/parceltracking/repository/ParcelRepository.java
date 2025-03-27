package com.everspark.parceltracking.repository;

import com.everspark.parceltracking.entity.Parcel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    Page<Parcel> findByGuestIdAndIsPickedUpFalse(Long guestId, Pageable pageable);
    Optional<Parcel> findByTrackingNumber(String trackingNumber);
}
