package com.plub_kao.asset_it_support.controller.main;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReturnRequest {
    private Integer borrowerEquipmentId;
    private Integer equipmentId;
    private Integer statusId;
    private LocalDate returnDate;
}
