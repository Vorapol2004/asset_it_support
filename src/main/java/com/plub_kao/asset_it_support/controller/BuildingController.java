package com.plub_kao.asset_it_support.controller;


import com.plub_kao.asset_it_support.entity.Building;
import com.plub_kao.asset_it_support.repository.BuildingRepository;
import com.plub_kao.asset_it_support.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
