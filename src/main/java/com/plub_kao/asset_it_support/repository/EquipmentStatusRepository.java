package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatus;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentStatusRepository extends JpaRepository<EquipmentStatus, Integer> {

    @Query(value = "SELECT * FROM `equipment_status`", nativeQuery = true)
    List<EquipmentStatusView> findAllByStatus();
}
