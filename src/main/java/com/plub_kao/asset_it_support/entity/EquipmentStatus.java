package com.plub_kao.asset_it_support.entity;

import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "equipment_status")
public class EquipmentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "equipment_status_name", nullable = false, length = 100)
    private String equipmentStatusName;

    @OneToMany(mappedBy = "equipmentStatus", fetch = FetchType.LAZY)
    private List<Equipment> equipments;


}