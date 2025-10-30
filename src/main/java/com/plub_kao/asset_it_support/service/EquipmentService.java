package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {


    @Autowired
    private EquipmentRepository equipmentRepository;


    //ดึงรายชื่อของ equipment ที่มีอยู่ในระบบออกมาแสดงทั้งหมด
    public List<EquipmentView> findAllEquipment() {
        return equipmentRepository.findAllEquipment();
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
}
