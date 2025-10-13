package com.plub_kao.asset_it_support.controller.main;


import com.plub_kao.asset_it_support.entity.employee.view.EmployeeView;
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


    //ดึงข้อมูลรายชื่อ employee ออกมาทั้งหมด
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeView>> findAll() {
        List<EmployeeView> employees = employeeService.findAll();
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(employees);
    }

    //ดึงข้อมูลรายชื่อ employee เฉพาะ role นั้นโดยเฉพาะออกมาทั้งหมด
    @GetMapping("/role/{id}")
    public ResponseEntity<List<EmployeeView>> ChooseEmployeeRoles(@PathVariable Integer id) {
        List<EmployeeView> employees = employeeService.ChooseEmployeeRoles(id);
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(employees);
    }

    //ดึงข้อมูลรายขื่อ employee เฉพาะ department นั้นโดยเฉพาะออกมาทั้งหมด
    @GetMapping("/dep/{id}")
    public ResponseEntity<List<EmployeeView>> ChooseEmployeeDepartments(@PathVariable Integer id) {
        List<EmployeeView> employee = employeeService.ChooseEmployeeDepartments(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    //ดึงข้อมูลรายชื่อ employee ฟิตเตอร์ที่มีตำแหน่ง role และ department ออกมาทั้งหมด
    @GetMapping("/dep/{idDep}/role/{idRole}")
    public ResponseEntity<List<EmployeeView>> ChoChooseEmployeeDepartmentAndRole
    (@PathVariable Integer idDep, @PathVariable Integer idRole) {
        List<EmployeeView> DepAndRole = employeeService.ChooseEmployeeDepartmentAndRole(idDep, idRole);
        if (DepAndRole.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(DepAndRole);
    }


    // ค้นหารายชื่อ employee ด้วย keyword ของชื่อ
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeView>> searchEmployees(@RequestParam String keyword) {
        List<EmployeeView> employees = employeeService.searchEmployeeKeyword(keyword);
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(employees);
    }
}
