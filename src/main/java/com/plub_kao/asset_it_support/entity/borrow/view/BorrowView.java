package com.plub_kao.asset_it_support.entity.borrow.view;

import java.nio.file.LinkOption;
import java.time.LocalDate;

public interface BorrowView {
    Integer getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getPhone();

    String getRoleName();

    LocalDate getBorrowDate();

    LocalDate getDueDate();

    String getApproverName();

    String getBorrowStatusName();

    Integer getBorrowId();


}
