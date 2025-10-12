package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.Building;
import com.plub_kao.asset_it_support.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;


    public Building newBuilding(Building buildingName) {
        return buildingRepository.save(buildingName);
    }
}
