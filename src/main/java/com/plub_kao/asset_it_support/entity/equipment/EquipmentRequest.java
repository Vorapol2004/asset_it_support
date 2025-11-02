package com.plub_kao.asset_it_support.entity.equipment;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentRequest {


    private Integer id;
    private String equipmentName; // อนุโลมให้เว้นได้ เพราะระบบอาจตั้งชื่อ auto

    private String brand;

    private String model;

    private String serialNumber;

    private String licenseKey;

    private Integer equipmentTypeId;

    // equipmentStatusId ไม่ต้องใส่ ถ้าคุณตั้งค่า default เป็น “Available” ใน service
}
