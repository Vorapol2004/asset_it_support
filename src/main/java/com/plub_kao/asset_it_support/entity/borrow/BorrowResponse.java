package com.plub_kao.asset_it_support.entity.borrow;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BorrowResponse {
    private Integer id;
    private Integer employeeId;
    private String employeeName;
    private String referenceDoc;
    private LocalDate borrowDate;
    private String statusName;

    
}
