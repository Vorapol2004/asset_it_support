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
    private List<EquipmentInfo> equipments;


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

    @Getter
    @Setter
    public static class EquipmentInfo {
        private String equipmentName;
        private String serialNumber;
        private String licenseKey;
    }

    public static BorrowResponse from(Borrow borrow, Employee employee, BorrowRequest request) {
        BorrowResponse res = new BorrowResponse();
        res.setId(borrow.getId());
        res.setReferenceDoc(borrow.getReferenceDoc());
        res.setBorrowDate(borrow.getBorrowDate());
        res.setBorrowStatus(borrow.getBorrowStatus().getId());

        List<EquipmentInfo> equipmentInfos = borrow.getBorrowEquipments().stream()
                .map(be -> {
                    EquipmentInfo info = new EquipmentInfo();
                    info.setEquipmentName(be.getEquipment().getEquipmentName());
                    info.setSerialNumber(be.getEquipment().getSerialNumber());
                    info.setLicenseKey(be.getEquipment().getLicenseKey());
                    return info;
                })
                .toList();
        res.setEquipments(equipmentInfos);

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

        res.setEmployeeResponse(empRes);

        return res;
    }

}
