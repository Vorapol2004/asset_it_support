package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatus;
import com.plub_kao.asset_it_support.entity.equipmentType.EquipmentType;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;

import com.plub_kao.asset_it_support.entity.lot.*;
import com.plub_kao.asset_it_support.entity.lotType.LotType;
import com.plub_kao.asset_it_support.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LotService {


    private final LotRepository lotRepository;
    private final LotTypeRepository lotTypeRepository;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;
    private final EquipmentStatusRepository equipmentStatusRepository;


    public LotResponse addLot(LotRequest request) {

        LotType lotType = lotTypeRepository.findById(request.getLotTypeId())
                .orElseThrow(() -> new RuntimeException("LotType not found"));

        Lot newLot = new Lot();
        newLot.setLotName(request.getLotName());
        newLot.setLotType(lotType);
        newLot.setAcademicYear(request.getAcademicYear());
        newLot.setPurchaseDate(request.getPurchaseDate());
        newLot.setExpireDate(request.getExpireDate());
        newLot.setReferenceDoc(request.getReferenceDoc());
        newLot.setDescription(request.getDescription());

        Lot savedLot = lotRepository.save(newLot);

        if (request.getEquipmentList() != null && !request.getEquipmentList().isEmpty()) {
            List<Equipment> equipments = new ArrayList<>();

            for (LotRequest.EquipmentRequest EquipmentRequest : request.getEquipmentList()) {
                Equipment eq = new Equipment();
                eq.setEquipmentName(EquipmentRequest.getEquipmentName() != null && !EquipmentRequest.getEquipmentName().isBlank()
                        ? EquipmentRequest.getEquipmentName() : EquipmentRequest.getBrand() + " " + EquipmentRequest.getModel());

                EquipmentType EquipmentType = equipmentTypeRepository.findById(EquipmentRequest.getEquipmentTypeId())
                        .orElseThrow(() -> new RuntimeException("EquipmentType not found"));

                EquipmentStatus defaultStatus = equipmentStatusRepository.findById(1)
                        .orElseThrow(() -> new RuntimeException("Default EquipmentStatus (id=1) not found"));

                eq.setEquipmentStatus(defaultStatus);
                eq.setEquipmentType(EquipmentType);
                eq.setBrand(EquipmentRequest.getBrand());
                eq.setModel(EquipmentRequest.getModel());
                eq.setSerialNumber(EquipmentRequest.getSerialNumber());
                eq.setLicenseKey(EquipmentRequest.getLicenseKey());
                eq.setLot(newLot);

                equipments.add(equipmentRepository.save(eq));
            }

            newLot.setEquipmentList(equipments);

        }

        return new LotResponse(savedLot);
    }


}
