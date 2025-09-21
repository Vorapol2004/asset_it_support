package com.plub_kao.asset_it_support.entity.borrow;

import com.plub_kao.asset_it_support.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "borrow")
public class Borrow {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;


    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "reference_doc")
    private String referenceDoc;

    @Column(name = "borrow_equipment_id")
    private Integer borrowEquipmentId;

}