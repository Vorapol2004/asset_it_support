package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.borrow.BorrowResponseTest;
import com.plub_kao.asset_it_support.entity.department.DepartmentDto;
import com.plub_kao.asset_it_support.entity.department.DepartmentView;

import com.plub_kao.asset_it_support.service.DepartmentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    @PostMapping("/create")
    public DepartmentDto create(@RequestBody DepartmentDto dto) {
        return departmentService.create(dto);
    }

    @PutMapping("/{id}")
    public DepartmentDto update(@PathVariable Integer id, @RequestBody DepartmentDto dto) {
        dto.setId(id);
        return departmentService.update(dto);
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        departmentService.delete(id);
    }
}
