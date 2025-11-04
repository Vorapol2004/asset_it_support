package com.plub_kao.asset_it_support.entity.employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String description;
    private Integer departmentId;
    private Integer roleId;
    private Integer roomId;
}
