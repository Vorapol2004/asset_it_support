package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.Building;
import com.plub_kao.asset_it_support.entity.department.Department;
import com.plub_kao.asset_it_support.entity.DepartmentRoom;
import com.plub_kao.asset_it_support.entity.department.DepartmentLocationRequest;
import com.plub_kao.asset_it_support.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;


    public Department newDepartment(Department departmentName) {
        return departmentRepository.save(departmentName);
    }


//    @Transactional
//    public String addDepartmentLocation(DepartmentLocationRequest request) {
//
//        Department department = departmentRepository.findById(request.getDepartmentId())
//                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
//
//
//        Building building = new Building();
//        building.setBuildingName(request.getBuildingName());
//        buildingRepository.save(building);
//
//
//        Floor floor = new Floor();
//        floor.setFloorName(request.getFloorName());
//        floor.setBuilding(building);
//        floorRepository.save(floor);
//
//        Room room = new Room();
//        room.setRoomName(request.getRoomName());
//        room.setFloor(floor);
//        roomRepository.save(room);
//
//
//        DepartmentRoom departmentRoom = new DepartmentRoom();
//        departmentRoom.setDepartment(department);
//        departmentRoom.setRoom(room);
//        departmentRoomRepository.save(departmentRoom);
//
//
//        return department.getDepartmentName()
//               + "  " + request.getBuildingName()
//               + "  " + request.getFloorName()
//               + " " + request.getRoomName();
//
//    }


}
