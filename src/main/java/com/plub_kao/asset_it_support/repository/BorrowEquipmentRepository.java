package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowEquipmentRepository extends JpaRepository<BorrowEquipment, Integer> {
    @Query(value = """
                SELECT
                    COUNT(b.id)
                FROM
                    `borrow_equipment` b
                WHERE
                    b.equipment_id = :equipmentId
            
            """, nativeQuery = true)
    int countByEquipmentId(Integer equipmentId);
}
