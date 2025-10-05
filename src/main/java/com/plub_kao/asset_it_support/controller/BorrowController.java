package com.plub_kao.asset_it_support.controller;

import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;
import com.plub_kao.asset_it_support.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @GetMapping("/all")
    public ResponseEntity<List<BorrowView>> getAllBorrowedEmployeeIds() {
        List<BorrowView> borrowAll = borrowService.getAllBorrowedEmployeeIds();
        return ResponseEntity.ok(borrowAll);
    }
}
