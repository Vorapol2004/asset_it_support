//# ✅ แยก DTO สำหรับแต่ละ item
package com.plub_kao.asset_it_support.dto.equipment;

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
    private Integer equipmentTypeId;
    private Integer equipmentStatusId;
}
