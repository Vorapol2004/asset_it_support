package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import com.plub_kao.asset_it_support.repository.EquipmentRepository;
import com.plub_kao.asset_it_support.repository.EquipmentStatusRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentStatusService {


    private final EquipmentStatusRepository equipmentStatusRepository;
    private final EquipmentRepository equipmentRepository;


    public List<EquipmentStatusView> findAll() {
        try {
            return equipmentStatusRepository.findAllByStatus();
        } catch (RuntimeException e) {
            throw new RuntimeException("พัง", e);
        }
    }

    public List<EquipmentView> FilterEquipmentStatus(Integer id) {
        try {
            return equipmentRepository.FilterEquipmentStatus(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("พัง", e);
        }
    }


}

