package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

    @Query(value = """
                SELECT
                    `id`,
                    `equipment_name`,
                    `brand`,
                    `model`,
                    `serial_number`,
                    `equipment_status_id`,
                    `equipment_type_id`,
                    `lot_id`,
                    `license_key`
            
                FROM
                    `equipment`
            
            """, nativeQuery = true)
    List<EquipmentView> findAllEquipment();


    @Query(value = """
            SELECT
                e.`id`,
                e.`equipment_name`,
                e.`brand`,
                e.`model`,
                e.`serial_number`,
                e.`license_key`,
                es.equipment_status_name,
                et.equipment_type_name
            FROM
                `equipment` e
            LEFT JOIN equipment_status es ON
                es.id = e.equipment_status_id
            LEFT JOIN equipment_type et ON
                et.id = e.equipment_type_id
            LEFT JOIN lot l ON
                l.id = e.lot_id
            WHERE
                (
                    e.equipment_name LIKE CONCAT('%',:keyword,'%')
                    OR e.brand LIKE CONCAT('%',:keyword,'%')
                    OR e.model LIKE CONCAT('%',:keyword,'%')
                )
            """, nativeQuery = true)
    List<EquipmentView> findEquipmentAll(@Param("keyword") String keyword);


//    @Query(value = """
//            SELECT
//                e.`id`,
//                e.`equipment_name`,
//                e.`brand`,
//                e.`model`,
//            	e.`serial_number`,
//                e.`license_key`,
//                es.equipment_status_name,
//                et.equipment_type_name
//
//            FROM
//                `equipment` e
//            LEFT JOIN
//            	equipment_status es ON es.id = e.equipment_status_id
//            LEFT JOIN
//            	equipment_type et ON et.id = e.equipment_type_id
//            LEFT JOIN
//            	lot l ON l.id = e.lot_id
//            WHERE
//            	es.id = :equipment
//            """, nativeQuery = true)

}






