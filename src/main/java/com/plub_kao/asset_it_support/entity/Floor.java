package com.plub_kao.asset_it_support.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "floor")
public class Floor {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "floor_name", nullable = false, length = 50)
    private String floorName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

}