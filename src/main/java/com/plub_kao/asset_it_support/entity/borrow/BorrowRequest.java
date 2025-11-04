package com.plub_kao.asset_it_support.entity.borrow;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BorrowRequest {
    private Integer employeeId;
    private String referenceDoc;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private List<Integer> equipmentIds;
}
