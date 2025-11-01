package com.plub_kao.asset_it_support.entity.lot;

import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lot")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    // ✅ LotType เชื่อมถูกต้อง
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_type_id", nullable = false)
    private LotType lotType;

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

    @Column(name = "expire_date")
    private LocalDate expireDate;

    // ✅ ความสัมพันธ์กับ Equipment
    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL)
    private List<Equipment> equipmentList;
}
