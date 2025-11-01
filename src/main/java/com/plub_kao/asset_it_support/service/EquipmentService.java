package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.repository.BorrowEquipmentRepository;
import com.plub_kao.asset_it_support.repository.EquipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {


    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private LotService lotService;


    //ดึงรายชื่อของ equipment ที่มีอยู่ในระบบออกมาแสดงทั้งหมด
    public List<EquipmentView> findAllEquipment() {
        return equipmentRepository.findAllEquipment();
    }

    public List<EquipmentView> SelectEquipment(@Param("equipmentStatusId") int equipmentStatusId) {
        return equipmentRepository.selectEquipmentById(equipmentStatusId);
    }

    //ฟิลเตอร์ Status และ Type ของ equipment ทั้งหมด
    public List<EquipmentView> filterStatusAndType(Integer equipmentStatusId, Integer equipmentTypeId) {
        if (equipmentStatusId != null && equipmentTypeId == null) {
            return equipmentRepository.FilterEquipmentStatus(equipmentStatusId);
        }
        if (equipmentStatusId == null && equipmentTypeId != null) {
            return equipmentRepository.FilterEquipmentType(equipmentTypeId);
        }
        if (equipmentStatusId != null && equipmentTypeId != null) {
            return equipmentRepository.findByDynamicFilter(equipmentStatusId, equipmentTypeId);
        }
        return equipmentRepository.findAllEquipment();
    }

    //ฟิลเตอร์ add


    //ค้นหาชื่อ equipment name,brand,model,serial_number,license_key ด้วย keyword
    public List<EquipmentView> searchEquipmentKeyword(@Param("keyword") String keyword) {
        return equipmentRepository.searchEquipmentKeyword(keyword);
    }

    //ฟิลเตอร์ lot ของ equipment ทั้งหมด
    public List<EquipmentView> FilterEquipmentLotType(@Param("equipmentLotTypeId") int equipmentLotTypeId) {
        return equipmentRepository.FilterEquipmentLotType(equipmentLotTypeId);
    }

    //ฟิลเตอร์ lot ของ equipment ทั้งหมด
    public List<EquipmentView> FilterEquipmentLot(@Param("equipmentLotId") int equipmentLotId) {
        return equipmentRepository.FilterEquipmentLot(equipmentLotId);
    }

    @Autowired
    private BorrowEquipmentRepository borrowEquipmentRepository; // เพิ่มบรรทัดนี้

    @Transactional
    public boolean deleteEquipment(Integer id) {
        // ตรวจว่ามีอยู่ไหม
        if (!equipmentRepository.existsById(id)) {
            return false;
        }

        // ตรวจว่ามีการอ้างอิงใน borrow_equipment ไหม
        int borrowCount = borrowEquipmentRepository.countByEquipmentId(id);
        if (borrowCount > 0) {
            throw new RuntimeException("ไม่สามารถลบอุปกรณ์ได้ เนื่องจากอุปกรณ์นี้เคยถูกยืมหรือมีประวัติการใช้งาน");
        }

        // ถ้าไม่เคยถูกยืม → ลบได้เลย
        equipmentRepository.deleteById(id);
        return true;
    }



}
