package com.plub_kao.asset_it_support.entity.borrow;


import com.plub_kao.asset_it_support.entity.employee.Employee;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BorrowResponse {
    private Integer id;
    private Integer employeeId;
    private String employeeName;
    private String referenceDoc;
    private LocalDate borrowDate;
    private List<EquipmentInfo> equipments;

    @Getter
    @Setter
    public static class EquipmentInfo {
        private String equipmentName;
        private String serialNumber;
        private String licenseKey;
    }

    public void addEquipmentInfo(Equipment equipment) {
        EquipmentInfo equipmentInfo = new EquipmentInfo();
        equipmentInfo.setEquipmentName(equipment.getEquipmentName());
        if (equipment.getSerialNumber() != null) {
            equipmentInfo.setSerialNumber(equipment.getSerialNumber());
        }

        if (equipment.getLicenseKey() != null) {
            equipmentInfo.setLicenseKey(equipment.getLicenseKey());
        }

        this.equipments.add(equipmentInfo);
    }
}
