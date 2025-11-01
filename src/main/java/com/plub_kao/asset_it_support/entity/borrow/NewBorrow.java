package com.plub_kao.asset_it_support.entity.borrow;

import com.plub_kao.asset_it_support.entity.BorrowStatus;
import com.plub_kao.asset_it_support.entity.employee.Employee;
import com.plub_kao.asset_it_support.entity.equipment.Equipment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class NewBorrow {


    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String description;
    private String referenceDoc;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private Integer departmentId;
    private Integer roleId;
    private List<Integer> equipmentId;

}
