// # ✅ DTO สำหรับรับข้อมูลจาก Frontend
package com.plub_kao.asset_it_support.dto.request;

import com.plub_kao.asset_it_support.dto.equipment.EquipmentRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class LotCreateRequest {
    private String lotName;
    private String academicYear;
    private LocalDate purchaseDate;
    private LocalDate expireDate;
    private String referenceDoc;
    private String description;
    private Integer lotTypeId;
    private List<EquipmentRequest> items;
}
