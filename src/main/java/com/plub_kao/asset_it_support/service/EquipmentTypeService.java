package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import com.plub_kao.asset_it_support.entity.equipmentType.EquipmentTypeView;
import com.plub_kao.asset_it_support.repository.EquipmentRepository;
import com.plub_kao.asset_it_support.repository.EquipmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentTypeService {


    private final EquipmentTypeRepository equipmentTypeRepository;
    private final EquipmentRepository equipmentRepository;

    public List<EquipmentTypeView> findAll() {
        try {
            return equipmentTypeRepository.findAllByType();
        } catch (RuntimeException e) {
            throw new RuntimeException("พัง");
        }
    }


}
