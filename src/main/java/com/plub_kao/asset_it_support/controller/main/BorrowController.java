package com.plub_kao.asset_it_support.controller.main;

import com.plub_kao.asset_it_support.entity.borrow.BorrowResponseTest;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.entity.role.Role;
import com.plub_kao.asset_it_support.entity.borrow.BorrowResponse;
import com.plub_kao.asset_it_support.entity.borrow.BorrowRequest;
import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;
import com.plub_kao.asset_it_support.repository.BorrowRepository;
import com.plub_kao.asset_it_support.repository.EmployeeRepository;
import com.plub_kao.asset_it_support.service.BorrowService;

import com.plub_kao.asset_it_support.service.EmployeeService;
import com.plub_kao.asset_it_support.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/borrow")
public class BorrowController {


    private final BorrowService borrowService;

    private final BorrowRepository borrowRepository;


    @PostMapping("/update-overdue")
    public ResponseEntity<String> updateOverdue() {
        borrowService.updateOverdueBorrowStatus();
        return ResponseEntity.ok("Update overdue status completed.");
    }


    @GetMapping("/all")
    public ResponseEntity<List<BorrowResponseTest>> getAllBorrowed() {
        List<BorrowResponseTest> borrowViews = borrowService.findAllBorrowedTest();
        return new ResponseEntity<>(borrowViews, HttpStatus.OK);
    }


    @GetMapping("/select")
    public ResponseEntity<List<BorrowResponseTest>> selectBorrow(@RequestParam Integer borrowId) {
        List<BorrowResponseTest> borrow = borrowService.selectBorrowId(borrowId);
        return ResponseEntity.ok(borrow);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<BorrowResponseTest>> filter(
            @RequestParam(required = false) Integer borrowStatusId,
            @RequestParam(required = false) Integer roleId,
            @RequestParam(required = false) Integer departmentId,
            @RequestParam(required = false) String keyword
    ) {
        List<BorrowResponseTest> result = borrowService.filterStatusRoleDeptKeyword(
                borrowStatusId,
                roleId,
                departmentId,
                keyword
        );

        return ResponseEntity.ok(result);
    }


    @GetMapping("/search")
    public ResponseEntity<List<BorrowResponseTest>> searchBorrowEquipment(@RequestParam String keyword) {
        List<BorrowResponseTest> borrowALll = borrowService.searchBorrowEquipment(keyword);
        if (borrowALll.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(borrowALll);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBorrow(@RequestBody BorrowRequest request) {
        try {
            BorrowResponseTest response = borrowService.createBorrow(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // ถ้ามี error เช่น อุปกรณ์ไม่พร้อม หรือไม่พบผู้ยืม
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // error อื่น ๆ
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/return")
    public ResponseEntity<String> returnBorrow(@RequestBody ReturnRequest request) {
        return ResponseEntity.ok(borrowService.returnEquipment(request));
    }
}
