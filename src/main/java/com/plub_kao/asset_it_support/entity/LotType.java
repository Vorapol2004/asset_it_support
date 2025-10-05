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
@Table(name = "lot_type")
public class LotType {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "lot_type_name", nullable = false)
    private String lotTypeName;

}