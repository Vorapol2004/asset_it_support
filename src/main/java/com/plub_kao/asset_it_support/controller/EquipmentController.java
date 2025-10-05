package com.plub_kao.asset_it_support.controller;


import com.plub_kao.asset_it_support.entity.employee.view.EmployeeViewDepartment;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/Type/{id}")
    public ResponseEntity<List<EquipmentView>> ChooseEquipmentType(@PathVariable Integer id) {
        List<EquipmentView> equipment = equipmentService.ChooseEquipmentType(id);
        return new ResponseEntity<>(equipment, HttpStatus.OK);
    }

    @GetMapping("/Status/{id}")
    public ResponseEntity<List<EquipmentView>> ChooseEquipmentStatus(@PathVariable Integer id) {
        List<EquipmentView> equipment = equipmentService.ChooseEquipmentStatus(id);
        return new ResponseEntity<>(equipment, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<List<EquipmentView>> searchEquipmentKeyword(@RequestParam String keyword) {
        List<EquipmentView> equipment = equipmentService.searchEquipmentKeyword(keyword);
        return ResponseEntity.ok(equipment);
    }


}
