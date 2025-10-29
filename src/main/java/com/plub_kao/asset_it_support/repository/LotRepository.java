package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.Lot;
import com.plub_kao.asset_it_support.entity.LotType;
import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Integer> {



}
