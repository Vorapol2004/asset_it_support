package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.lot.Lot;
import com.plub_kao.asset_it_support.entity.lot.LotType;
import com.plub_kao.asset_it_support.repository.EquipmentRepository;
import com.plub_kao.asset_it_support.repository.LotRepository;
import com.plub_kao.asset_it_support.repository.LotTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotService {

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private LotTypeRepository lotTypeRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    public Lot addLot(Lot request) {

        LotType lotType = lotTypeRepository.findById(request.getLotType().getId())
                .orElseThrow(() -> new RuntimeException("LotType not found"));

        Lot newLot = new Lot();
        newLot.setLotName(request.getLotName());
        newLot.setAcademicYear(request.getAcademicYear());
        newLot.setReferenceDoc(request.getReferenceDoc());
        newLot.setDescription(request.getDescription());
        newLot.setPurchaseDate(request.getPurchaseDate());
        newLot.setExpireDate(request.getExpireDate());
        newLot.setLotType(lotType);

        Lot savedLot = lotRepository.save(newLot);

        if (request.getEquipmentList() != null && !request.getEquipmentList().isEmpty()) {
            for (Equipment eq : request.getEquipmentList()) {

                if (eq.getEquipmentName() == null || eq.getEquipmentName().isBlank()) {
                    eq.setEquipmentName(eq.getBrand() + " " + eq.getModel());
                }

                eq.setLot(savedLot);
                equipmentRepository.save(eq);
            }
        }

        return savedLot;
    }


}
