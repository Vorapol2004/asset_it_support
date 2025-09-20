package com.plub_kao.asset_it_support.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "lot")
public class Lot {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "lot_name", nullable = false, length = 100)
    private String lotName;

    @Column(name = "academic_year", length = 20)
    private String academicYear;

    @Column(name = "reference_doc")
    private String referenceDoc;

    @Column(name = "description")
    private String description;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

}