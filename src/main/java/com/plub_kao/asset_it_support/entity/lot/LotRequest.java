package com.plub_kao.asset_it_support.entity.lot;

import com.plub_kao.asset_it_support.entity.equipment.EquipmentRequest;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class LotRequest {

    private Integer id;
    private String lotName;

    private String academicYear;

    private String referenceDoc;

    private String description;


    private LocalDate purchaseDate;

    private LocalDate expireDate; // อาจไม่บังคับ ถ้าไม่จำเป็นต้องใส่


    private Integer lotTypeId; // แค่ส่ง id ของ LotType มาก็พอ

    private List<EquipmentRequest> equipmentList; // รายการอุปกรณ์ใน Lot


}
