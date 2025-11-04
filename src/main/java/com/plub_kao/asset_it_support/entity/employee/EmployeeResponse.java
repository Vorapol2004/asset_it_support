package com.plub_kao.asset_it_support.entity.employee;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {
    private Integer id;
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String description;
    private String departmentName;
    private String roleName;
    private String roomName;
}
