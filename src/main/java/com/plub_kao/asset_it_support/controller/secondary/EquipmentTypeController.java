package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import com.plub_kao.asset_it_support.entity.equipmentType.EquipmentType;
import com.plub_kao.asset_it_support.entity.equipmentType.EquipmentTypeView;
import com.plub_kao.asset_it_support.repository.EquipmentTypeRepository;
import com.plub_kao.asset_it_support.service.EquipmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/equipmenttype")
public class EquipmentTypeController {


    private final EquipmentTypeService equipmentTypeService;

    @GetMapping("/type")
    public ResponseEntity<List<EquipmentTypeView>> findByStatus() {
        List<EquipmentTypeView> EquipmentTypeView = equipmentTypeService.findAll();
        return ResponseEntity.ok(EquipmentTypeView);

    }
}
