package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.building.Building;
import com.plub_kao.asset_it_support.entity.building.BuildingView;
import com.plub_kao.asset_it_support.entity.department.DepartmentView;
import com.plub_kao.asset_it_support.entity.floor.FloorView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Integer> {

    @Query(value = """
            SELECT
            	b.id,
                b.building_name
            FROM
                 `building` b
            WHERE
                b.department_id = :departmentId;
            """, nativeQuery = true)
    List<BuildingView> filterBuildingByDepartment(@Param("departmentId") Integer Id);
}
