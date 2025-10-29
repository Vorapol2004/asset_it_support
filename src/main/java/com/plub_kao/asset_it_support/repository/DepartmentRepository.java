package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {


}
