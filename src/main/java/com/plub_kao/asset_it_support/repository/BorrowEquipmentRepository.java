package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BorrowEquipmentRepository extends JpaRepository<BorrowEquipment, Integer> {
    @Query("SELECT COUNT(b) FROM BorrowEquipment b WHERE b.equipment.id = :equipmentId")
    int countByEquipmentId(Integer equipmentId);
}
