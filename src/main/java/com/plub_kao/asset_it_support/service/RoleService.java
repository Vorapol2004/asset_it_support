package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.borrow.view.BorrowView;
import com.plub_kao.asset_it_support.entity.equipment.view.EquipmentView;
import com.plub_kao.asset_it_support.entity.equipmentType.EquipmentTypeView;
import com.plub_kao.asset_it_support.entity.role.Role;
import com.plub_kao.asset_it_support.entity.role.RoleView;
import com.plub_kao.asset_it_support.repository.BorrowRepository;
import com.plub_kao.asset_it_support.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {


    private final RoleRepository roleRepository;
    private final BorrowRepository borrowRepository;

    public List<RoleView> findAll() {
        try {
            return roleRepository.findAllByRole();
        } catch (RuntimeException e) {
            throw new RuntimeException("พัง");
        }
    }

    public List<BorrowView> FilterBorrowStatus(Integer id) {
        try {
            return borrowRepository.FilterRole(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("พัง", e);
        }
    }
}
