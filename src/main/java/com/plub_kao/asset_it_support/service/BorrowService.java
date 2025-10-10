package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;

import com.plub_kao.asset_it_support.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;


    @Transactional
    @Scheduled(cron = "0 0 0 * * *") // รันทุกวันตอนเที่ยงคืน
    public void updateOverdueBorrowStatus() {
        borrowRepository.updateOverdueStatus();
    }

    public List<BorrowView> getAllBorrowedEmployeeIds() {
        List<BorrowView> borrowAll = borrowRepository.getAllBorrowedEmployeeIds();
        return borrowAll;

    }

    public List<BorrowView> filterBorrowStatus(@Param("statusId") Integer statusId) {
        List<BorrowView> borrowAll = borrowRepository.filterBorrowStatus(statusId);
        return borrowAll;
    }


}
