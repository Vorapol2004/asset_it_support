package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.building.Building;
import com.plub_kao.asset_it_support.entity.building.BuildingDto;
import com.plub_kao.asset_it_support.entity.building.BuildingView;
import com.plub_kao.asset_it_support.entity.department.Department;
import com.plub_kao.asset_it_support.entity.department.DepartmentDto;
import com.plub_kao.asset_it_support.repository.BuildingRepository;
import com.plub_kao.asset_it_support.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingService {


    private final BuildingRepository buildingRepository;
    private final DepartmentRepository departmentRepository;

    public List<BuildingView> filterBuilding(@RequestParam Integer Id) {
        try {
            return buildingRepository.filterBuildingByDepartment(Id);
        } catch (Exception e) {
            throw new RuntimeException("พัง", e);
        }
    }

    public BuildingDto create(BuildingDto BuildingDto) {
        Department Department = departmentRepository.findById(BuildingDto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Building building = new Building();
        building.setBuildingName(BuildingDto.getBuildingName());
        building.setDepartment(Department);
        Building savedBuilding = buildingRepository.save(building);

        BuildingDto.setId(savedBuilding.getId());

        return BuildingDto;
    }

    public BuildingDto update(Integer id, BuildingDto request) {

        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Building not found"));
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        building.setBuildingName(request.getBuildingName());
        building.setDepartment(department);
        

        Building updated = buildingRepository.save(building);
        request.setId(updated.getId());
        return request;
    }


    public void deleteBuilding(Integer id) {
        buildingRepository.deleteById(id);
    }


}
