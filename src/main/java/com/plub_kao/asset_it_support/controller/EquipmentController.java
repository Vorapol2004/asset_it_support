package com.plub_kao.asset_it_support.controller;


import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {


    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/all")
    public List<EquipmentView> findAllEquipment() {
        return equipmentService.findAllEquipment();
    }


    @GetMapping("/search")
    public ResponseEntity<List<EquipmentView>> searchEmployees(@RequestParam String keyword) {
        List<EquipmentView> equipment = equipmentService.findEquipmentAll(keyword);
        return ResponseEntity.ok(equipment);
    }


}
