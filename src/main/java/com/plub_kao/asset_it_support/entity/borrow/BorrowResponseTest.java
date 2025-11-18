package com.plub_kao.asset_it_support.entity.borrow;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BorrowResponseTest {

    private Integer id;
    private LocalDate borrowDate;
    private Integer borrowStatusId;
    private String borrowStatusName;
    private String referenceDoc;
    private EmployeeInfo employee;
    private String approverName;
    private Integer borrowEquipmentCount;
    private List<BorrowEquipment> equipments;

    @Getter
    @Setter
    public static class EmployeeInfo {
        private Integer id;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String roleName;
        private String departmentName;
    }

    @Getter
    @Setter
    public static class BorrowEquipment {

        private Integer id;
        private Integer borrowEquipmentId;
        private String equipmentName;
        private String serialNumber;
        private String licenseKey;
        private String brand;
        private String model;
        private String equipmentTypeName;
        private LocalDate dueDate;
        private LocalDate returnDate;


    }

    @Getter
    @Setter
    public static class CategoryInfo {
        private Integer id;
        private String name;
    }


}
