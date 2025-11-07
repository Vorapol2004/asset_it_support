package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.employee.EmployeeResponse;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.equipment.EquipmentResponse;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatus;
import com.plub_kao.asset_it_support.entity.equipmentType.EquipmentType;
import com.plub_kao.asset_it_support.entity.lot.LotRequest;
import com.plub_kao.asset_it_support.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {


    private final EquipmentRepository equipmentRepository;
    private final EquipmentStatusService equipmentStatusService;
    private final EquipmentTypeService equipmentTypeService;
    private final BorrowEquipmentRepository borrowEquipmentRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;
    private final EquipmentStatusRepository equipmentStatusRepository;


    public List<EquipmentView> findAll() {
        try {
            return equipmentRepository.findAllEquipment();
        } catch (Exception e) {
            throw new RuntimeException("ไม่เจอ", e);
        }


    }

    public List<EquipmentView> equipmentIdentifier(@RequestParam String keyword) {
        try {
            return equipmentRepository.equipmentIdentifier(keyword);
        } catch (Exception e) {
            throw new RuntimeException("ไม่เจอ", e);
        }

    }


    public List<EquipmentView> SelectEquipment(@Param("equipmentStatusId") int equipmentStatusId) {
        try {
            return equipmentRepository.selectEquipmentById(equipmentStatusId);
        } catch (Exception e) {
            throw new RuntimeException("ไม่เจอ", e);
        }

    }


    public List<EquipmentView> filterStatusAndType(@RequestParam Integer equipmentStatusId,
                                                   @RequestParam Integer equipmentTypeId) {
        if (equipmentStatusId != null && equipmentTypeId == null) {
            return equipmentStatusService.FilterEquipmentStatus(equipmentStatusId);
        }
        if (equipmentStatusId == null && equipmentTypeId != null) {
            return equipmentTypeService.FilterEquipmentType(equipmentTypeId);
        }
        if (equipmentStatusId != null && equipmentTypeId != null) {
            return equipmentRepository.findByDynamicFilter(equipmentStatusId, equipmentTypeId);
        }
        return equipmentRepository.findAllEquipment();
    }


    public List<EquipmentView> selectEquipmentByType(@RequestParam(required = false) Integer equipmentTypeId) {
        try {
            List<EquipmentView> equipment;
            if (equipmentTypeId != null) {
                equipment = equipmentRepository.selectEquipmentTypeId(equipmentTypeId);
            } else {
                equipment = equipmentRepository.findAllEquipment();
            }
            return equipment;
        } catch (Exception e) {
            throw new RuntimeException("พัง", e);
        }

    }


    //ค้นหาชื่อ equipment name,brand,model,serial_number,license_key ด้วย keyword
    public List<EquipmentView> searchEquipmentKeyword(@RequestParam("keyword") String keyword) {
        try {
            return equipmentRepository.searchEquipmentKeyword(keyword);
        } catch (Exception e) {
            throw new RuntimeException("พัง", e);
        }

    }

    @Transactional
    public EquipmentResponse editEquipment(LotRequest.EquipmentRequest equipmentRequest) {
        Equipment equipment = equipmentRepository.findById(equipmentRequest.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        boolean isBorrowingNow = borrowEquipmentRepository.existsByEquipmentIdAndReturnDateIsNull(equipment.getId());

        if (isBorrowingNow) {
            throw new RuntimeException("Cannot update status. Equipment is currently borrowing.");
        }

        if (equipmentRequest.getEquipmentName() != null) {
            equipment.setEquipmentName(equipmentRequest.getEquipmentName());
        }
        if (equipmentRequest.getBrand() != null) {
            equipment.setBrand(equipmentRequest.getBrand());
        }
        if (equipmentRequest.getModel() != null) {
            equipment.setModel(equipmentRequest.getModel());
        }
        if (equipmentRequest.getLicenseKey() != null) {
            equipment.setLicenseKey(equipmentRequest.getLicenseKey());
        }
        if (equipmentRequest.getSerialNumber() != null) {
            equipment.setSerialNumber(equipmentRequest.getSerialNumber());

        }

        if (equipmentRequest.getEquipmentTypeId() != null) {
            EquipmentType equipmentType = equipmentTypeRepository.findById(equipmentRequest.getEquipmentTypeId())
                    .orElseThrow(() -> new RuntimeException("EquipmentType not found"));
            equipment.setEquipmentType(equipmentType);
        }

        if (equipmentRequest.getEquipmentStatusId() != null) {
            EquipmentStatus equipmentStatus = equipmentStatusRepository.findById(equipmentRequest.getEquipmentStatusId())
                    .orElseThrow(() -> new RuntimeException("EquipmentStatus not found"));
            equipment.setEquipmentStatus(equipmentStatus);

        }
        equipmentRepository.save(equipment);

        return createUndoEquipment(equipment);
    }

    private EquipmentResponse createUndoEquipment(Equipment saveEquipment) {
        EquipmentResponse response = new EquipmentResponse();
        response.setId(saveEquipment.getId());
        response.setEquipmentName(saveEquipment.getEquipmentName());
        response.setBrand(saveEquipment.getBrand());
        response.setModel(saveEquipment.getModel());
        response.setLicenseKey(saveEquipment.getLicenseKey());
        response.setSerialNumber(saveEquipment.getSerialNumber());

        if (saveEquipment.getEquipmentType() != null) {
            response.setEquipmentTypeName(saveEquipment.getEquipmentType().getEquipmentTypeName());
        }
        if (saveEquipment.getEquipmentStatus() != null) {
            response.setEquipmentStatusName(saveEquipment.getEquipmentStatus().getEquipmentStatusName());
        }

        return response;


    }


    @Transactional
    public boolean deleteEquipment(Integer id) {
        // ตรวจว่ามีอยู่ไหม
        if (!equipmentRepository.existsById(id)) {
            return false;
        }
        // ตรวจว่ามีการอ้างอิงใน borrow_equipment ไหม
        int borrowCount = borrowEquipmentRepository.countByEquipmentId(id);
        if (borrowCount > 0) {
            throw new RuntimeException("ไม่สามารถลบอุปกรณ์ได้ เนื่องจากอุปกรณ์นี้เคยถูกยืมหรือมีประวัติการใช้งาน");
        }
        // ถ้าไม่เคยถูกยืม → ลบได้เลย
        equipmentRepository.deleteById(id);
        return true;
    }
}
