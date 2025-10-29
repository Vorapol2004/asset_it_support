package com.plub_kao.asset_it_support.entity.department;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentLocationRequest {

    private Integer departmentId;
    private String buildingName;
    private String floorName;
    private String roomName;
    
}
