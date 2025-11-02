package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.equipmentType.EquipmentTypeView;
import com.plub_kao.asset_it_support.entity.lot.LotTypeView;
import com.plub_kao.asset_it_support.service.LotTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lottype")
public class LotTypeController {

    private final LotTypeService lotTypeService;

    @GetMapping("/type")
    public ResponseEntity<List<LotTypeView>> findByStatus() {
        List<LotTypeView> LotTypeView = lotTypeService.findAll();
        return ResponseEntity.ok(LotTypeView);

    }

}
