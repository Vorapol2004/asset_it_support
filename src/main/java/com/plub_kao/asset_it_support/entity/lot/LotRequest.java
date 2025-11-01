package com.plub_kao.asset_it_support.entity.lot;

import com.plub_kao.asset_it_support.entity.equipment.EquipmentRequest;
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
    private Integer lotTypeId; // ส่งแค่ id ของ LotType ก็พอ
    private List<EquipmentRequest> equipmentList;


}
