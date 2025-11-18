package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;
import com.plub_kao.asset_it_support.entity.department.Department;
import com.plub_kao.asset_it_support.entity.department.DepartmentDto;
import com.plub_kao.asset_it_support.entity.department.DepartmentView;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import com.plub_kao.asset_it_support.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {


    private final DepartmentRepository departmentRepository;
    private final BorrowRepository borrowRepository;


    public List<DepartmentView> findAll() {
        try {
            return departmentRepository.findAllByDepartment();
        } catch (RuntimeException e) {
            throw new RuntimeException("พัง", e);
        }
    }

    public DepartmentDto create(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setDepartmentName(departmentDto.getDepartmentName());
        departmentRepository.save(department);

        departmentDto.setId(department.getId());
        return departmentDto;
    }

    public DepartmentDto update(DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(departmentDto.getId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        department.setDepartmentName(departmentDto.getDepartmentName());
        departmentRepository.save(department);
        return departmentDto;
    }


    public void delete(Integer id) {
        departmentRepository.deleteById(id);
    }


}
