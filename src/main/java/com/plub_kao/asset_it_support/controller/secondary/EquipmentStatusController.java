package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import com.plub_kao.asset_it_support.service.EquipmentStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/equipment_status")
public class EquipmentStatusController {


    private final EquipmentStatusService equipmentStatusService;

    @GetMapping("/status")
    public ResponseEntity<List<EquipmentStatusView>> findByStatus() {
        List<EquipmentStatusView> EquipmentStatusView = equipmentStatusService.findAll();
        return ResponseEntity.ok(EquipmentStatusView);

    }
}
