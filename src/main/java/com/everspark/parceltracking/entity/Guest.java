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
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = EntityConstant.GUEST_TABLE)
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = EntityConstant.ROOM_NUMBER, nullable = false)
    private String roomNumber;

    @Column(name = EntityConstant.IS_CHECKED_OUT, nullable = false)
    private boolean isCheckedOut = false;
}

