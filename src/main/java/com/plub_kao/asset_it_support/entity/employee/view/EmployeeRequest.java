package com.plub_kao.asset_it_support.entity.employee.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String description;
    private Integer departmentId;
    private Integer roleId;
}
