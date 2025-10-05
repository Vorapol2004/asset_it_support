package com.plub_kao.asset_it_support.entity;

import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "return_date")
    private Instant returnDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "borrow_status_id", nullable = false)
    private BorrowStatus borrowStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "borrow_id", nullable = false)
    private Borrow borrow;

}