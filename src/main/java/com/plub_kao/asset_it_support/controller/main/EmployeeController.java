package com.plub_kao.asset_it_support.controller.main;


import com.plub_kao.asset_it_support.entity.employee.Employee;
import com.plub_kao.asset_it_support.entity.employee.EmployeeRequest;
import com.plub_kao.asset_it_support.entity.employee.EmployeeResponse;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeView;
import com.plub_kao.asset_it_support.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {


    private final EmployeeService employeeService;


    @GetMapping("/all")
    public ResponseEntity<List<EmployeeView>> findAllEmployee() {
        List<EmployeeView> employeeViews = employeeService.findAllEmployee();
        return ResponseEntity.ok(employeeViews);
    }

    @GetMapping("/select_employee")
    public ResponseEntity<EmployeeView> selectEmployee(Integer employeeId) {
        EmployeeView employeeView = employeeService.SearchEmployeeById(employeeId);
        return ResponseEntity.ok(employeeView);
    }


    @GetMapping("/search")
    public ResponseEntity<List<EmployeeView>> searchEmployees(String keyword) {
        List<EmployeeView> employees = employeeService.searchEmployeeKeyword(keyword);
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeResponse> addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse response = employeeService.addEmployee(employeeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
