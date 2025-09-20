package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
}
