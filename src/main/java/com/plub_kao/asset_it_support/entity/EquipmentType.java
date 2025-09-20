package com.plub_kao.asset_it_support.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "equipment_type")
public class EquipmentType {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "equipment_type_name", length = 100)
    private String equipmentTypeName;

}