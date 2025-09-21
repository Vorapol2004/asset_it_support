package com.plub_kao.asset_it_support.service;

import com.plub_kao.asset_it_support.entity.Role;
import com.plub_kao.asset_it_support.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {


    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
