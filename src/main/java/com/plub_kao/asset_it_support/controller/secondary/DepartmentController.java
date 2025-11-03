package com.plub_kao.asset_it_support.controller.secondary;

import com.plub_kao.asset_it_support.entity.department.Department;
import com.plub_kao.asset_it_support.entity.department.DepartmentLocationRequest;
import com.plub_kao.asset_it_support.entity.department.DepartmentView;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import com.plub_kao.asset_it_support.repository.DepartmentRepository;
import com.plub_kao.asset_it_support.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {


    private final DepartmentService departmentService;


    @GetMapping("/drop_down")
    public ResponseEntity<List<DepartmentView>> findByDepartment() {
        List<DepartmentView> DepartmentView = departmentService.findAll();
        return ResponseEntity.ok(DepartmentView);

    }


}
