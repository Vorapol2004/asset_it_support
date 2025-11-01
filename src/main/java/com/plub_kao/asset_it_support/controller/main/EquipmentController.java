package com.plub_kao.asset_it_support.controller.main;


import com.plub_kao.asset_it_support.entity.EquipmentStatus;
import com.plub_kao.asset_it_support.entity.EquipmentType;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.entity.lot.Lot;
import com.plub_kao.asset_it_support.entity.lot.LotRequest;
import com.plub_kao.asset_it_support.entity.lot.LotType;
import com.plub_kao.asset_it_support.repository.EquipmentStatusRepository;
import com.plub_kao.asset_it_support.repository.EquipmentTypeRepository;
import com.plub_kao.asset_it_support.repository.LotRepository;
import com.plub_kao.asset_it_support.repository.LotTypeRepository;
import com.plub_kao.asset_it_support.service.EquipmentService;
import com.plub_kao.asset_it_support.service.LotService;
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
    private LotService lotService;


    //ดึงรายชื่อของ equipment ที่มีอยู่ในระบบออกมาแสดงทั้งหมด
    @GetMapping("/all")
    public ResponseEntity<List<EquipmentView>> findAllEquipment() {
        if (equipmentService.findAllEquipment().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(equipmentService.findAllEquipment());
    }

    @GetMapping("/equipmentStatus/dropDown")
    public List<EquipmentStatus> getAllStatuses() {
        return equipmentService.findAllEquipmentStatus();
    }


    @GetMapping("/equipmentType/dropDown")
    public List<EquipmentType> getAllTypeStatuses() {
        return equipmentService.findAllEquipmentType();
    }

    @GetMapping("/lotType/dropDown")
    public List<LotType> getAllLotTypeStatuses() {
        return equipmentService.findAllLotType();
    }


    //ค้นหาชื่อ equipment name,brand,model,serial_number,license_key ด้วย keyword
    @GetMapping("/search")
    public ResponseEntity<List<EquipmentView>> searchEquipmentKeyword(@RequestParam String keyword) {
        List<EquipmentView> equipment = equipmentService.searchEquipmentKeyword(keyword);
        return ResponseEntity.ok(equipment);
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

    @PostMapping("/add")
    public ResponseEntity<Lot> addLot(@RequestBody LotRequest request) {
        try {
            Lot savedLot = lotService.addLot(request);
            return new ResponseEntity<>(savedLot, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
