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


    //เรียกดูประวัติการยืมของ Borrow ทั้งหมด
    @Query(value = """
            SELECT
            	b.id,
                b.borrow_date,
                be.return_date,
                be.due_date,
                b.borrow_status_id,
                bs.borrow_status_name,
                b.reference_doc,
            
            
                b.employee_id,
                CONCAT(e.first_name,' ', e.last_name) AS employee_name,
                e.email,
                e.phone,
                e.description,
            
                d.id AS department_id,
                d.department_name,
            
                r.id AS role_id ,
                r.role_name,
            
                be.equipment_id,
                eq.equipment_type_id,
                eqt.equipment_type_name,
                eq.equipment_name,
                eq.brand,
                eq.model,
                eq.serial_number,
                eq.license_key,
                eq.equipment_status_id,
                eqs.equipment_status_name
            
            FROM
                `borrow` b
            LEFT JOIN borrow_equipment be ON
                be.borrow_id = b.id
            LEFT JOIN borrow_status bs ON
            	bs.id = b.borrow_status_id
            LEFT JOIN equipment eq ON
                eq.id = be.equipment_id
            LEFT JOIN equipment_type eqt ON
            	eqt.id = eq.equipment_type_id
            LEFT JOIN equipment_status eqs ON
            	eqs.id = eq.equipment_status_id
            LEFT JOIN employee e ON
                e.id = b.employee_id
            LEFT JOIN department d ON
            	d.id = e.department_id
            LEFT JOIN role r ON
            	r.id = e.role_id
            
            """, nativeQuery = true)
    List<BorrowView> getAllBorrowedEmployeeId();


    //ฟิลเตอร์ Status ของ Borrow ออกมาทั้งหมด
    @Query(value = """
            SELECT
            	b.id,
                b.borrow_date,
                be.return_date,
                be.due_date,
                b.borrow_status_id,
                bs.borrow_status_name,
                b.reference_doc,
            
            
                b.employee_id,
                CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
                e.email,
                e.phone,
                e.description,
            
                d.id AS department_id,
                d.department_name,
            
                r.id AS role_id ,
                r.role_name,
            
                be.equipment_id,
                eq.equipment_type_id,
                eqt.equipment_type_name,
                eq.equipment_name,
                eq.brand,
                eq.model,
                eq.serial_number,
                eq.license_key,
                eq.equipment_status_id,
                eqs.equipment_status_name
            
            FROM
                `borrow` b
            LEFT JOIN borrow_equipment be ON
                be.borrow_id = b.id
            LEFT JOIN borrow_status bs ON
            	bs.id = b.borrow_status_id
            LEFT JOIN equipment eq ON
                eq.id = be.equipment_id
            LEFT JOIN equipment_type eqt ON
            	eqt.id = eq.equipment_type_id
            LEFT JOIN equipment_status eqs ON
            	eqs.id = eq.equipment_status_id
            LEFT JOIN employee e ON
                e.id = b.employee_id
            LEFT JOIN department d ON
            	d.id = e.department_id
            LEFT JOIN role r ON
            	r.id = e.role_id
            WHERE
                bs.id = :borrowStatusId
                AND r.id = :roleId
                AND
            
            """, nativeQuery = true)
    List<BorrowView> filterBorrowStatus(@Param("borrowStatusId") Integer borrowStatusId);


    //ค้นหาประวัติการยืมอุปกรณ์แต่ละชนิด จาก license_key และ serial_number
    @Query(value = """
            SELECT
            	b.id,
                b.borrow_date,
                be.return_date,
                be.due_date,
                b.borrow_status_id,
                bs.borrow_status_name,
                b.reference_doc,
            
                b.employee_id,
                CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
                e.email,
                e.phone,
                e.description,
            
                d.id AS department_id,
                d.department_name,
            
                r.id AS role_id ,
                r.role_name,
            
                be.equipment_id,
                eq.equipment_type_id,
                eqt.equipment_type_name,
                eq.equipment_name,
                eq.brand,
                eq.model,
                eq.serial_number,
                eq.license_key,
                eq.equipment_status_id,
                eqs.equipment_status_name
            
            FROM
                `borrow` b
            LEFT JOIN borrow_equipment be ON
                be.borrow_id = b.id
            LEFT JOIN borrow_status bs ON
            	bs.id = b.borrow_status_id
            LEFT JOIN equipment eq ON
                eq.id = be.equipment_id
            LEFT JOIN equipment_type eqt ON
            	eqt.id = eq.equipment_type_id
            LEFT JOIN equipment_status eqs ON
            	eqs.id = eq.equipment_status_id
            LEFT JOIN employee e ON
                e.id = b.employee_id
            LEFT JOIN department d ON
            	d.id = e.department_id
            LEFT JOIN role r ON
            	r.id = e.role_id
            WHERE
            	eq.license_key LIKE CONCAT('%', :keyword, '%')
                OR eq.serial_number LIKE CONCAT('%', :keyword, '%')
            """, nativeQuery = true)
    List<BorrowView> searchBorrowEquipment(@Param("keyword") String keyword);


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
