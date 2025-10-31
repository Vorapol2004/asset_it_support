package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.BorrowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BorrowStatusRepository extends JpaRepository<BorrowStatus, Integer> {


}
