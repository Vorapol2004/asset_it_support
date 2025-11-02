package com.plub_kao.asset_it_support.controller.main;


import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatus;
import com.plub_kao.asset_it_support.entity.equipmentType.EquipmentType;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.entity.lot.LotRequest;
import com.plub_kao.asset_it_support.entity.lot.LotResponse;
import com.plub_kao.asset_it_support.entity.lot.LotType;
import com.plub_kao.asset_it_support.service.EquipmentService;
import com.plub_kao.asset_it_support.service.LotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/equipment")
public class EquipmentController {


    private final EquipmentService equipmentService;


    @GetMapping("/search")
    public ResponseEntity<List<EquipmentView>> searchEquipmentKeyword(@RequestParam String keyword) {
        List<EquipmentView> equipment = equipmentService.searchEquipmentKeyword(keyword);
        return ResponseEntity.ok(equipment);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EquipmentView>> filter(
            @RequestParam(required = false) Integer equipmentStatus,
            @RequestParam(required = false) Integer equipmentType
    ) {
        List<EquipmentView> equipment = equipmentService.filterStatusAndType(equipmentStatus, equipmentType);
        return ResponseEntity.ok(equipment);
    }

    @GetMapping("/select_equipment_type")
    public ResponseEntity<List<EquipmentView>> selectEquipmentType(@RequestParam(required = false) Integer equipmentType) {
        List<EquipmentView> equipment = equipmentService.selectEquipment(equipmentType);
        return ResponseEntity.ok(equipment);
    }


    @GetMapping("/select/{id}")
    public ResponseEntity<List<EquipmentView>> FilterEquipmentSelect(@PathVariable Integer id) {
        List<EquipmentView> equipment = equipmentService.SelectEquipment(id);
        return new ResponseEntity<>(equipment, HttpStatus.OK);
    }


//    @PostMapping("/add")
//    public ResponseEntity<LotResponse> addLot(@RequestBody LotRequest request) {
//        try {
//            LotResponse savedLot = lotService.addLot(request);
//            return new ResponseEntity<>(savedLot, HttpStatus.CREATED);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable Integer id) {
        try {
            boolean deleted = equipmentService.deleteEquipment(id);
            if (deleted) {
                return ResponseEntity.ok("ลบอุปกรณ์เรียบร้อยแล้ว");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("ไม่พบอุปกรณ์ที่ต้องการลบ");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("เกิดข้อผิดพลาดภายในระบบ");
        }
    }


}
