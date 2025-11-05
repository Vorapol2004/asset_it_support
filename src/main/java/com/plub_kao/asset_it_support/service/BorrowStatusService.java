package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.borrowStatus.BorrowStatusView;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import com.plub_kao.asset_it_support.repository.BorrowStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowStatusService {

    private final BorrowStatusRepository borrowStatusRepository;

    public List<BorrowStatusView> findAll() {
        try {
            return borrowStatusRepository.findAllByStatus();
        } catch (RuntimeException e) {
            throw new RuntimeException("พัง", e);
        }
    }

}
