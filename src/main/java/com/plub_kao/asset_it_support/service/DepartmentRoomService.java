package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.department.View.BuildingView;
import com.plub_kao.asset_it_support.entity.department.View.RoomView;
import com.plub_kao.asset_it_support.repository.DepartmentRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentRoomService {

    @Autowired
    private DepartmentRoomRepository departmentRoomRepository;


    public List<BuildingView> getBuildingsByDepartment(Integer departmentId) {
        return departmentRoomRepository.findBuildingsByDepartment(departmentId);
    }

    public List<RoomView> getRoomsByDepartmentAndBuilding(Integer departmentId, Integer buildingId) {
        return departmentRoomRepository.findRoomsByDepartmentAndBuilding(departmentId, buildingId);
    }

}
