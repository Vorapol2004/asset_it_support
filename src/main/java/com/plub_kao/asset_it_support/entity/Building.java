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
@Table(name = "building")
public class Building {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "building_name", nullable = false, length = 50)
    private String buildingName;

}