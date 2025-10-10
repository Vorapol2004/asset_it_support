package com.plub_kao.asset_it_support.entity.borrow.view;

import java.nio.file.LinkOption;
import java.time.LocalDate;

public interface BorrowView {
    Integer getId();


    String getFirstName();

    String getLastName();

    LocalDate getBorrowDate();

    LocalDate getReturnDate();

    String getEmail();

    String getBorrowStatusName();

    String getEquipmentType();

    String getEquipmentName();

    String getSerialNumber();

    String getLicenseKey();

}
