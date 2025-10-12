package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {


    //ดึงการธุรกรรมการยืมที่มีอยู่ทั้งหมด
    @Query(value = """
                    SELECT
                                                                                        b.id,
                                                                                        e.first_name,
                                                                                        e.last_name,
                                                                                        e.email,
                                                                                        b.borrow_date,
                                                                                        be.return_date,
                                                                                        eq.equipment_name,
                                                                                        eq.brand,
                                                                                        eq.model,
                                                                                        eq.serial_number,
                                                                                        eq.license_key,
                                                                                        eqs.equipment_status_name
                                                                                    FROM
                                                                                        borrow b
                                                                                    LEFT JOIN employee e ON
                                                                                        e.id = b.employee_id
                                                                                    INNER JOIN borrow_equipment be ON
                                                                                        b.id = be.borrow_id
                                                                                    LEFT JOIN borrow_equipment_status bes ON
                                                                                        bes.id = be.borrow_equipment_status_id
                                                                                    LEFT JOIN equipment eq ON
                                                                                        eq.id = be.equipment_id
                                                                                    LEFT JOIN equipment_status eqs ON
                                                                                        eqs.id = eq.equipment_status_id
            """, nativeQuery = true)
    List<BorrowView> getAllBorrowedEmployeeIds();


    //filter borrow status
    @Query(value = """
                   SELECT
                       b.id,
                       e.first_name,
                       e.last_name,
                       e.email,
                       b.borrow_date,
                       be.return_date,
                       eq.equipment_name,
                       eq.brand,
                       eq.model,
                       eq.serial_number,
                       eq.license_key,
                       eqs.equipment_status_name
                   FROM
                       borrow b
                   LEFT JOIN employee e ON
                       e.id = b.employee_id
                   INNER JOIN borrow_equipment be ON
                       b.id = be.borrow_id
                   LEFT JOIN borrow_equipment_status bes ON
                       bes.id = be.borrow_equipment_status_id
                   LEFT JOIN equipment eq ON
                       eq.id = be.equipment_id
                   LEFT JOIN equipment_status eqs ON
                       eqs.id = eq.equipment_status_id
                   WHERE (:statusId IS NULL OR bs.id = :statusId)
                   ORDER BY b.borrow_date DESC
            """, nativeQuery = true)
    List<BorrowView> filterBorrowStatus(@Param("statusId") Integer statusId);

    //check due_date < current  borrowed change overdue
    @Modifying
    @Transactional
    @Query(value = """
             UPDATE
                borrow_equipment be
            INNER JOIN borrow_status bs_borrowed ON
                bs_borrowed.borrow_status_name = 'borrowed'
            INNER JOIN borrow_status bs_overdue ON
                bs_overdue.borrow_status_name = 'overdue'
            SET
                be.borrow_status_id = bs_overdue.id
            WHERE
                be.due_date < CURDATE() AND be.borrow_status_id = bs_borrowed.id
            """, nativeQuery = true)
    void updateOverdueStatus();


}
