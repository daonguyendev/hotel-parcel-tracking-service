package com.everspark.parceltracking.entity;

import com.everspark.parceltracking.constant.EntityConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = EntityConstant.PARCELS_TABLE)
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = EntityConstant.TRACKING_NUMBER, nullable = false)
    private String trackingNumber;

    @Column(name = EntityConstant.RECIPIENT_NAME, nullable = false)
    private String recipientName;

    @ManyToOne
    @JoinColumn(name = EntityConstant.GUEST_ID)
    private Guest guest;

    @Column(name = EntityConstant.IS_PICKED_UP, nullable = false)
    private boolean isPickedUp;
}

