package com.plub_kao.asset_it_support.entity.equipment;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentRequest {
    private String equipmentName;
    private String brand;
    private String model;
    private String serialNumber;
    private String licenseKey;
    private Integer equipmentStatusId;
    private Integer equipmentTypeId;
}
