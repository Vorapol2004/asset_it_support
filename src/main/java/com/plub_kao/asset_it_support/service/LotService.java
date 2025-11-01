package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.dto.request.LotCreateRequest;
import com.plub_kao.asset_it_support.dto.response.LotResponse;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.lot.Lot;
import com.plub_kao.asset_it_support.entity.lot.LotType;
import com.plub_kao.asset_it_support.repository.*;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    private EquipmentStatusRepository equipmentStatusRepository;

    @Autowired
    private EquipmentTypeRepository equipmentTypeRepository;

    @Transactional

    public LotResponse createLotWithEquipments(@RequestBody LotCreateRequest request) {

        // ✅ STEP 1: สร้างและบันทึก Lot
        LotType lotType = lotTypeRepository.findById(request.getLotTypeId())
                .orElseThrow(() -> new RuntimeException("ไม่พบ LotType ID: " + request.getLotTypeId()));

        Lot lot = new Lot();
        lot.setLotName(request.getLotName());
        lot.setAcademicYear(request.getAcademicYear());
        lot.setPurchaseDate(request.getPurchaseDate());
        lot.setExpireDate(request.getExpireDate());
        lot.setReferenceDoc(request.getReferenceDoc());
        lot.setDescription(request.getDescription());
        lot.setLotType(lotType);

        Lot savedLot = lotRepository.save(lot);

        // ✅ STEP 2: เพิ่มอุปกรณ์ทั้งหมด
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            for (var eqReq : request.getItems()) {

                var type = equipmentTypeRepository.findById(eqReq.getEquipmentTypeId())
                        .orElseThrow(() -> new RuntimeException("ไม่พบ EquipmentType ID: " + eqReq.getEquipmentTypeId()));

                var status = equipmentStatusRepository.findById(eqReq.getEquipmentStatusId())
                        .orElseThrow(() -> new RuntimeException("ไม่พบ EquipmentStatus ID: " + eqReq.getEquipmentStatusId()));

                Equipment eq = new Equipment();
                eq.setEquipmentName(eqReq.getEquipmentName());
                eq.setBrand(eqReq.getBrand());
                eq.setModel(eqReq.getModel());
                eq.setSerialNumber(eqReq.getSerialNumber());
                eq.setLicenseKey(eqReq.getLicenseKey());
                eq.setEquipmentType(type);
                eq.setEquipmentStatus(status);
                eq.setLot(savedLot);

                equipmentRepository.save(eq);
            }
        }

        // ✅ STEP 3: แปลง Lot + Equipment เป็น DTO (LotResponse)
        LotResponse response = new LotResponse();
        response.setId(savedLot.getId());
        response.setLotName(savedLot.getLotName());
        response.setAcademicYear(savedLot.getAcademicYear());
        response.setPurchaseDate(savedLot.getPurchaseDate());
        response.setExpireDate(savedLot.getExpireDate());
        response.setReferenceDoc(savedLot.getReferenceDoc());
        response.setDescription(savedLot.getDescription());
        response.setLotTypeName(savedLot.getLotType().getLotTypeName());

        // ดึงรายการอุปกรณ์ที่เกี่ยวข้องกับ lot ที่เพิ่งสร้าง
        var equipmentList = equipmentRepository.FilterEquipmentLot(savedLot.getId());

        // map เป็น EquipmentSummary
        List<LotResponse.EquipmentSummary> equipmentSummaries = equipmentList.stream()
                .map(eqView -> {
                    var summary = new LotResponse.EquipmentSummary();
                    summary.setId(eqView.getId());
                    summary.setEquipmentName(eqView.getEquipmentName());
                    summary.setBrand(eqView.getBrand());
                    summary.setModel(eqView.getModel());
                    summary.setSerialNumber(eqView.getSerialNumber());
                    summary.setLicenseKey(eqView.getLicenseKey());
                    summary.setEquipmentTypeName(eqView.getEquipmentTypeName());
                    summary.setEquipmentStatusName(eqView.getEquipmentStatusName());
                    return summary;
                })
                .toList();

        response.setEquipmentList(equipmentSummaries);

        return response;


    }


}
