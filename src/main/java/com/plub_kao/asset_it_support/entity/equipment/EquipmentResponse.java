package com.plub_kao.asset_it_support.entity.equipment;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentResponse {
    private Integer id;
    private String equipmentName;
    private String brand;
    private String model;
    private String serialNumber;
    private String licenseKey;
    private String equipmentTypeName;
    private String equipmentStatusName;
}
