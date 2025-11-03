package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.building.BuildingView;
import com.plub_kao.asset_it_support.entity.floor.FloorView;
import com.plub_kao.asset_it_support.repository.BuildingRepository;
import com.plub_kao.asset_it_support.repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FloorService {

    private final FloorRepository floorRepository;


    public List<FloorView> filterFloor(@RequestParam Integer Id) {
        try {
            return floorRepository.filterFloorByBuilding(Id);
        } catch (Exception e) {
            throw new RuntimeException("พัง", e);
        }
    }
}
