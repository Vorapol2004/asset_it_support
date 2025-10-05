package com.plub_kao.asset_it_support.entity.room;

import com.plub_kao.asset_it_support.entity.Department;
import com.plub_kao.asset_it_support.entity.Floor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "room_name", nullable = false, length = 90)
    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

}