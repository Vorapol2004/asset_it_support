package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;
import com.plub_kao.asset_it_support.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    public List<BorrowView> getAllBorrowedEmployeeIds() {
        List <BorrowView> borrowAll = borrowRepository.getAllBorrowedEmployeeIds();
        return borrowAll;

    }







}
