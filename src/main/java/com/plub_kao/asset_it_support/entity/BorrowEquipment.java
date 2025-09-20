package com.plub_kao.asset_it_support.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "borrow_equipment")
public class BorrowEquipment {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "return_date")
    private Instant returnDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "borrow_status_id", nullable = false)
    private com.plub_kao.asset_it_support.entity.BorrowStatus borrowStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private com.plub_kao.asset_it_support.entity.Equipment equipment;

}