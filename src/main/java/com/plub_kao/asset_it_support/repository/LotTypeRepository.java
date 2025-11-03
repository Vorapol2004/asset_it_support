package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.lotType.LotType;
import com.plub_kao.asset_it_support.entity.lotType.LotTypeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LotTypeRepository extends JpaRepository<LotType, Integer> {

    @Query(value = "SELECT * FROM `lot_type` ", nativeQuery = true)
    List<LotTypeView> findAllByType();
}
