package com.plub_kao.asset_it_support.controller.main;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/borrow")
public class BorrowController {


    private final BorrowService borrowService;

    private final BorrowRepository borrowRepository;


    @GetMapping("/update-overdue")
    public String testOverdueUpdate() {
        borrowRepository.updateOverdueStatus();
        return "Updated!";

    }


    @GetMapping("/all")
    public ResponseEntity<List<BorrowView>> getAllBorrowed() {
        List<BorrowView> borrowViews = borrowService.findAllBorrowed();
        return new ResponseEntity<>(borrowViews, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<BorrowView>> filter(
            @RequestParam(required = false) Integer borrowStatusId,
            @RequestParam(required = false) Integer roleId
    ) {
        List<BorrowView> BorrowView = borrowService.filterStatusAndRole(borrowStatusId, roleId);
        return ResponseEntity.ok(BorrowView);
    }

    @GetMapping("/select")
    public ResponseEntity<List<BorrowView>> selectBorrow(@RequestParam Integer borrowId) {
        List<BorrowView> borrow = borrowService.selectBorrowId(borrowId);
        return ResponseEntity.ok(borrow);
    }


    @GetMapping("/search")
    public ResponseEntity<List<BorrowView>> searchBorrowEquipment(@RequestParam String keyword) {
        List<BorrowView> borrowALll = borrowService.searchBorrowEquipment(keyword);
        if (borrowALll.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(borrowALll);
    }

    @PostMapping("/create")
    public ResponseEntity<BorrowResponse> createBorrow(@RequestBody BorrowRequest request) {
        try {
            BorrowResponse response = borrowService.createBorrow(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // ถ้ามี error เช่น อุปกรณ์ไม่พร้อม หรือไม่พบผู้ยืม
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // error อื่น ๆ
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
