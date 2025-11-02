package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import com.plub_kao.asset_it_support.entity.lot.LotTypeView;
import com.plub_kao.asset_it_support.repository.LotTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LotTypeService {

    public final LotTypeRepository lotTypeRepository;

    public List<LotTypeView> findAll() {
        try {
            return lotTypeRepository.findAllByType();
        } catch (RuntimeException e) {
            throw new RuntimeException("พัง", e);
        }
    }
}
