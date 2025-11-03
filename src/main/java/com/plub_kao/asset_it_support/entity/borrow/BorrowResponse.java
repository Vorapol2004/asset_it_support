package com.plub_kao.asset_it_support.entity.borrow;


import com.plub_kao.asset_it_support.entity.employee.Employee;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BorrowResponse {
    private Integer id;
    private EmployeeResponse employeeResponse;
    private String referenceDoc;
    private LocalDate borrowDate;
    private Integer BorrowStatus;
    private List<Integer> equipmentId;


    @Getter
    @Setter
    public static class EmployeeResponse {

        private Integer id;
        private Integer employeeId;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String description;
        private Integer departmentId;
        private Integer roleId;
    }

    public static BorrowResponse from(Borrow borrow, Employee employee, BorrowRequest request) {
        BorrowResponse BorrowResponse = new BorrowResponse();
        BorrowResponse.setId(borrow.getId());
        BorrowResponse.setReferenceDoc(borrow.getReferenceDoc());
        BorrowResponse.setBorrowDate(borrow.getBorrowDate());
        BorrowResponse.setBorrowStatus(borrow.getBorrowStatus().getId());
        BorrowResponse.setEquipmentId(request.getEquipmentId());

        EmployeeResponse empRes = new EmployeeResponse();
        empRes.setId(employee.getId());
        empRes.setEmployeeId(employee.getId());
        empRes.setFirstName(employee.getFirstName());
        empRes.setLastName(employee.getLastName());
        empRes.setEmail(employee.getEmail());
        empRes.setPhone(employee.getPhone());
        empRes.setDescription(employee.getDescription());
        empRes.setDepartmentId(employee.getDepartment().getId());
        empRes.setRoleId(employee.getRole() != null ? employee.getRole().getId() : null);

        BorrowResponse.setEmployeeResponse(empRes);

        return BorrowResponse;
    }

}
