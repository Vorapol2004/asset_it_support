package com.plub_kao.asset_it_support.controller.main;


import com.plub_kao.asset_it_support.entity.EquipmentStatus;
import com.plub_kao.asset_it_support.entity.EquipmentType;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentTypeView;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.repository.EquipmentStatusRepository;
import com.plub_kao.asset_it_support.repository.EquipmentTypeRepository;
import com.plub_kao.asset_it_support.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {


    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private EquipmentStatusRepository equipmentStatusRepository;

    @Autowired
    private EquipmentTypeRepository equipmentTypeRepository;


    //ดึงรายชื่อของ equipment ที่มีอยู่ในระบบออกมาแสดงทั้งหมด
    @GetMapping("/all")
    public ResponseEntity<List<EquipmentView>> findAllEquipment() {
        if (equipmentService.findAllEquipment().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(equipmentService.findAllEquipment());
    }

    @GetMapping("/status/dropdown")
    public List<EquipmentStatus> getAllStatuses() {
        return equipmentStatusRepository.findAll();
    }

    @GetMapping("/type/dropdown")
    public List<EquipmentType> getAllTypeStatuses() {
        return equipmentTypeRepository.findAll();
    }


    //ค้นหาชื่อ equipment name,brand,model,serial_number,license_key ด้วย keyword
    @GetMapping("/search")
    public ResponseEntity<List<EquipmentView>> searchEquipmentKeyword(@RequestParam String keyword) {
        List<EquipmentView> equipment = equipmentService.searchEquipmentKeyword(keyword);
        return ResponseEntity.ok(equipment);
    }

    //ฟิลเตอร์ lottype ของ equipment ทั้งหมด
    @GetMapping("/lottype/{id}")
    public ResponseEntity<List<EquipmentView>> FilterEquipmentLotType(@PathVariable Integer id) {
        List<EquipmentView> equipment = equipmentService.FilterEquipmentLotType(id);
        return new ResponseEntity<>(equipment, HttpStatus.OK);
    }

    //ฟิลเตอร์ lot ของ equipment ทั้งหมด
    @GetMapping("/lot/{id}")
    public ResponseEntity<List<EquipmentView>> FilterEquipmentLot(@PathVariable Integer id) {
        List<EquipmentView> equipment = equipmentService.FilterEquipmentLot(id);
        return new ResponseEntity<>(equipment, HttpStatus.OK);

    }

    @GetMapping("/select/{id}")
    public ResponseEntity<List<EquipmentView>> FilterEquipmentSelect(@PathVariable Integer id) {
        List<EquipmentView> equipment = equipmentService.SelectEquipment(id);
        return new ResponseEntity<>(equipment, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EquipmentView>> filter(
            @RequestParam(required = false) Integer equipmentStatus,
            @RequestParam(required = false) Integer equipmentType
    ) {
        List<EquipmentView> equipment = equipmentService.filterStatusAndType(equipmentStatus, equipmentType);
        return ResponseEntity.ok(equipment);
    }
}
