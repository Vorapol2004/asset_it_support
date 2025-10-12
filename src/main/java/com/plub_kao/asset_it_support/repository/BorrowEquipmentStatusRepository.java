package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.BorrowEquipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowEquipmentStatusRepository extends JpaRepository<BorrowEquipmentStatus, Integer> {


    public BorrowEquipmentStatus findById(int equipmentId);
}
