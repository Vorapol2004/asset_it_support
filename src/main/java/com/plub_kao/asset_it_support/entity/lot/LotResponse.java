package com.plub_kao.asset_it_support.entity.lot;

import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import com.plub_kao.asset_it_support.entity.equipment.EquipmentResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class LotResponse {
    private Integer id;
    private String lotName;
    private String academicYear;
    private String referenceDoc;
    private String description;
    private LocalDate purchaseDate;
    private LocalDate expireDate;
    private String lotTypeName;
    private List<EquipmentResponse> equipmentResponses;

    public LotResponse(Lot lot) {
        this.id = lot.getId();
        this.lotName = lot.getLotName();
        this.academicYear = lot.getAcademicYear();
        this.referenceDoc = lot.getReferenceDoc();
        this.description = lot.getDescription();
        this.purchaseDate = lot.getPurchaseDate();
        this.expireDate = lot.getExpireDate();
        this.lotTypeName = lot.getLotType().getLotTypeName();

        if (lot.getEquipmentList() != null) {
            this.equipmentResponses = lot.getEquipmentList().stream()
                    .map(EquipmentResponse::new)
                    .toList();
        }
    }

    @Getter
    @Setter
    public static class EquipmentResponse {
        private Integer id;
        private String equipmentName;
        private String brand;
        private String model;
        private String serialNumber;
        private String licenseKey;
        private String equipmentTypeName;

        public EquipmentResponse(Equipment eq) {
            this.id = eq.getId();
            this.equipmentName = eq.getEquipmentName();
            this.brand = eq.getBrand();
            this.model = eq.getModel();
            this.serialNumber = eq.getSerialNumber();
            this.licenseKey = eq.getLicenseKey();
            this.equipmentTypeName = eq.getEquipmentType().getEquipmentTypeName();
        }
    }
}
