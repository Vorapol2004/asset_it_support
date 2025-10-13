package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.employee.view.EmployeeView;
import com.plub_kao.asset_it_support.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    //ดึงข้อมูลรายชื่อ employee ออกมาทั้งหมด
    public List<EmployeeView> findAll() {
        List<EmployeeView> employees = employeeRepository.findAllEmployee();
        return employees;
    }


    //ดึงข้อมูลรายชื่อ employee เฉพาะ role นั้นโดยเฉพาะออกมาทั้งหมด
    public List<EmployeeView> ChooseEmployeeRoles(@Param("EmployeeRoleId") Integer EmployeeRoleId) {
        List<EmployeeView> employees = employeeRepository.ChooseEmployeeRoles(EmployeeRoleId);
        return employees;
    }


    //ดึงข้อมูลรายขื่อ employee เฉพาะ department นั้นโดยเฉพาะออกมาทั้งหมด
    public List<EmployeeView> ChooseEmployeeDepartments(@Param("EmployeeDepartmentId") Integer EmployeeDepartmentId) {
        List<EmployeeView> employees = employeeRepository.ChooseEmployeeDepartments(EmployeeDepartmentId);
        return employees;
    }


    //ดึงข้อมูลรายชื่อ employee ฟิตเตอร์ที่มีตำแหน่ง role และ department ออกมาทั้งหมด
    public List<EmployeeView> ChooseEmployeeDepartmentAndRole(@Param("EmployeeDepartmentId") Integer EmployeeDepartmentId,
                                                              @Param("EmployeeRoleId") Integer EmployeeRoleId) {
        List<EmployeeView> employees = employeeRepository.ChooseEmployeeDepartmentAndRole(EmployeeDepartmentId, EmployeeRoleId);
        return employees;
    }

    // ค้นหารายชื่อ employee ด้วย keyword ของชื่อ
    public List<EmployeeView> searchEmployeeKeyword(@Param("keyword") String keyword) {
        // ถ้า keyword เป็น null ให้ใส่ค่าว่างไว้ จะได้ค้นหาได้หมด
        if (keyword == null) {
            keyword = "";
        }
        return employeeRepository.searchEmployeeKeyword(keyword);
    }


}
