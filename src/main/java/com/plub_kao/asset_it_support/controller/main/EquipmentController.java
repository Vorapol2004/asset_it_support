package com.plub_kao.asset_it_support.controller.main;


import com.plub_kao.asset_it_support.entity.equipment.EquipmentResponse;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.entity.lot.LotRequest;
import com.plub_kao.asset_it_support.entity.lot.LotResponse;
import com.plub_kao.asset_it_support.service.EquipmentService;
import com.plub_kao.asset_it_support.service.LotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/equipment")
public class EquipmentController {


    private final EquipmentService equipmentService;
    private final LotService lotService;

    @GetMapping("/all")
    public ResponseEntity<List<EquipmentView>> SelectAll() {
        List<EquipmentView> equipmentAll = equipmentService.findAll();
        return new ResponseEntity<>(equipmentAll, HttpStatus.OK);
    }


    @GetMapping("/identifier")
    public ResponseEntity<List<EquipmentView>> selectEquipmentIdentifier(
            @RequestParam(required = false) String keyword
    ) {
        List<EquipmentView> equipment = equipmentService.equipmentIdentifier(keyword);
        return ResponseEntity.ok(equipment);
    }

    @GetMapping("/select_equipment_type")
    public ResponseEntity<List<EquipmentView>> selectEquipmentType(@RequestParam(required = false) Integer equipmentId) {
        List<EquipmentView> equipment = equipmentService.selectEquipmentByType(equipmentId);
        return ResponseEntity.ok(equipment);
    }


    @GetMapping("/select/{id}")
    public ResponseEntity<List<EquipmentView>> FilterEquipmentSelect(@PathVariable Integer id) {
        List<EquipmentView> equipment = equipmentService.SelectEquipment(id);
        return new ResponseEntity<>(equipment, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EquipmentView>> filter(
            @RequestParam(required = false) Integer equipmentStatusId,
            @RequestParam(required = false) Integer equipmentTypeId,
            @RequestParam(required = false) String keyword
    ) {
        List<EquipmentView> result = equipmentService.searchDynamic(
                equipmentStatusId,
                equipmentTypeId,
                keyword
        );
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<LotResponse> addLot(@RequestBody LotRequest request) {
        try {
            LotResponse savedLot = lotService.addLot(request);
            return new ResponseEntity<>(savedLot, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<EquipmentResponse> editEquipment(@RequestBody LotRequest.EquipmentRequest request) {
        return new ResponseEntity<>(equipmentService.editEquipment(request), HttpStatus.OK);
    }

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
