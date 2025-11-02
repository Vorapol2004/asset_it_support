package com.plub_kao.asset_it_support.entity.lot;

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
    private List<EquipmentResponse> equipmentList;


}
