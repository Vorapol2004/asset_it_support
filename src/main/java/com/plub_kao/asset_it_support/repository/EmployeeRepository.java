package com.plub_kao.asset_it_support.repository;

import com.plub_kao.asset_it_support.entity.employee.Employee;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    //ดึงข้อมูลรายชื่อ employee ออกมาทั้งหมด
    @Query(value = """
               SELECT
                   e.id AS employee_id,
                   e.first_name,
                   e.last_name,
                   e.email,
                   e.phone,
                   e.description,
                   e.department_id,
                   d.department_name,
                   e.role_id,
                   r.role_name,
                   b.building_name,
                   f.floor_name,
                   r.role_name,
                   COUNT(br.id) AS borrow_count
            
               FROM
                   employee e
               LEFT JOIN department d ON e.department_id = d.id
               LEFT JOIN role r ON e.role_id = r.id
               LEFT JOIN room rm ON e.room_id = rm.id
               LEFT JOIN floor f ON rm.floor_id = f.id
               LEFT JOIN building b ON f.building_id = b.id
               LEFT JOIN borrow br ON br.employee_id = e.id
               GROUP BY
                   e.id,
                   e.first_name,
                   e.last_name,
                   e.email,
                   e.phone,
                   e.description,
                   e.department_id,
                   d.department_name,
                   e.role_id,
                   r.role_name,
                   b.building_name,
                   f.floor_name,
                   rm.room_name;
            """, nativeQuery = true)
    List<EmployeeView> findAllEmployee();

    @Query(value = """
               SELECT
                   e.id AS employee_id,
                   e.first_name,
                   e.last_name,
                   e.email,
                   e.phone,
                   e.description,
                   e.department_id,
                   d.department_name,
                   e.role_id,
                   r.role_name,
                   b.building_name,
                   f.floor_name,
                   r.role_name,
                   rm.room_name
               FROM
                   employee e
               LEFT JOIN department d ON e.department_id = d.id
               LEFT JOIN role r ON e.role_id = r.id
               LEFT JOIN room rm ON e.room_id = rm.id
               LEFT JOIN floor f ON rm.floor_id = f.id
               LEFT JOIN building b ON f.building_id = b.id
               WHERE
                   e.id = :employeeId;
            """, nativeQuery = true)
    EmployeeView selectOldEmployee(@Param("employeeId") Integer employeeId);


    //ค้นหาชื่อ employee ด้วย keyword
    @Query(value = """
            
                    SELECT
                        e.id AS employee_id,
                        e.first_name,
                        e.last_name,
                        e.email,
                        e.phone,
                        e.description,
                        e.department_id,
                        d.department_name,
                        e.role_id,
                        r.role_name,
                        b.building_name,
                        f.floor_name,
                        r.role_name,
                        COUNT(br.id) AS borrow_count
                    FROM
                        employee e
                    LEFT JOIN department d ON
                        e.department_id = d.id
                    LEFT JOIN role r ON
                        e.role_id = r.id
                    LEFT JOIN room rm ON
                        e.room_id = rm.id
                    LEFT JOIN FLOOR f ON
                        rm.floor_id = f.id
                    LEFT JOIN building b ON
                        f.building_id = b.id
                    LEFT JOIN borrow br ON
                        br.employee_id = e.id
                    WHERE
                        e.first_name LIKE CONCAT('%', :keyword, '%')
                        OR e.last_name LIKE CONCAT('%', :keyword, '%')
                        OR e.email LIKE CONCAT('%', :keyword, '%')
                        OR e.phone LIKE CONCAT('%', :keyword, '%')
                        OR CONCAT(e.first_name,' ', e.last_name) LIKE CONCAT('%', :keyword, '%')
                    GROUP BY
                        e.id,
                        e.first_name,
                        e.last_name,
                        e.email,
                        e.phone,
                        e.description,
                        e.department_id,
                        d.department_name,
                        e.role_id,
                        r.role_name,
                        b.building_name,
                        f.floor_name,
                        rm.room_name;
            """, nativeQuery = true)
    List<EmployeeView> searchEmployeeKeyword(
            @Param("keyword") String keyword);


}
