package com.plub_kao.asset_it_support.controller.main;


import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
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


    //ดึงรายชื่อของ equipment ที่มีอยู่ในระบบออกมาแสดงทั้งหมด
    @GetMapping("/all")
    public ResponseEntity<List<EquipmentView>> findAllEquipment() {
        if (equipmentService.findAllEquipment().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(equipmentService.findAllEquipment());
    }

    //ฟิลเตอร์ Type ของ equipment ทั้งหมด
    @GetMapping("/Type/{id}")
    public ResponseEntity<List<EquipmentView>> FilterEquipmentType(@PathVariable Integer id) {
        List<EquipmentView> equipment = equipmentService.FilterEquipmentType(id);
        return new ResponseEntity<>(equipment, HttpStatus.OK);
    }

    //ฟิลเตอร์ Status ของ equipment ทั้งหมด
    @GetMapping("/Status/{id}")
    public ResponseEntity<List<EquipmentView>> FilterEquipmentStatus(@PathVariable Integer id) {
        List<EquipmentView> equipment = equipmentService.FilterEquipmentStatus(id);
        return new ResponseEntity<>(equipment, HttpStatus.OK);
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


}
