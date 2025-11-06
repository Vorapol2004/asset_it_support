package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;

import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {


    @Query(value = """
            SELECT
                b.id,
                e.first_name,
                e.last_name,
                e.email,
                e.phone,
                r.role_name,
                b.borrow_date,
                be.due_date,
                bs.borrow_status_name,
                COUNT(be.id) AS borrow_equipment_Count
            
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
            
            GROUP BY
            	b.id,
                e.first_name,
                e.last_name,
                r.role_name,
                b.borrow_date,
                e.email,
                bs.borrow_status_name
            ORDER BY
                b.borrow_date DESC;
            
            
            
            """, nativeQuery = true)
    List<BorrowView> findAllBorrow();


    @Query(value = """
            SELECT
                b.id,
                e.first_name,
                e.last_name,
                e.email,
                e.phone,
                r.role_name,
                b.borrow_date,
                be.due_date,
                bs.borrow_status_name
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
                e.role_id = :roleId
            GROUP BY
            	b.id,
                e.first_name,
                e.last_name,
                r.role_name,
                b.borrow_date,
                e.email,
                bs.borrow_status_name
            ORDER BY
                b.borrow_date DESC;
            
            
            
            """, nativeQuery = true)
    List<BorrowView> FilterRole(@Param("roleId") int id);


    @Query(value = """
            SELECT
                b.id,
                e.first_name,
                e.last_name,
                e.email,
                e.phone,
                r.role_name,
                b.borrow_date,
                be.due_date,
                bs.borrow_status_name
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
                 b.borrow_status_id = :borrowStatusId
            GROUP BY
            	b.id,
                e.first_name,
                e.last_name,
                r.role_name,
                b.borrow_date,
                e.email,
                bs.borrow_status_name
            ORDER BY
                b.borrow_date DESC;
            
            
            
            """, nativeQuery = true)
    List<BorrowView> FilterBorrowStatus(@Param("borrowStatusId") int id);


    @Query(value = """
             SELECT
                b.id,
                e.first_name,
                e.last_name,
                e.email,
                e.phone,
                r.role_name,
                b.borrow_date,
                be.due_date,
                bs.borrow_status_name
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
               (:borrowStatusId IS NULL OR b.borrow_status_id = :borrowStatusId)
                AND (:roleId IS NULL OR e.role_id = :roleId)
            
            GROUP BY
            	b.id,
                e.first_name,
                e.last_name,
                r.role_name,
                b.borrow_date,
                e.email,
                bs.borrow_status_name
            ORDER BY
                b.borrow_date DESC;
            
            """, nativeQuery = true)
    List<BorrowView> findByDynamicFilter(@Param("borrowStatusId") int borrowStatusId,
                                         @Param("roleId") int roleId);


    @Query(value = """
            SELECT
                b.id,
                e.first_name,
                e.last_name,
                e.email,
                e.phone,
                r.role_name,
                b.borrow_date,
                be.due_date,
                bs.borrow_status_name
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
                OR eq.serial_number LIKE CONCAT('%',:keyword, '%')
                OR e.first_name LIKE CONCAT('%',:keyword, '%')
                OR e.last_name LIKE CONCAT('%',:keyword, '%')
                OR CONCAT(e.first_name,' ', e.last_name) LIKE CONCAT('%', :keyword, '%')
            
            GROUP BY
            	b.id,
                e.first_name,
                e.last_name,
                r.role_name,
                b.borrow_date,
                e.email,
                bs.borrow_status_name
            ORDER BY
                b.borrow_date DESC;
            
            
            
            """, nativeQuery = true)
    List<BorrowView> searchBorrowEquipment(@Param("keyword") String keyword);


    @Query(value = """
            SELECT
                b.id,
                d.department_name,
                e.first_name,
                e.last_name,
                e.phone,
                e.email,
                r.role_name,
                b.borrow_date,
                b.reference_doc,
                beq.due_date,
                beq.return_date,
                beq.equipment_id,
                eq.id AS equipment_id,
                eq.equipment_name,
                eq.brand,
                eq.model,
                eq.license_key,
                eq.serial_number,
                bs.borrow_status_name,
                eqt.equipment_type_name
            
            
            FROM
                borrow b
            LEFT JOIN
                employee e ON e.id = b.employee_id
            LEFT JOIN
                role r ON r.id = e.id
            LEFT JOIN
                borrow_equipment beq ON beq.borrow_id = b.id
            LEFT JOIN
                borrow_status bs ON b.borrow_status_id = bs.id
            LEFT JOIN
                equipment eq ON beq.equipment_id = eq.id
            LEFT JOIN
                equipment_type eqt ON eq.equipment_type_id = eqt.id
            JOIN
            	department d ON e.department_id = d.id
            
            WHERE
                b.id = :borrowId;
            
            
            
            """, nativeQuery = true)
    List<BorrowView> selectBorrow(@Param("borrowId") Integer borrowId);


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
