package com.plub_kao.asset_it_support.entity.borrow.view;

import java.nio.file.LinkOption;
import java.time.LocalDate;

public interface BorrowView {
    Integer getId();

    String getDepartmentName();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getPhone();

    Integer getBorrowStatusId();

    Integer getEmployeeId();

    String getRoleName();

    String getReferenceDoc();

    Integer getBorrowEquipmentId();

    String getApproverName();

    Integer getBorrowEquipmentCount();

    LocalDate getBorrowDate();

    LocalDate getDueDate();

    LocalDate getReturnDate();

    Integer getEquipmentId();

    String getEquipmentName();

    String getBrand();

    String getLicenseKey();

    String getSerialNumber();

    String getModel();

    String getBorrowStatusName();

    String getEquipmentTypeName();


}
