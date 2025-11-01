package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.department.DepartmentRoom;
import com.plub_kao.asset_it_support.entity.department.View.BuildingView;
import com.plub_kao.asset_it_support.entity.department.View.RoomView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRoomRepository extends JpaRepository<DepartmentRoom, Integer> {


    @Query(value = """
                SELECT
                        b.id AS id,
                        b.building_name
                FROM
                        department_room dr
                JOIN
                        room r ON r.id = dr.room_id
                JOIN
                        building b ON b.id = r.building_id
                WHERE
                        dr.department_id = :departmentId
            """, nativeQuery = true)
    List<BuildingView> findBuildingsByDepartment(@Param("departmentId") Integer departmentId);


    @Query(value = """
                SELECT r.id AS id, r.room_name AS roomName, r.floor_name AS floorName
                FROM department_room dr
                JOIN room r ON r.id = dr.room_id
                JOIN building b ON b.id = r.building_id
                WHERE dr.department_id = :departmentId
                  AND b.id = :buildingId
            """, nativeQuery = true)
    List<RoomView> findRoomsByDepartmentAndBuilding(
            @Param("departmentId") Integer departmentId,
            @Param("buildingId") Integer buildingId
    );


}
