package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.EquipmentStatus;
import com.plub_kao.asset_it_support.entity.EquipmentType;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.equipment.EquipmentRequest;
import com.plub_kao.asset_it_support.entity.lot.Lot;
import com.plub_kao.asset_it_support.entity.lot.LotRequest;
import com.plub_kao.asset_it_support.entity.lot.LotType;
import com.plub_kao.asset_it_support.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LotService {

    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private LotTypeRepository lotTypeRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private EquipmentTypeRepository equipmentTypeRepository;
    @Autowired
    private EquipmentStatusRepository equipmentStatusRepository;


    public Lot addLot(LotRequest request) {

        LotType lotType = lotTypeRepository.findById(request.getLotTypeId())
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
            List<Equipment> equipments = new ArrayList<>();

            for (EquipmentRequest eqReq : request.getEquipmentList()) {
                Equipment eq = new Equipment();
                eq.setEquipmentName(eqReq.getEquipmentName() != null && !eqReq.getEquipmentName().isBlank()
                        ? eqReq.getEquipmentName() : eqReq.getBrand() + " " + eqReq.getModel());

                EquipmentType eqType = equipmentTypeRepository.findById(eqReq.getEquipmentTypeId())
                        .orElseThrow(() -> new RuntimeException("EquipmentType not found"));

                EquipmentStatus defaultStatus = equipmentStatusRepository.findById(1)
                        .orElseThrow(() -> new RuntimeException("Default EquipmentStatus (id=1) not found"));

                eq.setEquipmentStatus(defaultStatus);
                eq.setEquipmentType(eqType);
                eq.setBrand(eqReq.getBrand());
                eq.setModel(eqReq.getModel());
                eq.setSerialNumber(eqReq.getSerialNumber());
                eq.setLicenseKey(eqReq.getLicenseKey());
                eq.setLot(savedLot);

                equipments.add(equipmentRepository.save(eq));
            }

            savedLot.setEquipmentList(equipments);
        }

        return savedLot;
    }


}
