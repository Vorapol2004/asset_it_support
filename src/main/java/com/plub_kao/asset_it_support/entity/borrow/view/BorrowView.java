package com.plub_kao.asset_it_support.entity.borrow.view;

import java.nio.file.LinkOption;
import java.time.LocalDate;

public interface BorrowView {
    Integer getId();

    LocalDate getBorrowDate();

    LocalDate returnDate();

    LocalDate dueDate();

    Integer getBorrowStatusId();

    String getBorrowStatusName();

    String getReferenceDoc();

    Integer getEmployeeId();

    String getEmployeeName();

    String getEmail();

    String getPhone();

    Integer getDescription();

    String getDepartmentName();

    Integer getRoleId();

    String getRoleName();

    Integer getEquipmentId();

    Integer getEquipmentTypeId();

    String getEquipmentTypeName();

    String getBrand();

    String getModel();

    String getSerialNumber();

    String getLicenseKey();

    Integer getEquipmentStatusId();

    String getEquipmentStatusName();


}
