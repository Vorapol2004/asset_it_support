package com.plub_kao.asset_it_support.service;


import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;
import com.plub_kao.asset_it_support.entity.borrowStatus.BorrowStatusView;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.entity.equipmentStatus.EquipmentStatusView;
import com.plub_kao.asset_it_support.repository.BorrowRepository;
import com.plub_kao.asset_it_support.repository.BorrowStatusRepository;
import com.plub_kao.asset_it_support.repository.EquipmentStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowStatusService {

    private final BorrowStatusRepository borrowStatusRepository;
    private final BorrowRepository borrowRepository;

    public List<BorrowStatusView> findAll() {
        try {
            return borrowStatusRepository.findAllByStatus();
        } catch (RuntimeException e) {
            throw new RuntimeException("พัง", e);
        }
    }

    public List<BorrowView> FilterBorrowStatus(Integer id) {
        try {
            return borrowRepository.FilterBorrowStatus(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("พัง", e);
        }
    }

}
