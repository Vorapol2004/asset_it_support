package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {


    //ดึงการธุรกรรมการยืมที่มีอยู่ทั้งหมด
    @Query(value = """
        SELECT
            `id`,
            `employee_id`,
            DATE_FORMAT(borrow_date, '%Y-%m-%d') AS borrow_date,
            `reference_doc`,
            `borrow_equipment_id`
        FROM
            `borrow`
""",nativeQuery = true)
    List<BorrowView> getAllBorrowedEmployeeIds();




}
