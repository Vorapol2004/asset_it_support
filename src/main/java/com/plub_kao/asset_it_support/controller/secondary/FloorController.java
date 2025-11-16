package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.building.BuildingDto;
import com.plub_kao.asset_it_support.entity.building.BuildingView;
import com.plub_kao.asset_it_support.entity.floor.FloorDto;
import com.plub_kao.asset_it_support.entity.floor.FloorView;
import com.plub_kao.asset_it_support.service.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public FloorDto create(@RequestBody FloorDto dto) {
        return floorService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FloorDto> updateFloor(
            @PathVariable Integer id,
            @RequestBody FloorDto request
    ) {
        FloorDto updated = floorService.update(id, request);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        floorService.deleteFloor(id);
    }
}
