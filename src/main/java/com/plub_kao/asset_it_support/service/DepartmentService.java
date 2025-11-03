package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.department.Department;
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


    public List<DepartmentView> findAll() {
        try {
            return departmentRepository.findAllByDepartment();
        } catch (RuntimeException e) {
            throw new RuntimeException("พัง", e);
        }
    }


}
