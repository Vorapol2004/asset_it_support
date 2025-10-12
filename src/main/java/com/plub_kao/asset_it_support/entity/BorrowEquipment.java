package com.plub_kao.asset_it_support.entity;

import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "borrow_equipment")
public class BorrowEquipment {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "borrow_id")
    private Borrow borrow;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "borrow_equipment_status_id", nullable = false)
    private BorrowEquipmentStatus borrowEquipmentStatus;


    public BorrowEquipment(Equipment equipment, LocalDate dueDate, BorrowEquipmentStatus borrowEquipmentStatus) {
        this.equipment = equipment;
        this.dueDate = dueDate;
        this.borrowEquipmentStatus = borrowEquipmentStatus;
    }
}