package com.plub_kao.asset_it_support.controller.main;

import com.plub_kao.asset_it_support.entity.borrow.BorrowResponse;
import com.plub_kao.asset_it_support.entity.borrow.NewBorrow;
import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;
import com.plub_kao.asset_it_support.entity.employee.view.EmployeeView;
import com.plub_kao.asset_it_support.repository.BorrowRepository;
import com.plub_kao.asset_it_support.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //เรียกดูประวัติการยืมของ Borrow ทั้งหมด
    @GetMapping("/all")
    public ResponseEntity<List<BorrowView>> getAllBorrowedEmployeeIds() {
        List<BorrowView> borrowAll = borrowService.getAllBorrowedEmployeeId();
        if (borrowAll.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(borrowAll);
    }


    //ฟิลเตอร์ Status ของ Borrow ออกมาทั้งหมด
    @GetMapping("/filter/Status/{id}")
    public ResponseEntity<List<BorrowView>> filterBorrowStatus(@PathVariable Integer id) {
        List<BorrowView> borrowALll = borrowService.filterBorrowStatus(id);
        if (borrowALll.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(borrowALll);

    }

    //ค้นหาประวัติการยืมอุปกรณ์แต่ละชนิด จาก license_key และ serial_number
    @GetMapping("/search")
    public ResponseEntity<List<BorrowView>> searchBorrowEquipment(@RequestParam String keyword) {
        List<BorrowView> borrowALll = borrowService.searchBorrowEquipment(keyword);
        if (borrowALll.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(borrowALll);
    }

//    @PostMapping("/add")
//    public BorrowResponse NewBorrow(@RequestBody NewBorrow newBorrow) {
//        return borrowService.newBorrow(newBorrow);
//    }
}
