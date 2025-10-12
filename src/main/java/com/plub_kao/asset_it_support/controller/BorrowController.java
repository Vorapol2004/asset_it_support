package com.plub_kao.asset_it_support.controller;

import com.plub_kao.asset_it_support.entity.borrow.Borrow;
import com.plub_kao.asset_it_support.entity.borrow.NewBorrow;
import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;
import com.plub_kao.asset_it_support.repository.BorrowRepository;
import com.plub_kao.asset_it_support.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BorrowRepository borrowRepository;


    @GetMapping("/update-overdue")
    public String testOverdueUpdate() {
        borrowRepository.updateOverdueStatus();
        return "Updated!";
    }

    @GetMapping("/all")
    public ResponseEntity<List<BorrowView>> getAllBorrowedEmployeeIds() {
        List<BorrowView> borrowAll = borrowService.getAllBorrowedEmployeeIds();
        return ResponseEntity.ok(borrowAll);
    }


    // ดึงทั้งหมด หรือกรองตามสถานะ
    @GetMapping
    public List<BorrowView> filterBorrows(@RequestParam(required = false) Integer statusId) {
        return borrowService.filterBorrowStatus(statusId);
    }

    @PostMapping("/add")
    public Borrow NewBorrow(@RequestBody NewBorrow newBorrow) {
        return borrowService.newBorrow(newBorrow);
    }
}
