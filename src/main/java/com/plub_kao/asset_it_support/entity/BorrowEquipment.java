package com.plub_kao.asset_it_support.entity;

import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "borrow_equipment")
public class BorrowEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "borrow_id")
    private Borrow borrow;

    @Column(name = "due_date")
    private LocalDate dueDate;

}