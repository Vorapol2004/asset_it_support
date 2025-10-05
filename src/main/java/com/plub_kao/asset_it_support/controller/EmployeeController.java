package com.plub_kao.asset_it_support.controller;


import com.plub_kao.asset_it_support.entity.employee.view.EmployeeView;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeViewDepAndRole;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeViewDepartment;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeViewRole;
import com.plub_kao.asset_it_support.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public List<EmployeeView> findAll() {
        List<EmployeeView> employees = employeeService.findAll();
        return employees;
    }

    @GetMapping("/role/{id}")
    public List<EmployeeViewRole> ChooseEmployeeRoles(@PathVariable Integer id) {
        List<EmployeeViewRole> employees = employeeService.ChooseEmployeeRoles(id);
        return employees;
    }


    @GetMapping("/dep/{id}")
    public ResponseEntity<List<EmployeeViewDepartment>> ChooseEmployeeDepartments(@PathVariable Integer id) {
        List<EmployeeViewDepartment> employee = employeeService.ChooseEmployeeDepartments(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }


    @GetMapping("/dep/{idDep}/role/{idRole}")
    public ResponseEntity<List<EmployeeViewDepAndRole>> ChoChooseEmployeeDepartmentAndRole
            (@PathVariable Integer idDep, @PathVariable Integer idRole) {
        List<EmployeeViewDepAndRole> DepAndRole = employeeService.ChooseEmployeeDepartmentAndRole(idDep, idRole);
        if (DepAndRole.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(DepAndRole);
    }


    // ค้นหาด้วย keyword
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeViewDepAndRole>> searchEmployees(@RequestParam String keyword) {
        List<EmployeeViewDepAndRole> employees = employeeService.searchEmployee(keyword);
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(employees);
    }
}
