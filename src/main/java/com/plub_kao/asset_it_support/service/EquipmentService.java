package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.equipment.Equipment;
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

    //ฟิลเตอร์ Type ของ equipment ทั้งหมด
    public List<EquipmentView> FilterEquipmentType(@Param("equipmentTypeId") int equipmentTypeId) {
        return equipmentRepository.FilterEquipmentType(equipmentTypeId);
    }

    //ฟิลเตอร์ Status ของ equipment ทั้งหมด
    public List<EquipmentView> FilterEquipmentStatus(@Param("equipmentStatusId") int equipmentStatusId) {
        return equipmentRepository.FilterEquipmentStatus(equipmentStatusId);
    }

    //ค้นหาชื่อ equipment name,brand,model,serial_number,license_key ด้วย keyword
    public List<EquipmentView> searchEquipmentKeyword(@Param("keyword") String keyword) {
        return equipmentRepository.searchEquipmentKeyword(keyword);
    }
}
