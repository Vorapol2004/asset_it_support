package com.plub_kao.asset_it_support.entity.borrow.view;

public interface BorrowViewTest {

    Integer getBorrowId();

    String getEmployeeName();

    String getEmail();

    String getBorrowDate();

    String getDueDate();

    String getReturnDate();

    String getBorrowStatusName();

    String getBorrowedEquipment(); // จาก GROUP_CONCAT

    Integer getTotalItems();

    Integer getReturnedItems();
}
