package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.building.Building;
import com.plub_kao.asset_it_support.entity.building.BuildingDto;
import com.plub_kao.asset_it_support.entity.building.BuildingView;
import com.plub_kao.asset_it_support.entity.department.DepartmentDto;
import com.plub_kao.asset_it_support.entity.department.DepartmentView;
import com.plub_kao.asset_it_support.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/building")
public class BuildingController {


    private final BuildingService buildingService;


    @GetMapping("/filter")
    public ResponseEntity<List<BuildingView>> filterBuilding(@RequestParam Integer departmentId) {
        List<BuildingView> BuildingView = buildingService.filterBuilding(departmentId);
        return ResponseEntity.ok(BuildingView);

    }

    @PostMapping("/create")
    public BuildingDto create(@RequestBody BuildingDto dto) {
        return buildingService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildingDto> updateBuilding(
            @PathVariable Integer id,
            @RequestBody BuildingDto request
    ) {
        BuildingDto updated = buildingService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        buildingService.deleteBuilding(id);
    }


}
