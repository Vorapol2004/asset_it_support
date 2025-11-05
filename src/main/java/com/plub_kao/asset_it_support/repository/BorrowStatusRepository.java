package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.borrowStatus.BorrowStatus;
import com.plub_kao.asset_it_support.entity.borrowStatus.BorrowStatusView;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowStatusRepository extends JpaRepository<BorrowStatus, Integer> {

    @Query(value = "SELECT * FROM `borrow_status` ", nativeQuery = true)
    List<BorrowStatusView> findAllByStatus();

}
