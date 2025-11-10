package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.BorrowEquipment;
import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.borrow.BorrowResponse;
import com.plub_kao.asset_it_support.entity.department.Department;
import com.plub_kao.asset_it_support.entity.employee.Employee;
import com.plub_kao.asset_it_support.entity.employee.EmployeeRequest;
import com.plub_kao.asset_it_support.entity.employee.EmployeeResponse;
import com.plub_kao.asset_it_support.entity.role.Role;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeView;
import com.plub_kao.asset_it_support.entity.room.Room;
import com.plub_kao.asset_it_support.repository.DepartmentRepository;
import com.plub_kao.asset_it_support.repository.EmployeeRepository;
import com.plub_kao.asset_it_support.repository.RoleRepository;
import com.plub_kao.asset_it_support.repository.RoomRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final RoomRepository roomRepository;


    public List<EmployeeView> findAllEmployee() {
        List<EmployeeView> employeeViews = employeeRepository.findAllEmployee();
        return employeeViews;
    }


    public List<EmployeeView> searchEmployeeKeyword(@RequestParam String keyword) {
        try {
            List<EmployeeView> employeeViews = employeeRepository.searchEmployeeKeyword(keyword);
            return employeeViews;
        } catch (Exception e) {
            throw new RuntimeException("พัง", e);
        }

    }

    public EmployeeView SearchEmployeeById(@RequestParam Integer employeeId) {
        try {
            EmployeeView employeeView = employeeRepository.selectOldEmployee(employeeId);
            return employeeView;
        } catch (Exception e) {
            throw new RuntimeException("พัง", e);
        }

    }

    @Transactional
    public EmployeeResponse addEmployee(EmployeeRequest request) {

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDescription(request.getDescription());

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        employee.setDepartment(department);

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        employee.setRole(role);

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        employee.setRoom(room);


        employeeRepository.save(employee);

        return createBorrowResponse(employee);
    }

    private EmployeeResponse createBorrowResponse(Employee savedEmployee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(savedEmployee.getId());
        response.setFirstName(savedEmployee.getFirstName());
        response.setLastName(savedEmployee.getLastName());
        response.setEmail(savedEmployee.getEmail());
        response.setPhone(savedEmployee.getPhone());
        response.setDescription(savedEmployee.getDescription());

        if (savedEmployee.getDepartment() != null) {
            response.setDepartmentName(savedEmployee.getDepartment().getDepartmentName());
        }

        if (savedEmployee.getRole() != null) {
            response.setRoleName(savedEmployee.getRole().getRoleName());
        }

        if (savedEmployee.getRoom() != null) {
            response.setRoomName(savedEmployee.getRoom().getRoomName());
        }

        return response;
    }
}
