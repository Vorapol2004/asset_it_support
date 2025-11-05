package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.borrowStatus.BorrowStatusView;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import com.plub_kao.asset_it_support.service.BorrowStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/borrow_status")
public class BorrowStatusController {

    private final BorrowStatusService borrowStatusService;

    @GetMapping("/status")
    public ResponseEntity<List<BorrowStatusView>> findByStatus() {
        List<BorrowStatusView> BorrowStatusView = borrowStatusService.findAll();
        return ResponseEntity.ok(BorrowStatusView);

    }
}
