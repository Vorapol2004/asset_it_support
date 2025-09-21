package com.plub_kao.asset_it_support.entity.borrow.view;

import java.nio.file.LinkOption;
import java.time.LocalDate;

public interface BorrowView {
    Integer getId();
    Integer getEmployeeId();
    LocalDate getBorrowDate();
    String getReferenceDoc();
    Integer getBorrowEquipmentId();
}
