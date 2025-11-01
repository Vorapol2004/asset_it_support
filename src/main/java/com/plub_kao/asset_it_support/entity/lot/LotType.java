package com.plub_kao.asset_it_support.entity.lot;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lot_type")
public class LotType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "lot_type_name", nullable = false)
    private String lotTypeName;

    // ✅ เพิ่ม mapping กลับมาที่ Lot
    @OneToMany(mappedBy = "lotType")
    private List<Lot> lots;

}