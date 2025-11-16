package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.building.Building;
import com.plub_kao.asset_it_support.entity.building.BuildingDto;
import com.plub_kao.asset_it_support.entity.building.BuildingView;
import com.plub_kao.asset_it_support.entity.department.Department;
import com.plub_kao.asset_it_support.entity.floor.Floor;
import com.plub_kao.asset_it_support.entity.floor.FloorDto;
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
    private final BuildingRepository buildingRepository;


    public List<FloorView> filterFloor(@RequestParam Integer Id) {
        try {
            return floorRepository.filterFloorByBuilding(Id);
        } catch (Exception e) {
            throw new RuntimeException("พัง", e);
        }
    }

    public FloorDto create(FloorDto FloorDto) {
        Building Building = buildingRepository.findById(FloorDto.getBuildingId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Floor Floor = new Floor();
        Floor.setFloorName(FloorDto.getFloorName());
        Floor.setBuilding(Building);

        Floor saveFloor = floorRepository.save(Floor);
        FloorDto.setId(saveFloor.getId());

        return FloorDto;
    }

    public FloorDto update(Integer id, FloorDto request) {

        Floor Floor = floorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("floor not found"));
        Building building = buildingRepository.findById(request.getBuildingId())
                .orElseThrow(() -> new RuntimeException("Building not found"));

        Floor.setFloorName(request.getFloorName());
        Floor.setBuilding(building);


        Floor updated = floorRepository.save(Floor);
        request.setId(updated.getId());
        return request;
    }

    public void deleteFloor(Integer id) {
        floorRepository.deleteById(id);
    }


}
