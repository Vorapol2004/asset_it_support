package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.building.Building;
import com.plub_kao.asset_it_support.entity.building.BuildingView;
import com.plub_kao.asset_it_support.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingService {


    private final BuildingRepository buildingRepository;

    public List<BuildingView> filterBuilding(@RequestParam Integer Id) {
        try {
            return buildingRepository.filterBuildingByDepartment(Id);
        } catch (Exception e) {
            throw new RuntimeException("พัง", e);
        }
    }

}
