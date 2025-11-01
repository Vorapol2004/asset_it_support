package com.plub_kao.asset_it_support.dto.response;

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
    private LocalDate purchaseDate;
    private LocalDate expireDate;
    private String referenceDoc;
    private String description;
    private String lotTypeName;
    private List<EquipmentSummary> equipmentList;

    @Getter
    @Setter
    public static class EquipmentSummary {
        private Integer id;
        private String equipmentName;
        private String brand;
        private String model;
        private String serialNumber;
        private String licenseKey;
        private String equipmentTypeName;
        private String equipmentStatusName;
    }
}
