package com.plub_kao.asset_it_support.entity.borrow;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BorrowRequest {


    private EmployeeRequest employeeRequest;
    private String referenceDoc;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private List<Integer> equipmentId;


    @Getter
    @Setter
    public static class EmployeeRequest {

        private Integer employeeId;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String description;
        private Integer departmentId;
        private Integer roleId;
    }

}
