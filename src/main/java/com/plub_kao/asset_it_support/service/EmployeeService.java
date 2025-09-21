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
    public List<EmployeeViewRole> findChooseRoles(Integer roleId) {
        List<EmployeeViewRole> employees = employeeRepository.findChooseRoles(roleId);
        return employees;
    }


    //ดึงข้อมูล emloyee เฉพาะ department นั้นๆโดยเฉพาะ
    public List<EmployeeViewDepartment> findChooseDepartments(Integer departmentId) {
        List<EmployeeViewDepartment> employees = employeeRepository.findChooseDepartments(departmentId);
        return employees;
    }


    //ดึงข้อมูล emloyee ฟิตเตอร์ที่มีตำแหน่ง role และ department ออกมาทั้งหมด
    public List<EmployeeViewDepAndRole> findChooseDepAndRoles(Integer depId, Integer roleId) {
        List<EmployeeViewDepAndRole> emloyees = employeeRepository.findChooseDepAndRole(depId, roleId);
        return emloyees;
    }


}
