package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.department.Department;
import com.plub_kao.asset_it_support.entity.department.DepartmentView;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query(value = "SELECT * FROM `department` ", nativeQuery = true)
    List<DepartmentView> findAllByDepartment();

}
