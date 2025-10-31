package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.Building;
import com.plub_kao.asset_it_support.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/building")
public class BuildingController {

    @Autowired
    private final BuildingService buildingService;


    @PostMapping("/add")
    public Building add(@RequestBody Building building) {
        return buildingService.newBuilding(building);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Building>> getAllBuildings() {
        try {
            return ResponseEntity.ok(buildingService.getAllBuildings());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}