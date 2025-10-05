package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {


    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<EquipmentView> findAllEquipment() {
        return equipmentRepository.findAllEquipment();
    }

    public List<EquipmentView> findEquipmentAll(@Param("keyword") String keyword) {
        return equipmentRepository.findEquipmentAll(keyword);
    }
}
