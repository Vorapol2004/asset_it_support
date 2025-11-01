package com.plub_kao.asset_it_support.controller.main;


import com.plub_kao.asset_it_support.entity.EquipmentStatus;
import com.plub_kao.asset_it_support.entity.EquipmentType;
import com.plub_kao.asset_it_support.entity.Role;
import com.plub_kao.asset_it_support.repository.EquipmentStatusRepository;
import com.plub_kao.asset_it_support.repository.EquipmentTypeRepository;
import com.plub_kao.asset_it_support.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private EquipmentTypeRepository equipmentTypeRepository;

    @GetMapping("/e_type")
    public List<EquipmentType> getAllEquipmentTypes() {
        try {
            return equipmentTypeRepository.findAll();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Autowired
    private EquipmentStatusRepository equipmenyStatusRepository;

    @GetMapping ("/e_status")
    public List<EquipmentStatus> getAllEquipmentStatus() {
        try {
            return equipmenyStatusRepository.findAll();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping ("/role")
    public List<Role> getAllRoles() {
        try{
            return roleRepository.findAll();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
