package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {


    private final EquipmentRepository equipmentRepository;
    private final EquipmentStatusService equipmentStatusService;
    private final EquipmentTypeService equipmentTypeService;

    @Autowired
    private BorrowEquipmentRepository borrowEquipmentRepository;


    public List<EquipmentView> SelectEquipment(@Param("equipmentStatusId") int equipmentStatusId) {
        return equipmentRepository.selectEquipmentById(equipmentStatusId);
    }


    public List<EquipmentView> filterStatusAndType(@RequestParam Integer equipmentStatusId,
                                                   @RequestParam Integer equipmentTypeId) {
        if (equipmentStatusId != null && equipmentTypeId == null) {
            return equipmentStatusService.FilterEquipmentStatus(equipmentStatusId);
        }
        if (equipmentStatusId == null && equipmentTypeId != null) {
            return equipmentTypeService.FilterEquipmentType(equipmentTypeId);
        }
        if (equipmentStatusId != null && equipmentTypeId != null) {
            return equipmentRepository.findByDynamicFilter(equipmentStatusId, equipmentTypeId);
        }
        return equipmentRepository.findAllEquipment();
    }


    public List<EquipmentView> selectEquipmentByType(@RequestParam(required = false) Integer equipmentTypeId) {
        try {
            List<EquipmentView> equipment;
            if (equipmentTypeId != null) {
                equipment = equipmentRepository.selectEquipmentTypeId(equipmentTypeId);
            } else {
                equipment = equipmentRepository.findAllEquipment();
            }
            return equipment;
        } catch (Exception e) {
            throw new RuntimeException("พัง", e);
        }

    }


    //ค้นหาชื่อ equipment name,brand,model,serial_number,license_key ด้วย keyword
    public List<EquipmentView> searchEquipmentKeyword(@RequestParam("keyword") String keyword) {
        try {
            return equipmentRepository.searchEquipmentKeyword(keyword);
        } catch (Exception e) {
            throw new RuntimeException("พัง", e);
        }

    }


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
