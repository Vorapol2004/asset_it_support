package com.plub_kao.asset_it_support.entity.borrow;

import com.plub_kao.asset_it_support.entity.employee.EmployeeRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BorrowResponse {
    private Integer id;
    private EmployeeRequest employeeRequest;
    private String referenceDoc;
    private LocalDate borrowDate;
    private Integer BorrowStatus;
    private List<Integer> equipmentId;
}
