package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.employee.Employee;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeView;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeViewDepAndRole;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeViewDepartment;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeViewRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

//ดึงข้อมูล employee ออกมาทั้งหมด
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
    List<EmployeeView> findAllEmployee();

//ดึงข้อมูล employee เฉพาะ role นั้นๆโดยเฉพาะ
    @Query(value= """
        SELECT
            e.id,
        	e.first_name,
            e.last_name,
            e.email,
            e.role_id,
            r.role_name
        FROM
            `employee` e
        LEFT JOIN
        	role r ON r.id = e.role_id
        WHERE
         	r.id = :roleId
    """, nativeQuery = true)
    List<EmployeeViewRole> findChooseRoles(@Param("roleId" )Integer roleId);


//ดึงข้อมูล emloyee เฉพาะ department นั้นๆโดยเฉพาะ
    @Query(value = """
        SELECT
        	e.id,
        	e.first_name,
            e.last_name,
            e.email,
            e.department_id,
            d.department_name
        FROM
            `employee` e
        LEFT JOIN
        	department d ON d.id = e.department_id
        WHERE
         	d.id = :departmentId
    """,nativeQuery = true)
    List<EmployeeViewDepartment> findChooseDepartments(@Param("departmentId" )Integer departmentId);

//ดึงข้อมูล emloyee ฟิตเตอร์ที่มีตำแหน่ง role และ department ออกมาทั้งหมด
    @Query(value = """

        SELECT
            e.id,
            e.first_name,
            e.last_name,
            e.email,
            e.department_id,
            d.department_name,
            e.role_id,
            r.role_name
        FROM
            employee e
        LEFT JOIN
            department d ON d.id = e.department_id
        LEFT JOIN
            role r ON r.id = e.role_id
        WHERE
            d.id = :departmentId
        AND
            r.id = :roleId
""",nativeQuery = true)
    List<EmployeeViewDepAndRole> findChooseDepAndRole(@Param("departmentId") Integer departmentId,
                                                      @Param("roleId") Integer roleId);

}
