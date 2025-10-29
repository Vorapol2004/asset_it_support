package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.repository.BuildingRepository;
import com.plub_kao.asset_it_support.repository.DepartmentRepository;
import com.plub_kao.asset_it_support.repository.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloorService {

    @Autowired
    FloorRepository floorRepository;
    @Autowired
    BuildingRepository buildingRepository;


}
