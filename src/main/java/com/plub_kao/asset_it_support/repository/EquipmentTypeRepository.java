package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import com.plub_kao.asset_it_support.entity.equipmentType.EquipmentType;
import com.plub_kao.asset_it_support.entity.equipmentType.EquipmentTypeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Integer> {

    @Query(value = "SELECT * FROM `equipment_type`", nativeQuery = true)
    List<EquipmentTypeView> findAllByType();
}
