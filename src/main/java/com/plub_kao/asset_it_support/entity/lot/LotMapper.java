package com.plub_kao.asset_it_support.entity.lot;

import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.equipment.EquipmentResponse;

import java.util.List;
import java.util.stream.Collectors;

public class LotMapper {

    public static LotResponse toLotResponse(Lot lot) {
        LotResponse dto = new LotResponse();
        dto.setId(lot.getId());
        dto.setLotName(lot.getLotName());
        dto.setAcademicYear(lot.getAcademicYear());
        dto.setReferenceDoc(lot.getReferenceDoc());
        dto.setDescription(lot.getDescription());
        dto.setPurchaseDate(lot.getPurchaseDate());
        dto.setExpireDate(lot.getExpireDate());
        dto.setLotTypeName(lot.getLotType() != null ? lot.getLotType().getLotTypeName() : null);

        if (lot.getEquipmentList() != null) {
            List<EquipmentResponse> equipmentDTOs = lot.getEquipmentList().stream()
                    .map(LotMapper::toEquipmentResponse)
                    .collect(Collectors.toList());
            dto.setEquipmentList(equipmentDTOs);
        }

        return dto;
    }

    public static EquipmentResponse toEquipmentResponse(Equipment eq) {
        EquipmentResponse dto = new EquipmentResponse();
        dto.setId(eq.getId());
        dto.setEquipmentName(eq.getEquipmentName());
        dto.setBrand(eq.getBrand());
        dto.setModel(eq.getModel());
        dto.setSerialNumber(eq.getSerialNumber());
        dto.setLicenseKey(eq.getLicenseKey());
        dto.setEquipmentTypeName(eq.getEquipmentType() != null ? eq.getEquipmentType().getEquipmentTypeName() : null);
        dto.setEquipmentStatusName(eq.getEquipmentStatus() != null ? eq.getEquipmentStatus().getEquipmentStatusName() : null);
        return dto;
    }
}
