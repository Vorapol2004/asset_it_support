package com.plub_kao.asset_it_support.entity.lot;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class LotRequest {

    private String lotName;

    private String academicYear;

    private String referenceDoc;

    private String description;


    private LocalDate purchaseDate;

    private LocalDate expireDate;


    private Integer lotTypeId;

    private List<EquipmentRequest> equipmentList;

    @Getter
    @Setter
    public static class EquipmentRequest {
        
        private Integer equipmentId;
        private String equipmentName;
        private Integer equipmentTypeId;
        private String brand;
        private String model;
        private String serialNumber;
        private String licenseKey;
        private Integer equipmentStatusId;

    }


}
