package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.building.BuildingView;
import com.plub_kao.asset_it_support.entity.floor.FloorView;
import com.plub_kao.asset_it_support.service.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/floor")
public class FloorController {


    private final FloorService floorService;


    @GetMapping("/filter")
    public ResponseEntity<List<FloorView>> filterFloor(@RequestParam Integer buildingId) {
        List<FloorView> FloorView = floorService.filterFloor(buildingId);
        return ResponseEntity.ok(FloorView);
    }
}
