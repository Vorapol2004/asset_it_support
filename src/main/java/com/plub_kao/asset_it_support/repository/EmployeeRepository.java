package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.employee.Employee;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    //ดึงข้อมูลรายชื่อ employee ออกมาทั้งหมด
    @Query(value = """
                SELECT
                    e.id AS employee_id,
                    CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
                    e.email,
                    e.phone,
                    e.description,
            
                    e.department_id,
                    d.department_name,
            
                    e.role_id,
                    r.role_name
            
                FROM
                    employee e
                LEFT JOIN department d ON
                    d.id = e.department_id
                LEFT JOIN role r ON
                    r.id = e.role_id
            """, nativeQuery = true)
    List<EmployeeView> findAllEmployee();


    //ดึงข้อมูล employee เฉพาะ role นั้นๆโดยเฉพาะ
    @Query(value = """
                SELECT
                    e.id AS employee_id,
                    CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
                    e.email,
                    e.phone,
                    e.description,
            
                    e.department_id,
                    d.department_name,
            
                    e.role_id,
                    r.role_name
                FROM
                    `employee` e
                LEFT JOIN
                	role r ON r.id = e.role_id
                WHERE
                 	r.id = :EmployeeRoleId
            """, nativeQuery = true)
    List<EmployeeView> ChooseEmployeeRoles(@Param("roleId") Integer EmployeeRoleId);


    //ดึงข้อมูล employee เฉพาะ department นั้นๆโดยเฉพาะ
    @Query(value = """
                SELECT
                	e.id AS employee_id,
                    CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
                    e.email,
                    e.phone,
                    e.description,
            
                    e.department_id,
                    d.department_name,
            
                    e.role_id,
                    r.role_name
                FROM
                    `employee` e
                LEFT JOIN
                	department d ON d.id = e.department_id
                WHERE
                 	d.id = :EmployeeDepartmentId
            """, nativeQuery = true)
    List<EmployeeView> ChooseEmployeeDepartments(@Param("departmentId") Integer EmployeeDepartmentId);


    //ดึงข้อมูลรายชื่อ employee ฟิตเตอร์ที่มีตำแหน่ง role และ department ออกมาทั้งหมด
    @Query(value = """
                SELECT
                    e.id AS employee_id,
                    CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
                    e.email,
                    e.phone,
                    e.description,
            
                    e.department_id,
                    d.department_name,
            
                    e.role_id,
                    r.role_name
                FROM
                    `employee` e
                LEFT JOIN
                	department d ON  d.id = e.department_id
                LEFT JOIN
                    role r ON r.id = e.role_id
                WHERE
                    d.id = :EmployeeDepartmentId
                    AND r.id = :EmployeeRoleId
            """, nativeQuery = true)
    List<EmployeeView> ChooseEmployeeDepartmentAndRole(
            @Param("descriptionId") Integer EmployeeDepartmentId,
            @Param("roleId") Integer EmployeeRoleId);


    //ค้นหาชื่อ employee ด้วย keyword
    @Query(value = """
            
                    SELECT
                    e.id AS employee_id,
                    CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
                    e.email,
                    e.phone,
                    e.description,
            
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
                        (
                        e.first_name LIKE CONCAT('%', :keyword, '%')
                        OR e.last_name LIKE CONCAT('%', :keyword, '%')
                        OR e.email LIKE CONCAT('%', :keyword, '%')
                        OR CONCAT(e.first_name,' ', e.last_name) LIKE CONCAT('%', :keyword, '%')
                        )
            """, nativeQuery = true)
    List<EmployeeView> searchEmployeeKeyword(
            @Param("keyword") String keyword);


    @Query(value = """
        e.id AS employee_id,
        CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
        e.email,
        e.phone,
        e.description,
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
            e.id = :employeeId
    """, nativeQuery = true)
    EmployeeView findEmployeesById(Integer employeeId);
}
