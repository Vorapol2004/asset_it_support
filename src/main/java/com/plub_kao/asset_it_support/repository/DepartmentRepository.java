package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {


}
