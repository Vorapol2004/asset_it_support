package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {


    Optional<Equipment> findBySerialNumber(String serialNumber);

    Optional<Equipment> findByLicenseKey(String licenseKey);

    //ดึงรายชื่อของ equipment ที่มีอยู่ในระบบออกมาแสดงทั้งหมด
    @Query(value = """
                SELECT
                                 e.id,
                                 e.equipment_type_id,
                                 et.equipment_type_name,
            
                                 e.equipment_name,
                                 e.brand,
                                 e.model,
                                 e.serial_number,
                                 e.license_key,
            
                                 e.equipment_status_id,
                                 es.equipment_status_name,
            
                                 l.id AS lot_id ,
                                 l.lot_name,
                                 l.academic_year,
                                 l.reference_doc,
                                 l.description,
                                 l.purchase_date,
                                 l.expire_date,
            
                                 lt.id AS lot_type_id,
                                 lt.lot_type_name
            
                               FROM
                                   `equipment` e
                               LEFT JOIN
                               	equipment_type et ON et.id = e.equipment_type_id
                               LEFT JOIN
                               	equipment_status es ON es.id = e.equipment_status_id
                               LEFT JOIN
                               	lot l ON l.id = e.lot_id
                               LEFT JOIN
                               	lot_type lt ON lt.id = l.lot_type_id
                               WHERE
                                (e.serial_number = :keyword
                                OR e.license_key = :keyword)
                                AND e.equipment_status_id = 1;
            
            """, nativeQuery = true)
    List<EquipmentView> equipmentIdentifier(@Param("keyword") String keyword);

    @Query(value = """
                SELECT
                                 e.id,
                                 e.equipment_type_id,
                                 et.equipment_type_name,
            
                                 e.equipment_name,
                                 e.brand,
                                 e.model,
                                 e.serial_number,
                                 e.license_key,
            
                                 e.equipment_status_id,
                                 es.equipment_status_name,
            
                                 l.id AS lot_id ,
                                 l.lot_name,
                                 l.academic_year,
                                 l.reference_doc,
                                 l.description,
                                 l.purchase_date,
                                 l.expire_date,
            
                                 lt.id AS lot_type_id,
                                 lt.lot_type_name
            
                               FROM
                                   `equipment` e
                               LEFT JOIN
                               	equipment_type et ON et.id = e.equipment_type_id
                               LEFT JOIN
                               	equipment_status es ON es.id = e.equipment_status_id
                               LEFT JOIN
                               	lot l ON l.id = e.lot_id
                               LEFT JOIN
                               	lot_type lt ON lt.id = l.lot_type_id
                               ORDER BY
                                l.purchase_date DESC, l.id;
            
            """, nativeQuery = true)
    List<EquipmentView> findAllEquipment();

    //ดึงรายชื่อของ equipment ที่มีอยู่ในระบบออกมาแสดงทั้งหมด
    @Query(value = """
                SELECT
                                 e.id,
                                 e.equipment_type_id,
                                 et.equipment_type_name,
            
                                 e.equipment_name,
                                 e.brand,
                                 e.model,
                                 e.serial_number,
                                 e.license_key,
            
                                 e.equipment_status_id,
                                 es.equipment_status_name,
            
                                 l.id AS lot_id ,
                                 l.lot_name,
                                 l.academic_year,
                                 l.reference_doc,
                                 l.description,
                                 l.purchase_date,
                                 l.expire_date,
            
                                 lt.id AS lot_type_id,
                                 lt.lot_type_name
            
                               FROM
                                   `equipment` e
                               LEFT JOIN
                               	equipment_type et ON et.id = e.equipment_type_id
                               LEFT JOIN
                               	equipment_status es ON es.id = e.equipment_status_id
                               LEFT JOIN
                               	lot l ON l.id = e.lot_id
                               LEFT JOIN
                               	lot_type lt ON lt.id = l.lot_type_id
                               WHERE
                                 e.equipment_status_id = 1
                                AND et.id = :equipmentTypeId;
            """, nativeQuery = true)
    List<EquipmentView> selectEquipmentTypeId(@Param("equipmentTypeId") Integer Id);


    @Query(value = """
                SELECT
                                 e.id,
                                 e.equipment_type_id,
                                 et.equipment_type_name,
            
                                 e.equipment_name,
                                 e.brand,
                                 e.model,
                                 e.serial_number,
                                 e.license_key,
            
                                 e.equipment_status_id,
                                 es.equipment_status_name,
            
                                 l.id AS lot_id ,
                                 l.lot_name,
                                 l.academic_year,
                                 l.reference_doc,
                                 l.description,
                                 l.purchase_date,
                                 l.expire_date,
            
                                 lt.id AS lot_type_id,
                                 lt.lot_type_name
            
                               FROM
                                   `equipment` e
                               LEFT JOIN
                               	equipment_type et ON et.id = e.equipment_type_id
                               LEFT JOIN
                               	equipment_status es ON es.id = e.equipment_status_id
                               LEFT JOIN
                               	lot l ON l.id = e.lot_id
                               LEFT JOIN
                               	lot_type lt ON lt.id = l.lot_type_id
            
                            WHERE
                                e.id = :equipmentId
            
            """, nativeQuery = true)
    List<EquipmentView> selectEquipmentById(@Param("equipmentId") Integer equipmentId);


    @Query(value = """
             SELECT
                                 e.id,
                                 e.equipment_type_id,
                                 et.equipment_type_name,
            
                                 e.equipment_name,
                                 e.brand,
                                 e.model,
                                 e.serial_number,
                                 e.license_key,
            
                                 e.equipment_status_id,
                                 es.equipment_status_name,
            
                                 l.id AS lot_id ,
                                 l.lot_name,
                                 l.academic_year,
                                 l.reference_doc,
                                 l.description,
                                 l.purchase_date,
                                 l.expire_date,
            
                                 lt.id AS lot_type_id,
                                 lt.lot_type_name
            
                               FROM
                                   `equipment` e
                               LEFT JOIN
                               	equipment_type et ON et.id = e.equipment_type_id
                               LEFT JOIN
                               	equipment_status es ON es.id = e.equipment_status_id
                               LEFT JOIN
                               	lot l ON l.id = e.lot_id
                               LEFT JOIN
                               	lot_type lt ON lt.id = l.lot_type_id
                               WHERE
                                es.id = :equipmentStatusId
                               ORDER BY
                                l.purchase_date DESC, l.id;
            """, nativeQuery = true)
    List<EquipmentView> FilterEquipmentStatus(@Param("equipmentStatusId") int Id);


    @Query(value = """
             SELECT
                                 e.id,
                                 e.equipment_type_id,
                                 et.equipment_type_name,
            
                                 e.equipment_name,
                                 e.brand,
                                 e.model,
                                 e.serial_number,
                                 e.license_key,
            
                                 e.equipment_status_id,
                                 es.equipment_status_name,
            
                                 l.id AS lot_id ,
                                 l.lot_name,
                                 l.academic_year,
                                 l.reference_doc,
                                 l.description,
                                 l.purchase_date,
                                 l.expire_date,
            
                                 lt.id AS lot_type_id,
                                 lt.lot_type_name
            
                               FROM
                                   `equipment` e
                               LEFT JOIN
                               	equipment_type et ON et.id = e.equipment_type_id
                               LEFT JOIN
                               	equipment_status es ON es.id = e.equipment_status_id
                               LEFT JOIN
                               	lot l ON l.id = e.lot_id
                               LEFT JOIN
                               	lot_type lt ON lt.id = l.lot_type_id
                               WHERE
                                et.id = :equipmentTypeId
                               ORDER BY
                                l.purchase_date DESC, l.id;
            """, nativeQuery = true)
    List<EquipmentView> FilterEquipmentType(@Param("equipmentTypeId") int id);


    @Query(value = """
             SELECT
                                 e.id,
                                 e.equipment_type_id,
                                 et.equipment_type_name,
            
                                 e.equipment_name,
                                 e.brand,
                                 e.model,
                                 e.serial_number,
                                 e.license_key,
            
                                 e.equipment_status_id,
                                 es.equipment_status_name,
            
                                 l.id AS lot_id ,
                                 l.lot_name,
                                 l.academic_year,
                                 l.reference_doc,
                                 l.description,
                                 l.purchase_date,
                                 l.expire_date,
            
                                 lt.id AS lot_type_id,
                                 lt.lot_type_name
            
                               FROM
                                   `equipment` e
                               LEFT JOIN
                               	equipment_type et ON et.id = e.equipment_type_id
                               LEFT JOIN
                               	equipment_status es ON es.id = e.equipment_status_id
                               LEFT JOIN
                               	lot l ON l.id = e.lot_id
                               LEFT JOIN
                               	lot_type lt ON lt.id = l.lot_type_id
                               WHERE
                               (:equipmentStatusId IS NULL OR e.equipment_status_id = :equipmentStatusId)
                               AND (:equipmentTypeId IS NULL OR e.equipment_type_id = :equipmentTypeId)
                               ORDER BY
                                l.purchase_date DESC, l.id;
            """, nativeQuery = true)
    List<EquipmentView> findByDynamicFilter(@Param("equipmentStatusId") int equipmentStatusId,
                                            @Param("equipmentTypeId") int equipmentTypeId);


    //ค้นหาชื่อ equipment name,brand,model,serial_number,license_key ด้วย keyword
    @Query(value = """
             SELECT
                                  e.id,
                                  e.equipment_type_id,
                                  et.equipment_type_name,
            
                                  e.equipment_name,
                                  e.brand,
                                  e.model,
                                  e.serial_number,
                                  e.license_key,
            
                                  e.equipment_status_id,
                                  es.equipment_status_name,
            
                                  l.id AS lot_id ,
                                  l.lot_name,
                                  l.academic_year,
                                  l.reference_doc,
                                  l.description,
                                  l.purchase_date,
                                  l.expire_date,
            
                                  lt.id AS lot_type_id,
                                  lt.lot_type_name
            
                                FROM
                                    `equipment` e
                                LEFT JOIN
                                	equipment_type et ON et.id = e.equipment_type_id
                                LEFT JOIN
                                	equipment_status es ON es.id = e.equipment_status_id
                                LEFT JOIN
                                	lot l ON l.id = e.lot_id
                                LEFT JOIN
                                	lot_type lt ON lt.id = l.lot_type_id
             WHERE
                (
                    e.equipment_name LIKE CONCAT('%',:keyword,'%')
                    OR e.brand LIKE CONCAT('%',:keyword,'%')
                    OR e.model LIKE CONCAT('%',:keyword,'%')
                    OR e.serial_number LIKE CONCAT('%',:keyword,'%')
                    OR e.license_key LIKE CONCAT('%',:keyword,'%')
                )
            """, nativeQuery = true)
    List<EquipmentView> searchEquipmentKeyword(@Param("keyword") String keyword);


}






