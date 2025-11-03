package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.building.BuildingView;
import com.plub_kao.asset_it_support.entity.floor.Floor;
import com.plub_kao.asset_it_support.entity.floor.FloorView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FloorRepository extends JpaRepository<Floor, Integer> {

    @Query(value = """
            SELECT
                f.`id`,
                f.`floor_name`
            FROM
                `floor` f
            WHERE
                f.building_id = :buildingId
            """, nativeQuery = true)
    List<FloorView> filterFloorByBuilding(@Param("buildingId") Integer Id);
}
