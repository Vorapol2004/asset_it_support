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


    //ค้นหาชื่อ equipment name,brand,model,serial_number,license_key ด้วย keyword
    @GetMapping("/search")
    public ResponseEntity<List<EquipmentView>> searchEquipmentKeyword(@RequestParam String keyword) {
        List<EquipmentView> equipment = equipmentService.searchEquipmentKeyword(keyword);
        return ResponseEntity.ok(equipment);
    }

//    //ฟิลเตอร์ lottype ของ equipment ทั้งหมด
//    @GetMapping("/lottype/{id}")
//    public ResponseEntity<List<EquipmentView>> FilterEquipmentLotType(@PathVariable Integer id) {
//        List<EquipmentView> equipment = equipmentService.FilterEquipmentLotType(id);
//        return new ResponseEntity<>(equipment, HttpStatus.OK);
//    }
//
//    //ฟิลเตอร์ lot ของ equipment ทั้งหมด
//    @GetMapping("/lot/{id}")
//    public ResponseEntity<List<EquipmentView>> FilterEquipmentLot(@PathVariable Integer id) {
//        List<EquipmentView> equipment = equipmentService.FilterEquipmentLot(id);
//        return new ResponseEntity<>(equipment, HttpStatus.OK);
//
//    }

    @GetMapping("/select/{id}")
    public ResponseEntity<List<EquipmentView>> FilterEquipmentSelect(@PathVariable Integer id) {
        List<EquipmentView> equipment = equipmentService.SelectEquipment(id);
        return new ResponseEntity<>(equipment, HttpStatus.OK);
    }


    @GetMapping("/filter")
    public ResponseEntity<List<EquipmentView>> filter(
            @RequestParam(required = false) Integer equipmentStatusId,
            @RequestParam(required = false) Integer equipmentTypeId
    ) {
        List<EquipmentView> equipment = equipmentService.filterStatusAndType(equipmentStatusId, equipmentTypeId);
        return ResponseEntity.ok(equipment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEquipment(@PathVariable Integer id) {
        try {
            boolean deleted = equipmentService.deleteEquipment(id);
            if (deleted) {
                return ResponseEntity.ok("ลบอุปกรณ์สำเร็จ");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("ไม่พบอุปกรณ์ที่ต้องการลบ");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    /**
     * ลบอุปกรณ์เฉพาะอุปกรณ์ที่ไม่ได้มีประวัติการยืมหรือไม่ได้ยืมอยู่
     * **/

}
