package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    @Query(value = """

        SELECT
            `id`,
            `first_name`,
            `last_name`,
            `email`,
            `description`,
            `role_id`,
            `department_id`
        FROM
            `employee`
    """ , nativeQuery = true)
    List<Object[]> findAllEmployee();


}
