package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.equipmentType.EquipmentTypeView;
import com.plub_kao.asset_it_support.entity.role.Role;
import com.plub_kao.asset_it_support.entity.role.RoleView;
import com.plub_kao.asset_it_support.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {


    private final RoleRepository roleRepository;

    public List<RoleView> findAll() {
        try {
            return roleRepository.findAllByRole();
        } catch (RuntimeException e) {
            throw new RuntimeException("พัง");
        }
    }
}
