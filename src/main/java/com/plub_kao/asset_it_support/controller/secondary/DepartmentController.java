package com.plub_kao.asset_it_support.controller.secondary;

import com.plub_kao.asset_it_support.entity.department.Department;
import com.plub_kao.asset_it_support.entity.department.DepartmentLocationRequest;
import com.plub_kao.asset_it_support.repository.DepartmentRepository;
import com.plub_kao.asset_it_support.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add")
    private Department saveDepartment(@RequestBody Department department) {
        return departmentRepository.save(department);
    }


    @PostMapping("/test")
    public String addDepartmentLocation(@RequestBody DepartmentLocationRequest request) {
        return departmentService.addDepartmentLocation(request);
    }

}
