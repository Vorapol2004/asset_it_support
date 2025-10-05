package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.employee.view.EmployeeView;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeViewDepAndRole;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeViewDepartment;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeViewRole;
import com.plub_kao.asset_it_support.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<EmployeeView> findAll() {
        List<EmployeeView> employees = employeeRepository.findAllEmployee();
        return employees;
    }


    //ดึงข้อมูล employee เฉพาะ role นั้นๆโดยเฉพาะ
    public List<EmployeeViewRole> ChooseEmployeeRoles(Integer roleId) {
        List<EmployeeViewRole> employees = employeeRepository.ChooseEmployeeRoles(roleId);
        return employees;
    }


    //ดึงข้อมูล emloyee เฉพาะ department นั้นๆโดยเฉพาะ
    public List<EmployeeViewDepartment> ChooseEmployeeDepartments(Integer departmentId) {
        List<EmployeeViewDepartment> employees = employeeRepository.ChooseEmployeeDepartments(departmentId);
        return employees;
    }


    //ดึงข้อมูล emloyee ฟิตเตอร์ที่มีตำแหน่ง role และ department ออกมาทั้งหมด
    public List<EmployeeViewDepAndRole> ChooseEmployeeDepartmentAndRole(Integer depId, Integer roleId) {
        List<EmployeeViewDepAndRole> emloyees = employeeRepository.ChooseEmployeeDepartmentAndRole(depId, roleId);
        return emloyees;
    }

    // ✅ เพิ่ม method ใหม่ — ค้นหาด้วย keyword + ตัวกรอง (role, department)
    public List<EmployeeViewDepAndRole> searchEmployee(String keyword) {
        // ถ้า keyword เป็น null ให้ใส่ค่าว่างไว้ จะได้ค้นหาได้หมด
        if (keyword == null) {
            keyword = "";
        }
        return employeeRepository.findEmployeeByDepRoleAndKeyword(keyword);
    }


}
